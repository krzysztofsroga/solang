import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class CodeBuilder {

    private val snippets = mutableListOf<Snippet>()

    internal fun build(): SoCode = SoCode(runBlocking {
        snippets.map {
            async {
                it.render()
            }
        }.map {
            it.await()
        }.joinToString("\n") //TODO open issue in kotlin - map can't be merged with joinToString
    })

    operator fun Snippet.unaryPlus(): Snippet {
        snippets += this
        return this
    }
}

inline class SoCode(val code: String)

fun createCode(block: CodeBuilder.() -> Unit): SoCode = CodeBuilder().apply(block).build()