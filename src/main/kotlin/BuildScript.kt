class SimpleBuildCommand(private val command: String) {
    fun bulid() {
        SoCompiler(command).build()
    }
}