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

    private suspend fun downloadAnswerBody(answerNumber: Int): String {
        val request = Fuel.get(
            answerNumber.toString(), listOf(
                "site" to "stackoverflow",
                "filter" to "withbody"
            )
        )
        val responseString = request.awaitString()
//  val obj = Json.nonstrict.parse<MyModel>(responseString) TODO change to this when no longer experimental
        val obj = Json.nonstrict.parse(MyModel.serializer(), responseString)
        return obj.items.first().body
    }

    @Serializable
    private data class MyModel(val items: List<MyModel2>)

    @Serializable
    private data class MyModel2(val body: String)
}
