import kotlinx.coroutines.runBlocking
import kotlinx.serialization.UnstableDefault

@UnstableDefault
fun main() = createCode {
    runBlocking {
//        StackOverflowConnection.getAcceptedAnswers(listOf("sorting", "python"), 3).forEach { println(it) }

        println("Resulting answers: ${StackOverflowConnection.getAcceptedAnswers(listOf("sorting", "python"), 10).size}")
    }
    SoLangConfiguration.soLangMode = SoLangConfiguration.SoLangMode.PRINT
    SoLangConfiguration.apiToken = "" //YOUR TOKEN GOES HERE

//    +StackOverflowSnippet(answerNumber = 50165755) toLine 15 //declare randomdate
//    +StackOverflowSnippet(answerNumber = 48980683, codeBlockNumber = 2) toLine 1 change ("random.random" to "randomTime") change ("x" to "my_list")  //generate list
//    +StackOverflowSnippet(answerNumber = 902736, codeBlockNumber = 6) fromLine 3 change ("&gt;" to ">")//declare bubble and print sorted lits
}.buildWith("python2", "script.py")

