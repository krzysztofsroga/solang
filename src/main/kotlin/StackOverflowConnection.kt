import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitString
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object StackOverflowConnection {

    private const val stackOverflowUrl = "http://api.stackexchange.com/2.2/posts"

    init {
        configureFuel()
    }

    private fun configureFuel() {
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        FuelManager.instance.basePath = stackOverflowUrl
    }

    internal val getAnswerBody = ::downloadAnswerBody.memSuspend()

    internal val getAnswerAllRevisionBodies = ::downloadAnswerAllRevisionBodies.memSuspend()

    private suspend fun downloadAnswerBody(answerNumber: Int): String {
        val request = Fuel.get(
            answerNumber.toString(), listOf(
                "site" to "stackoverflow",
                "filter" to "withbody"
            )
        )
        val responseString = request.awaitString()
//  val obj = Json.nonstrict.parse<ResponseModel<AnswerModel>(responseString) TODO change to this when no longer experimental
        val obj = Json.nonstrict.parse(ResponseModel.serializer(AnswerModel.serializer()), responseString)
        return obj.items.first().body
    }

    private suspend fun downloadAnswerAllRevisionBodies(answerNumber: Int): Map<Int, String> {
        val request = Fuel.get(
            "$answerNumber/revisions", listOf(
                "site" to "stackoverflow",
                "filter" to "withbody"
            )
        )
        val responseString = request.awaitString()
        val obj = Json.nonstrict.parse(ResponseModel.serializer(AnswerRevisionModel.serializer()), responseString)
        return mapOf(*obj.items.map { it.revision_number to it.body }.toTypedArray())
    }

    @Serializable
    private data class ResponseModel<T>(val items: List<T>)

    @Serializable
    private data class AnswerModel(val body: String)

    @Serializable
    private data class AnswerRevisionModel(val body: String, val revision_number: Int)
}
