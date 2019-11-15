class UnparsedAnswer(private val body: String) { //TODO this should be inline class
    companion object {
        private val codeRegex = Regex(
            "<pre><code>((.|\\n)*?)</code></pre>",
            setOf(RegexOption.CANON_EQ)
        )
    }
    fun getCodeBlocks() = codeRegex.findAll(body)
        .map { it.destructured.component1() }.toList()
}