package reprator.wipro.factlist.test

import androidx.core.content.res.ResourcesCompat
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import reprator.wipro.factlist.Factlist
import reprator.wipro.factlist.dispatcherWithCustomBody
import reprator.wipro.factlist.dispatcherWithErrorTimeOut
import reprator.wipro.factlist.screen.FactListScreen
import reprator.wipro.factlist.util.launchFragmentInHiltContainer
import javax.inject.Inject

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class FactListFragmentTest {

    @get:Rule
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

    @Test
    fun loadItemSuccessfullyInRecyclerview() {

        onScreen<FactListScreen> {

            factList {

                hasSize(14)

                firstChild<FactListScreen.Item> {
                    title {
                        isVisible()
                        hasText("A")
                    }
                    description {
                        isDisplayed()
                        hasText("First Item Description")
                    }
                    image {
                        isDisplayed()
                    }
                }

                scrollToEnd()

                lastChild<FactListScreen.Item> {
                    title {
                        hasText("Last Item")
                    }
                    description {
                        hasText("Last Description")
                    }
                    image {
                        val appContext = InstrumentationRegistry.getInstrumentation().context!!

                        val drawable = ResourcesCompat.getDrawable(
                            appContext.resources,
                            R.drawable.ic_error,
                            appContext.theme
                        )
                        // hasDrawable(drawable!!)
                    }
                }
            }
        }
    }

    @Test
    fun showErrorView_retry() {
        mockWebServer.dispatcher = dispatcherWithErrorTimeOut()

        onScreen<FactListScreen> {

            factList {
                isNotDisplayed()
            }

            mockWebServer.dispatcher = dispatcherWithCustomBody()

            errorRetry {
                isDisplayed()
                click()
            }

            factList {
                isDisplayed()
            }
        }
    }

    @Test
    fun swipeToRefreshTest() {
        onScreen<FactListScreen> {

            Thread.sleep(2000)
            swipeToRefresh {
                isVisible()
                swipeDown()
            }
        }
    }

    @Test
    fun toolbarTest() {
        onScreen<FactListScreen> {
            toolBar {
                isDisplayed()
                hasTitle("About India")
            }
        }
    }

    @After
    fun cleanup() {
        mockWebServer.close()
        IdlingRegistry.getInstance().unregister(okHttp3IdlingResource)
    }
}