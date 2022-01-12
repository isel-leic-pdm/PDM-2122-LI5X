import kotlinx.coroutines.*

suspend fun doIO() {
    println("${Thread.currentThread().name}: doIt() starts")
    withContext(Dispatchers.IO) {
        println("${Thread.currentThread().name}: doIt() inside withContext")
        delay(3_000)
    }
}

fun main(args: Array<String>) {

    println("${Thread.currentThread().name}: main() starts")

    runBlocking {
        println("${Thread.currentThread().name}: Launching")
        launch {
            println("${Thread.currentThread().name}: Coroutine 1 starts")
            doIO()
            println("${Thread.currentThread().name}: Coroutine 1 ends")
        }

        launch {
            println("${Thread.currentThread().name}: Coroutine 2 starts")
            repeat(30) {
                delay(1_000)
                print('2')
            }
            println("${Thread.currentThread().name}: Coroutine 2 starts")
        }
    }


    println("${Thread.currentThread().name}: main() ends")
}
