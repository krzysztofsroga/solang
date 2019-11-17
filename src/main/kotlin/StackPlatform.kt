enum class StackPlatform(val platform: String) { //TODO allow customized platform (non-enum)
    StackOverflowPlatform("stackoverflow"),
    CodeGolfPlatform("codegolf");

    val parameters: Pair<String, String>
        get() = "site" to platform
}