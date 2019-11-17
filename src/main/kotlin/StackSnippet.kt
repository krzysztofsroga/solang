import kotlinx.serialization.UnstableDefault

@UnstableDefault
sealed class StackSnippet: Snippet() {
    protected abstract val platform: StackPlatform
    protected abstract val codeBlockNumber: Int
    protected abstract val revisionNumber: Int?
    protected abstract val answerNumber: Int

    override suspend fun getCode(): String {
        val answerBody: UnparsedPost = revisionNumber?.let { revisionNumber ->
            StackOverflowConnection.getAnswerAllRevisionBodies(platform, answerNumber).getValue(revisionNumber)
        } ?: StackOverflowConnection.getAnswerBody(platform, answerNumber)

        //TODO throw NoSuchAnswerRevision exception
        return answerBody.getCodeBlocks()[codeBlockNumber - SoLangConfiguration.codeBlockIndices.value]
    }
}

@UnstableDefault
class StackOverflowSnippet(
    override val answerNumber: Int,
    override val codeBlockNumber: Int = 1,
    override val revisionNumber: Int? = null
) : StackSnippet() {
    override val platform: StackPlatform = StackPlatform.StackOverflowPlatform
}

@UnstableDefault
class StackCodeGolfSnippet(
    override val answerNumber: Int,
    override val codeBlockNumber: Int = 1,
    override val revisionNumber: Int? = null
) : StackSnippet() {
    override val platform: StackPlatform = StackPlatform.CodeGolfPlatform
}

@UnstableDefault
class StackCustomSnippet(
    override val platform: StackPlatform,
    override val answerNumber: Int,
    override val codeBlockNumber: Int = 1,
    override val revisionNumber: Int? = null
) : StackSnippet()