import SoLangConfiguration.SoLangMode.*
import SoLangConfiguration.soLangMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

@Experimental
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class ExperimentalSnippet


fun SoCode.buildWith(buildScript: String, targetFile: String) {
    val command = "$buildScript $targetFile"

    when (soLangMode) {
        UNSAFE -> {
            println("Code will be saved in file: $targetFile")
            SoCompiler(this, command).compile(targetFile)
        }
        SAFE -> {
            println("Code will be saved in file: $targetFile")
            if (code.promptOk("code") && command.promptOk("build command")) {
                SoCompiler(this, command).compile(targetFile)
            } else {
                println("Build canceled.")
            }
        }
        PRINT -> {
            println("Build command: $command")
            println("Code:\n$code")
        }
    }
}

internal fun String.promptOk(what: String): Boolean {
    println("This is $what:")
    println(this)
    print(":: Does $what look okay? [y/N] ")
    val input = readLine()
    return input != null && input.isNotEmpty() && input.first().toLowerCase() == 'y'
}

/**
 * Thread safe, locked memoization of one argument function
 */
internal fun <A, R> (suspend (A) -> R).memSuspend(): suspend (A) -> R {
    val cache: MutableMap<A, Deferred<R>> = HashMap() //No need for ConcurrentMap because of lock
    val scope = CoroutineScope(Dispatchers.Default)
    return { a: A ->
        synchronized(scope) {
            //so that it isn't called once again until previous async is started
            cache.getOrPut(a) {
                scope.async { this@memSuspend(a) }
            }
        }.await()
    }
}
internal fun <A, B, R> (suspend (A, B) -> R).memSuspend(): suspend (A, B) -> R {
    val cache: MutableMap<Pair<A, B>, Deferred<R>> = HashMap() //No need for ConcurrentMap because of lock
    val scope = CoroutineScope(Dispatchers.Default)
    return { a: A, b: B ->
        synchronized(scope) {
            //so that it isn't called once again until previous async is started
            cache.getOrPut(a to b) {
                scope.async { this@memSuspend(a, b) }
            }
        }.await()
    }
}


internal fun<T> Sequence<T>.getElement(n: Int): T = toList()[n]
//internal fun<T> Sequence<T>.getElement(n: Int): T = take(n+1).last()//more efficient than toList()[n] because it skips all further elements