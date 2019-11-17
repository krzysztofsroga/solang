import kotlinx.serialization.UnstableDefault

@UnstableDefault
class SearchSnippet(
    private val platform: StackPlatform,
    private val searchTags: Collection<String>,
    private val numberOfAnswers: Int
) : Collection<Snippet> {

    override val size: Int
        get() = numberOfAnswers

    operator fun get(answerNumber: Int): Snippet {
        assert(answerNumber < numberOfAnswers)
        return object : Snippet() {
            override suspend fun getCode(): String {
                val answerBody =
                    StackOverflowConnection.getAcceptedAnswers(platform, searchTags, numberOfAnswers)[answerNumber]
                return answerBody.getCodeBlocks().firstOrNull() ?: "" //TODO allow getting non-first code block
            }
        }
    }

    override fun contains(element: Snippet): Boolean {
        return false
    }

    override fun containsAll(elements: Collection<Snippet>): Boolean {
        return false
    }

    override fun isEmpty(): Boolean {
        return numberOfAnswers == 0
    }

    override fun iterator(): Iterator<Snippet> {
        return object : Iterator<Snippet> {
            var position: Int = 0
            override fun hasNext(): Boolean {
                return position < numberOfAnswers
            }

            override fun next(): Snippet {
                return this@SearchSnippet[position++]
            }
        }
    }
}