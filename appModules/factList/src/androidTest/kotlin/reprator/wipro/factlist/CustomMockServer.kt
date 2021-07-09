package reprator.wipro.factlist

import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer

class CustomMockServer {

    companion object {
        lateinit var httpUrl: HttpUrl
    }
    val mockWebServer = MockWebServer()

    init {
        mockWebServer.start()
        httpUrl = mockWebServer.url("/")
    }

}