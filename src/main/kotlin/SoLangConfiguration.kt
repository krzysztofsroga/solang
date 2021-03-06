object SoLangConfiguration {

    var codeBlockIndices = Indices.START_FROM_1
//    var lineIndices = Indices.START_FROM_1 TODO implement

    var soLangMode = SoLangMode.SAFE

    var apiKey: String = "r9OD2Nz5AIxYCgo5IfyrLg(("
    var apiToken: String = ""

    internal val stackConnectionParameters = listOf(
//        "site" to "stackoverflow",
        "filter" to "withbody"
    )

    internal val stackAuthenticationData
        get() = listOf(
            "access_token" to apiToken,
            "key" to apiKey
        )

    enum class Indices(val value: Int) {
        START_FROM_0(0),
        START_FROM_1(1)
    }

    enum class SoLangMode {
        UNSAFE,
        SAFE,
        PRINT
    }

    fun generateParameters(
        site: String = "stackoverflow",
        withBody: Boolean = true
    ): List<Pair<String, String>> {
        return listOf(
            "site" to site,
            "filter" to if (withBody) "withbody" else ""
        )
        // TODO authentication etc
    }
}