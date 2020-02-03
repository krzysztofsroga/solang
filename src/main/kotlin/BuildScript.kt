class SimpleBuildCommand(private val command: String) {
    fun bulid(outputMode: OutputMode = OutputMode.INHERIT): String {
        return SoCompiler(command).build(outputMode)
    }
}

enum class OutputMode {
    INHERIT,
    CATCH
}