fun String.buildWith(buildScript: String, targetFile: String) {
    SoCompiler(this, "$buildScript $targetFile").compile(targetFile)
}