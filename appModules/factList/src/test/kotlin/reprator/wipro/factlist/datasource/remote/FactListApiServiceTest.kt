package reprator.wipro.factlist.datasource.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.wipro.base.util.network.bodyOrThrow
import reprator.wipro.factlist.dispatcherWithCustomBody
import reprator.wipro.factlist.dispatcherWithErrorTimeOut
import retrofit2.Retrofit
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class FactListApiServiceTest {

    companion object {
        private const val COUNT = 14
        private const val TITLE = "About India"
        private const val ITEM_TITLE = "A"
        private const val ITEM_DESCRIPTION =
            "Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony"
        private const val ITEM_URL =
            "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
        private const val REQUEST_PATH = "/2iodh4vg0eortkl/facts.json"
    }

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: FactListApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS) // For testing purposes
            .readTimeout(2, TimeUnit.SECONDS) // For testing purposes
            .writeTimeout(2, TimeUnit.SECONDS)
            .build()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(createJacksonConverterFactory())
            .build()
            .create(FactListApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get fact list successfully`() = runBlocking {
        mockWebServer.dispatcher = dispatcherWithCustomBody()

        val factListEntity =
            service.factList().body()

        Truth.assertThat(factListEntity).isNotNull()

        val title = factListEntity!!.title
        Truth.assertThat(title).isEqualTo(TITLE)

        val factList = factListEntity.rows
        Truth.assertThat(factList.size).isEqualTo(COUNT)

        val factListItem = factListEntity.rows[0]
        Truth.assertThat(factListItem.title).isEqualTo(ITEM_TITLE)
        Truth.assertThat(factListItem.description).isEqualTo(ITEM_DESCRIPTION)
        Truth.assertThat(factListItem.imageHref).isEqualTo(ITEM_URL)
    }

    @Test
    fun `get fact list request check`() = runBlocking {
        mockWebServer.dispatcher = dispatcherWithCustomBody()

        service.factList().body()
        val request = mockWebServer.takeRequest()

        Truth.assertThat(request.requestUrl!!.pathSegments.size).isEqualTo(2)
        Truth.assertThat(request.requestUrl!!.pathSegments[0]).isEqualTo("2iodh4vg0eortkl")
        Truth.assertThat(request.requestUrl!!.pathSegments[1]).isEqualTo("facts.json")

        val queryParams = request.requestUrl?.queryParameterNames
        Truth.assertThat(queryParams).isEmpty()

        Truth.assertThat(request.path)
            .isEqualTo(REQUEST_PATH)
        Truth.assertThat(request.method).isEqualTo("GET")
    }

    @Test(expected = SocketTimeoutException::class)
    fun `Timeout example`(): Unit = runBlocking {
        mockWebServer.dispatcher = dispatcherWithErrorTimeOut()

        service.factList().bodyOrThrow()
    }
}