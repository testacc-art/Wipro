package reprator.wipro.factlist.test

import androidx.test.espresso.IdlingRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import reprator.wipro.factlist.Factlist
import reprator.wipro.factlist.dispatcherWithCustomBody
import reprator.wipro.factlist.dispatcherWithErrorTimeOut
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


    private fun setUp() {
        hiltRule.inject()

        mockWebServer.start(8080)

        IdlingRegistry.getInstance().register(okHttp3IdlingResource)

        mockWebServer.dispatcher = dispatcherWithCustomBody()
    }

    private fun cleanup() {
        mockWebServer.close()
        IdlingRegistry.getInstance().unregister(okHttp3IdlingResource)
    }

    @Test
    fun `load_item_successfully_in_recyclerview`() =
        before {
            setUp()
            testLogger.i("Before section")
        }.after {
            cleanup()
            testLogger.i("After section")
        }.run {
            step("Open App and show Toolbar") {
                testLogger.i("Main section")

                launchFragmentInHiltContainer<Factlist>()

                FactListKaspressoScreen {
                    toolBar {
                        isVisible()
                        isCompletelyDisplayed()
                        hasTitle("About India")
                    }

                    factList {

                        hasSize(14)

                        firstChild<FactListKaspressoScreen.Item> {
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

                        lastChild<FactListKaspressoScreen.Item> {
                            title {
                                hasText("Last Item")
                            }
                            description {
                                hasText("Last Description")
                            }
                            image {
                                isDisplayed()
                            }
                        }
                    }
                }
            }
        }


    @Test
    fun `load_test`() =
        before {
            setUp()
            testLogger.i("Before section")
        }.after {
            cleanup()
            testLogger.i("After section")
        }.run {
            step("Open App and show Toolbar") {
                testLogger.i("Main section")

                launchFragmentInHiltContainer<Factlist>()

                FactListKaspressoScreen {

                    factList.hasSize(14)

                    mockWebServer.dispatcher = dispatcherWithErrorTimeOut()
                    swipeToRefresh {
                        isDisplayed()
                        swipeDown()
                    }

                    snackbar {
                        isDisplayed()
                        text.hasText("timeout")
                    }
                }
            }
        }
}
