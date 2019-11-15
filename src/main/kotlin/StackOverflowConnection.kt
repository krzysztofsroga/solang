import SoLangConfiguration.apiToken
import SoLangConfiguration.stackAuthenticationData
import SoLangConfiguration.stackConnectionParameters
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitString
import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@UnstableDefault
object StackOverflowConnection {

    private const val stackExchangeApiUrl = "https://api.stackexchange.com/"
    private const val stackExchangeApiVersion = 2.2

    init {
        configureFuel()
    }

    private val parameters: List<Pair<String, String>> = stackConnectionParameters
        get() = if (apiToken.isEmpty()) field else field + stackAuthenticationData

    private fun configureFuel() {
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        FuelManager.instance.basePath = stackExchangeApiUrl + stackExchangeApiVersion
    }

    internal val getAnswerBody = ::downloadAnswerBody.memSuspend()

    internal val getAnswerAllRevisionBodies = ::downloadAnswerAllRevisionBodies.memSuspend()

    internal val getPostsTagged = ::downloadPostsTagged.memSuspend()

    private suspend fun downloadAnswerBody(answerNumber: Int): String {
        val request = Fuel.get("posts/$answerNumber", parameters)
        val responseString = request.awaitString()
        val obj = Json.nonstrict.parse(ResponseModel.serializer(AnswerModel.serializer()), responseString)
        return obj.items.first().body
        // TODO when no longer experimental change it to Json.nonstrict.parse<ResponseModel<AnswerModel>>(responseString)
    }

    private suspend fun downloadAnswerAllRevisionBodies(answerNumber: Int): Map<Int, String> {
        val request = Fuel.get("posts/$answerNumber/revisions", parameters)
        val responseString = request.awaitString()
        val obj = Json.nonstrict.parse(ResponseModel.serializer(AnswerRevisionModel.serializer()), responseString)
        return mapOf(*obj.items.map { it.revision_number to it.body }.toTypedArray())
    }

    private suspend fun downloadPostsTagged(/*tags: Collection<String>, */numberOfResults: Int = 10): List<Int> {
        val additionalParameters = listOf(
            "tagged" to arrayOf("javascript", "sorting").joinToString(";"),
            "page" to 1,
            "sort" to "activity",
            "pagesize" to numberOfResults,
            "order" to "desc"
        )
        val request = Fuel.get("questions", parameters + additionalParameters)
        val responseString = request.awaitString()
        val obj = Json.nonstrict.parse(ResponseModel.serializer(SearchResult.serializer()), responseString)
        return obj.items.mapNotNull { it.accepted_answer_id }
        //questions?sort=activity&tagged=sort;javascript&page=1&pagesize=100&order=desc&site=stackoverflow
    }

    private suspend fun getSearchResults(searchPhrase: String, numberOfResults: Int = 10) {

    }

    @Serializable
    private data class ResponseModel<T>(val items: List<T>)

    @Serializable
    private data class AnswerModel(val body: String)

    @Serializable
    private data class AnswerRevisionModel(val body: String, val revision_number: Int)

    @Serializable
    data class SearchResult(val question_id: Int, val title: String, val is_answered: Boolean, val tags: Collection<String>, val accepted_answer_id: Int? = null) //TODO private
}
