import org.jsoup.Jsoup

class UnparsedPost(private val body: String) { //TODO this should be inline class

    fun getCodeBlocks(): List<String> = Jsoup.parse(body).select("pre > code").map { it.wholeText() } // TODO warning if no block found
    //        val firstSelection = doc.select("code").filter {it.parent().`is`("pre")}.first().wholeText()
    fun getRawData() = body //TODO delete this function
}