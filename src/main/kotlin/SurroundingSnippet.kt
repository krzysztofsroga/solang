import kotlin.properties.Delegates

class SurroundingSnippetBuilder {
    var before: Snippet by Delegates.notNull()
    var after: Snippet by Delegates.notNull()
}

fun surroundingSnippet(beforeAfterConfig: SurroundingSnippetBuilder.() -> Unit): CodeBuilder.(CodeBuilder.() -> Unit) -> Unit {
    return { insideCode ->
        //apart from receiving insideCode this lambda also extends CodeBuilder
        val builder = SurroundingSnippetBuilder().apply(beforeAfterConfig)
        addSnippet(builder.before) //TODO use urnary+
        insideCode()
        addSnippet(builder.after)
    }
}

