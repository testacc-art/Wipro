package reprator.wipro.factlist

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

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
