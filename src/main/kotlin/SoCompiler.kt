import java.io.File
import java.io.IOException

class SoCompiler(val code: String, val buildScript: String) {

    fun compile(targetFile: String) { //todo create also function which will return output as string
        val file = File(targetFile)
        file.writeText(code)
        runCommand(buildScript)
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