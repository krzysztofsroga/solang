import SoLangConfiguration.SoLangMode.*
import SoLangConfiguration.soLangMode

@Experimental
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class ExperimentalSnippet


fun SoCode.buildWith(buildScript: String, targetFile: String) {
    val command = "$buildScript $targetFile"

    when (soLangMode) {
        UNSAFE -> {
            println("Code will be saved in file: $targetFile")
            SoCompiler(this, command).compile(targetFile)
        }
        SAFE -> {
            println("Code will be saved in file: $targetFile")
            if (code.promptOk("code") && command.promptOk("build command")) {
                SoCompiler(this, command).compile(targetFile)
            } else {
                println("Build canceled.")
            }
        }
        PRINT -> {
            println("Build command: $command")
            println("Code:\n$code")
        }
    }
}

internal fun String.promptOk(what: String): Boolean {
    println("This is $what:")
    println(this)
    print(":: Does $what look okay? [y/N] ")
    val input = readLine()
    return input != null && input.isNotEmpty() && input.first().toLowerCase() == 'y'
}



internal fun<T> Sequence<T>.getElement(n: Int): T = toList()[n]
//internal fun<T> Sequence<T>.getElement(n: Int): T = take(n+1).last()//more efficient than toList()[n] because it skips all further elements