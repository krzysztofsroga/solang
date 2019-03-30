import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

fun String.buildWith(buildScript: String, targetFile: String) {
    SoCompiler(this, "$buildScript $targetFile").compile(targetFile)
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