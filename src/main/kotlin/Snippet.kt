sealed class Snippet {

    private val modifiers = mutableListOf<String.() -> String>()

    private fun applyStringModifier(modifier: String.() -> String) = apply {
        modifiers += modifier
    }

    infix fun fromLine(x: Int) = applyStringModifier {
        lines().drop(x - 1).joinToString("\n")
    }

    infix fun toLine(x: Int) = applyStringModifier {
        // TODO Doesn't work after fromLine - takes n lines instead of up to nth line of whole snippet"
        lineSequence().take(x).joinToString("\n")
    }

    infix fun takeLines(x: IntRange) = applyStringModifier {
        lines().slice((x.first - 1)..(x.last - 1)).joinToString("\n")
    }

    infix fun change(x: Pair<String, String>): Snippet = applyStringModifier {
        replace(x.first, x.second)
    }

    protected abstract suspend fun getCode(): String

    internal suspend fun render(): String {
        var code = getCode()
        for (modifier in modifiers) {
            code = code.modifier()
        }
        return code
    }
}

class SimpleSnippet(private val code: String) : Snippet() {
    override suspend fun getCode(): String {
        return code
    }
}

sealed class StackSnippet: Snippet() {
    protected abstract val platform: String
    protected abstract val codeBlockNumber: Int
    protected abstract val revisionNumber: Int?
    protected abstract val answerNumber: Int

    override suspend fun getCode(): String {
        val answerBody: String = revisionNumber?.let { revisionNumber ->
            StackOverflowConnection.getAnswerAllRevisionBodies(platform, answerNumber).getValue(revisionNumber)
        } ?: StackOverflowConnection.getAnswerBody(platform, answerNumber)
        //TODO throw NoSuchAnswerRevision exception
        val codeRegex = Regex(
            "<pre><code>((.|\\n)*?)</code></pre>",
            setOf(RegexOption.CANON_EQ)
        )
        return codeRegex.findAll(answerBody)
            .getElement(codeBlockNumber - SoLangConfiguration.codeBlockIndices.value)
            .destructured.component1()
    }
}

class StackOverflowSnippet(
    override val answerNumber: Int,
    override val codeBlockNumber: Int = 1,
    override val revisionNumber: Int? = null
) : StackSnippet() {
    override val platform: String = "stackoverflow"
}

class StackCodeGolfSnippet(
    override val answerNumber: Int,
    override val codeBlockNumber: Int = 1,
    override val revisionNumber: Int? = null
) : StackSnippet() {
    override val platform: String = "codegolf"
}

class StackCustomSnippet(
    override val platform: String,
    override val answerNumber: Int,
    override val codeBlockNumber: Int = 1,
    override val revisionNumber: Int? = null
) : StackSnippet()