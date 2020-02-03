class SimpleBuildCommand(private val command: String) {
    fun bulid() {
        SoCompiler.runCommand(command)
    }
}