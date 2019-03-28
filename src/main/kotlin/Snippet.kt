sealed class Snippet {

    private val modifiers = mutableListOf<String.() -> String>()

    private fun applyStringModifier(modifier: String.() -> String) = apply {
        modifiers += modifier
    }

    infix fun fromLine(x: Int) = applyStringModifier {
        lines().drop(x).joinToString("\n")
    }

    infix fun toLine(x: Int) = applyStringModifier {
        lineSequence().take(x).joinToString("\n")
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

class StackOverflowSnippet(private val answerNumber: Int, private val codeBlockNumber: Int) : Snippet() {
    override suspend fun getCode(): String {
        val answerBody = StackOverflowConnection.getAnswerBody(answerNumber)
        val codeRegex = Regex(
            "<code>((.|\\n)*?)</code>",
            setOf(RegexOption.CANON_EQ)
        ) //TODO check if it doesn't need to be <pre><code>
        return codeRegex.findAll(answerBody).take(codeBlockNumber+1).toList()[codeBlockNumber].destructured.component1() //Using take because finding later blocks is not necessary
    }
}