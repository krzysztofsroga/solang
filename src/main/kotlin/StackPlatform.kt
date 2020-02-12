enum class StackPlatform(val platform: String) { //TODO allow customized platform (non-enum)
    StackOverflowPlatform("stackoverflow"),
    CodeGolfPlatform("codegolf"),
    LaTeXPlatform("tex"),
    ArcadePlatform("gaming"),
    ServerFaultPlatform("serverfault"),
    MathematicsPlatform("math"),
    EnglishPlatform("english"),
    MetaPlatform("meta"),
    BlenderPlatform("blender"),
    SuperUserPlatform("superuser"),
    AskUbuntuPlatform("ubuntu"),
    ;

    val parameters: Pair<String, String>
        get() = "site" to platform
}