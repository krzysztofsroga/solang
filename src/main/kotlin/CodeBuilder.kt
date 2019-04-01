class CodeBuilder {

    private val snippets = mutableListOf<Snippet>()

    internal fun build(): SoCode = SoCode(
        snippets.joinToString("\n") {
            it.render()
        } //TODO open issue in kotlin - map can't be merged with joinToString
    )

    operator fun Snippet.unaryPlus(): Snippet {
        snippets += this
        return this
    }
}

inline class SoCode(val code: String)

fun createCode(block: CodeBuilder.() -> Unit): SoCode = CodeBuilder().apply(block).build()