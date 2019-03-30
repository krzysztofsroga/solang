import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class CodeBuilder {

    private val snippets = mutableListOf<Snippet>()

    internal fun build(): String = runBlocking {
        snippets.map {
            async {
                it.render()
            }
        }.map {
            it.await()
        }.joinToString("\n") //TODO open issue in kotlin - map can't be merged with joinToString
    }

    operator fun Snippet.unaryPlus(): Snippet {
        snippets += this
        return this
    }
}

fun createCode(block: CodeBuilder.() -> Unit): String = CodeBuilder().apply(block).build()