@ExperimentalSnippet
fun main() = createCode {
    SoLangConfiguration.apiToken = "" //YOUR TOKEN GOES HERE
    SoLangConfiguration.soLangMode = SoLangConfiguration.SoLangMode.PRINT
    +StackOverflowSnippet(40427469, 7)
    +SimpleSnippet("First revision:")
    +StackOverflowSnippet(4362605, 2, 1)
    +SimpleSnippet("Second revision:")
    +StackOverflowSnippet(4362605, 2, 2)
}.buildWith("python2", "script.py")



