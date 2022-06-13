import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext
import org.koin.dsl.module

fun main(args: Array<String>) {

    // стартует механихм DI на основе библиотеки Koin
    GlobalContext.startKoin {
        printLogger() // печатает служебную строку DI
        modules(injModule) // какие модули (контейнеры) стартовать
    }

    // тестируем DI
    val testKoin = TestKoin() // класс, в котором внедряются объекты
    testKoin.comp1.on() // пробуем вызвать метод у внедренного объекта

}

// этот интерфейс будет реализовавать объект
// вместо конкретного типа, при внедрении будет указываться интефейс
interface Technica{
    fun on()
    fun off()
}

// его будем внедрять в коде где необходимо
class Computer(val name: String): Technica {

    // функции-реализации интерфейса Technica

    override fun on() {
        println("$name + on")
    }

    override fun off() {
        println("$name + off")
    }

}

// DI контейнер, в котором указываем какие объекты будут возвращаться и их область действия
val injModule = module {
    // "as" указывать обязательно, иначе DI не сработает (даже если IDEA рекомендует удалить as)
    single {Computer("IBM") as Technica} // single - область действия объекта (scope)

}

// с помощью KoinComponent мы теперь можем внедрять объекты в коде
class TestKoin: KoinComponent {
    val comp1 by inject<Technica>() // само внедрение объекта по типу интерфейса

}