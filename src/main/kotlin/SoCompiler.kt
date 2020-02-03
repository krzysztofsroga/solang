import SoLangConfiguration.soLangMode
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

    private fun filesToString(): String = createdFiles.joinToString("\n\n\n") {
        "File: " + it.name + "\nContent:\n" + it.readText()
    }

    internal fun build() {
        val files = filesToString()
        when (soLangMode) {
            SoLangConfiguration.SoLangMode.UNSAFE -> {
                compile()
            }
            SoLangConfiguration.SoLangMode.SAFE -> {
                if (files.promptOk("code") && command.promptOk("build command")) {
                    compile()
                } else {
                    println("Build canceled.")
                }
            }
            SoLangConfiguration.SoLangMode.PRINT -> {
                println("Build command: ${command}")
                println("Code:\n$files")
            }
        }
    }

    companion object {
        val createdFiles: MutableList<File> = mutableListOf() //TODO when only one compiler instance is used per project, move these fields to class, CREATE PROJECT CLASS

        internal fun runCommand(command: String) = try {
            val parts = command.split("\\s".toRegex())
            val proc = ProcessBuilder(*parts.toTypedArray())
                .inheritIO()
                .start()
            proc.waitFor()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}