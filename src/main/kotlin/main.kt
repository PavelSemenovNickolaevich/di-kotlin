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
    val testKoin = TestKoin()
    print(testKoin.getCompName())

}

// его будем внедрять в коде где необходимо
data class Computer(val name: String)

// DI контейнер, в котором указываем какие объекты будут возвращаться и их область действия
val injModule = module {
    single {Computer("test")} // single - область действия объекта (scope)
}

// с помощью KoinComponent мы теперь можем внедрять объекты в коде
class TestKoin: KoinComponent {
    val comp1 by inject<Computer>()

    fun getCompName(): String{
        return comp1.name
    }
}