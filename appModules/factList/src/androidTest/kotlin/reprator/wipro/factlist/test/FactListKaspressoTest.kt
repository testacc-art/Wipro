package reprator.wipro.factlist.test

import androidx.test.espresso.IdlingRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import reprator.wipro.factlist.Factlist
import reprator.wipro.factlist.dispatcherWithCustomBody
import reprator.wipro.factlist.screen.FactListKaspressoScreen
import reprator.wipro.factlist.util.launchFragmentInHiltContainer
import javax.inject.Inject

@HiltAndroidTest
class FactListKaspressoTest : TestCase() {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    private val mockWebServer = MockWebServer()

    @Inject
    lateinit var okHttp3IdlingResource: OkHttp3IdlingResource

    @Before
    fun setUp() {
        hiltRule.inject()

        mockWebServer.start(8080)

        IdlingRegistry.getInstance().register(okHttp3IdlingResource)

        mockWebServer.dispatcher = dispatcherWithCustomBody()

        launchFragmentInHiltContainer<Factlist>()
    }

    @After
    fun cleanup() {
        mockWebServer.close()
        IdlingRegistry.getInstance().unregister(okHttp3IdlingResource)
    }

    @Test
    fun initialScreenTest() =
        before {
            testLogger.i("Before section initial test")
        }.after {
            testLogger.i("After section initial test")
        }.run {
            step("Open App") {
                testLogger.i("Main section")

                FactListKaspressoScreen {

                    progress {
                        isDisplayed()
                    }
                }
            }
        }
}
