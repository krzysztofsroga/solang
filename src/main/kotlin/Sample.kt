fun main() = createCode {
    SoLangConfiguration.apiToken = "" //YOUR TOKEN GOES HERE
    SoLangConfiguration.soLangMode = SoLangConfiguration.SoLangMode.PRINT

    +StackOverflowSnippet(answerNumber = 1077349)
    +StackOverflowSnippet(answerNumber = 40427469, codeBlockNumber = 7)
    +SimpleSnippet("First revision:")
    +StackOverflowSnippet(answerNumber = 4362605, codeBlockNumber = 2, revisionNumber = 1)
    +SimpleSnippet("Second revision:")
    +StackOverflowSnippet(answerNumber = 4362605, codeBlockNumber = 2, revisionNumber = 2)
}.buildWith("python2", "script.py")



