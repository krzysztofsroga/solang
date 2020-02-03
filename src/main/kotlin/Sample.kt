import kotlinx.coroutines.runBlocking
import kotlinx.serialization.UnstableDefault

@UnstableDefault
fun main() {
    SoLangConfiguration.soLangMode = SoLangConfiguration.SoLangMode.SAFE
    SoLangConfiguration.apiToken = "" //YOUR TOKEN GOES HERE

    createCode {
        +StackOverflowSnippet(answerNumber = 24846766, codeBlockNumber = 1)
    }.justSave("Fibonacci.py")

    createCode {
        +SimpleSnippet("from Fibonacci import *")
        +StackOverflowSnippet(answerNumber = 24846766, codeBlockNumber = 2)
    }.buildWith("python", "Main.py")
}


@UnstableDefault
fun CodeBuilder.stackSort() = runBlocking {
    val fetchedAnswers = SearchSnippet(StackPlatform.StackOverflowPlatform, listOf("sorting", "python"), 10)
    fetchedAnswers.forEachIndexed { i, fetchedAnswer ->
        +SimpleSnippet("\n\n\n\nDownloaded Snipped number $i:\n")
        +fetchedAnswer
    }
}

