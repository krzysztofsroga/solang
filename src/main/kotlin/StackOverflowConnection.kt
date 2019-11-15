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
    private const val pageSize = 100

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

//    internal val getPostsTagged = ::downloadPostsTagged.memSuspend()

    internal val getAcceptedAnswers = ::downloadAcceptedAnswers.memSuspend() //TODO memoization shouldn't take into account the second parameter - use greater one somehow (if possible)

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

    private suspend fun downloadPostsTagged(tags: Collection<String> = listOf("javascript", "sorting"), page: Int = 1): List<SearchResult> {
        println("Tags: $tags, downloading page $page...")
        val additionalParameters = listOf(
            "tagged" to tags.joinToString(";"),
            "page" to page,
            "sort" to "activity",
            "pagesize" to pageSize,
            "order" to "desc"
        )
        val request = Fuel.get("questions", parameters + additionalParameters)
        val responseString = request.awaitString()
        val obj = Json.nonstrict.parse(ResponseModel.serializer(SearchResult.serializer()), responseString)
        return obj.items
//        return obj.items.mapNotNull { it.accepted_answer_id }
        //questions?sort=activity&tagged=sort;javascript&page=1&pagesize=100&order=desc&site=stackoverflow
    }

    private suspend fun downloadAcceptedAnswers(tags: Collection<String> = listOf("javascript", "sorting"), numberOfResults: Int = 10): List<AnswerModel> {
        val additionalParameters = listOf(
            "sort" to "activity"
        )
//        val answerIds = downloadPostsTagged(tags).mapNotNull { it.accepted_answer_id }.joinToString{";"}
        val answerIds = mutableListOf<Int>()
        var page = 1
        while(answerIds.size < numberOfResults) {//TODO every response has 'has_more' field which says if there's another page. Throw exception if there's too little accepted answers
            answerIds += downloadPostsTagged(tags, page++).mapNotNull { it.accepted_answer_id }
        }
        val request = Fuel.get("answers/${answerIds.take(numberOfResults).joinToString(";")}", parameters + additionalParameters)
        val responseString = request.awaitString()
        val obj = Json.nonstrict.parse(ResponseModel.serializer(AnswerModel.serializer()), responseString)
        return obj.items

    }

    private suspend fun getSearchResults(searchPhrase: String, numberOfResults: Int = 25) {
        TODO()
    }

    @Serializable
    private data class ResponseModel<T>(val items: List<T>)

    @Serializable
    data class AnswerModel(val body: String) // TODO private

    @Serializable
    private data class AnswerRevisionModel(val body: String, val revision_number: Int)

    @Serializable
    data class SearchResult(
        val accepted_answer_id: Int? = null,
        val tags: Collection<String>,
        val title: String,
        val question_id: Int
    ) //TODO private
}
