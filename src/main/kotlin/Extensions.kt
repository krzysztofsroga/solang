import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class ExperimentalSnippet


fun SoCode.buildWith(buildScript: String, targetFile: String, outputMode: OutputMode = OutputMode.INHERIT): String {
    val compiler = SoCompiler("$buildScript $targetFile")
    compiler.save(targetFile, this)
    return compiler.build(outputMode)
}

fun SoCode.justSave(targetFile: String) = SoCompiler("").save(targetFile, this) //TODO use compiler of the project when project class is created

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

internal fun <A, B, C, R> (suspend (A, B, C) -> R).memSuspend(): suspend (A, B, C) -> R {
    val cache: MutableMap<Triple<A, B, C>, Deferred<R>> = HashMap() //No need for ConcurrentMap because of lock
    val scope = CoroutineScope(Dispatchers.Default)
    return { a: A, b: B, c: C ->
        synchronized(scope) {
            //so that it isn't called once again until previous async is started
            cache.getOrPut(Triple(a, b, c)) {
                scope.async { this@memSuspend(a, b, c) }
            }
        }.await()
    }
}

internal fun <T> Sequence<T>.getElement(n: Int): T = toList()[n]
//internal fun<T> Sequence<T>.getElement(n: Int): T = take(n+1).last()//more efficient than toList()[n] because it skips all further elements