package reprator.wipro.factlist

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okhttp3.mockwebserver.SocketPolicy
import java.util.concurrent.TimeUnit

fun dispatcherWithCustomBody() = object : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        with(request.path) {
            val response = MockResponse().setResponseCode(200)
            return when {
                this?.contains("/2iodh4vg0eortkl/facts.json") == true ->
                    response
                        .setBody(FileReader.readTestResourceFile("factlist.json"))
                else ->
                    throw Exception("Wrong path")
            }
        }
    }
}

fun dispatcherWithErrorTimeOut() = object : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return MockResponse()
            .setSocketPolicy(SocketPolicy.NO_RESPONSE)
            .throttleBody(1, 2, TimeUnit.SECONDS)
    }
}
