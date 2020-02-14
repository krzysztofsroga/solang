import kotlinx.coroutines.runBlocking
import kotlinx.serialization.UnstableDefault
import java.io.File

@UnstableDefault
fun main() {

    multiFileC()
//    searchSnippet()

}
//fun main()=cc{+Cgs(180218)}.bw("python")
//fun main()=createCode{+StackCodeGolfSnippet(180218)}.buildWith("python","a")


@UnstableDefault
fun multiFileC() {
    // https://stackoverflow.com/a/4349617
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

data class SnippetWithOutput(val code: SoCode, val output: String)

@UnstableDefault
fun searchSnippet() {
    val testName = "SomeSuperUniqueName"
    File(testName).createNewFile()
    val fetchedAnswers = SearchSnippet(StackPlatform.StackOverflowPlatform, listOf("command", "python"), 10)
    val snippetsWithOutput = fetchedAnswers.mapIndexed { i, fetchedAnswer ->
        val code = createCode { +fetchedAnswer }
        val output = code.buildWith("python", "answer$i.py", outputMode = OutputMode.CATCH)
        SnippetWithOutput(code, output)
    }
    snippetsWithOutput.filter { it.output.contains(testName) }.forEach {
        println("Desired output is produced by code:")
        println(it.code.code)
    }
}

@UnstableDefault
fun blenderPlatform() {
    println(createCode {
        +StackCustomSnippet(StackPlatform.BlenderPlatform, 128374)
    }.code)
}