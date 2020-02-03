import java.io.File
import java.io.IOException

internal class SoCompiler(internal val command: String) {
    val directory = "intermediate_files" //TODO used directory there and in command creation; per project directory, user specified

    internal fun compile() {
        runCommand(command)
        createdFiles.clear()
    }

    internal fun save(targetFile: String, code: SoCode) {
        val file = File(targetFile)
        createdFiles += file
        file.writeText(code.code)
    }

    internal fun filesToString(): String = createdFiles.joinToString("\n\n\n") {
        "File: " + it.name + "\nContent:\n" + it.readText()
    }


    private fun runCommand(command: String) = try {
        val parts = command.split("\\s".toRegex())
        val proc = ProcessBuilder(*parts.toTypedArray())
            .inheritIO()
            .start()
        proc.waitFor()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    companion object { //TODO when only one compiler instance is used per project, move these fields to class, CREATE PROJECT CLASS
        val createdFiles: MutableList<File> = mutableListOf()
    }
}