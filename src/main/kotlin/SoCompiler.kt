import SoLangConfiguration.soLangMode
import SoLangConfiguration.SoLangMode.*
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

internal class SoCompiler(internal val command: String) {
    val directory = "intermediate_files" //TODO used directory there and in command creation; per project directory, user specified

    internal fun compile(outputMode: OutputMode): String {
        createdFiles.clear()
        return runCommand(command, outputMode)
    }

    internal fun save(targetFile: String, code: SoCode) {
        val file = File(targetFile)
        createdFiles += file
        file.writeText(code.code)
    }

    private fun filesToString(): String = createdFiles.joinToString("\n\n\n") {
        "File: " + it.name + "\nContent:\n" + it.readText()
    }

    internal fun build(outputMode: OutputMode): String {
        val files = filesToString()
        when (soLangMode) {
            UNSAFE -> {
                return compile(outputMode)
            }
            SAFE -> {
                if (files.promptOk("code") && command.promptOk("build command")) {
                    return compile(outputMode)
                } else {
                    println("Build canceled.")
                }
            }
            PRINT -> {
                println("Build command: $command")
                println("Code:\n$files")
            }
        }
        return ""
    }

    companion object {
        val createdFiles: MutableList<File> = mutableListOf() //TODO when only one compiler instance is used per project, move these fields to class, CREATE PROJECT CLASS

        internal fun runCommand(command: String, outputMode: OutputMode): String = try {
            val parts = command.split("\\s".toRegex())
            val procBuilder = ProcessBuilder(*parts.toTypedArray())
            when(outputMode) {
                OutputMode.INHERIT -> {
                    procBuilder.inheritIO().start().waitFor()
                    ""
                }
                OutputMode.CATCH -> {
                    val proc = procBuilder.start()
                    val i = BufferedReader(InputStreamReader(proc.inputStream))
                    proc.waitFor()
                    i.lineSequence().joinToString("\n")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            "ERROR"
        }
    }
}