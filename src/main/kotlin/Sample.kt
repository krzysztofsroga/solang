import kotlinx.coroutines.runBlocking
import kotlinx.serialization.UnstableDefault

@UnstableDefault
fun main() {
    SoLangConfiguration.soLangMode = SoLangConfiguration.SoLangMode.SAFE
    createCode {
        +StackOverflowSnippet(answerNumber = 4349617, codeBlockNumber = 1)
    }.justSave("main.c")
    createCode {
        +StackOverflowSnippet(answerNumber = 4349617, codeBlockNumber = 2)
    }.justSave("foo.h")
    createCode {
        +StackOverflowSnippet(answerNumber = 4349617, codeBlockNumber = 3)
    }.justSave("foo.c")
    createCode {
        +StackOverflowSnippet(answerNumber = 4349617, codeBlockNumber = 4) change ("    " to "\t")
    }.justSave("makefile")
    SimpleBuildCommand("make").bulid()
    SimpleBuildCommand("./myprogram").bulid()
}


@UnstableDefault
fun CodeBuilder.stackSort() = runBlocking {
    val fetchedAnswers = SearchSnippet(StackPlatform.StackOverflowPlatform, listOf("sorting", "python"), 10)
    fetchedAnswers.forEachIndexed { i, fetchedAnswer ->
        +SimpleSnippet("\n\n\n\nDownloaded Snipped number $i:\n")
        +fetchedAnswer
    }
}

