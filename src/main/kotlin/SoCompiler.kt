import java.io.File
import java.io.IOException

internal class SoCompiler(val code: String, val command: String) {

    internal fun compile(targetFile: String) { //todo create also function which will return output as string
        val file = File(targetFile)
        file.writeText(code)
        runCommand(command)
    }

    private fun runCommand(command: String) = try {
        val parts = command.split("\\s".toRegex())
        val proc = ProcessBuilder(*parts.toTypedArray())
            .inheritIO()
            .start()
        proc.waitFor()
    } catch(e: IOException) {
        e.printStackTrace()
    }
}