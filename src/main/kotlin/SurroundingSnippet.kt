import kotlin.properties.Delegates

@ExperimentalSnippet
class SurroundingSnippetBuilder {
    var before: Snippet by Delegates.notNull()
    var after: Snippet by Delegates.notNull()
}

@ExperimentalSnippet
fun surroundingSnippet(beforeAfterConfig: SurroundingSnippetBuilder.() -> Unit): CodeBuilder.(CodeBuilder.() -> Unit) -> Unit {
    return { insideCode ->
        //apart from receiving insideCode this lambda also extends CodeBuilder
        val builder = SurroundingSnippetBuilder().apply(beforeAfterConfig)
        +builder.before
        insideCode()
        +builder.after
    }
}

