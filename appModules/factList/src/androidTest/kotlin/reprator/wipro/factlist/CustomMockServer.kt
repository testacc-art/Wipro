package reprator.wipro.factlist

import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer

object CustomMockServer {
    val mockWebServer = MockWebServer()
    val httpUrl: HttpUrl

    init {
        mockWebServer.start()
        httpUrl = mockWebServer.url("/")
    }

}