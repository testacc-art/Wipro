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
import reprator.wipro.factlist.dispatcherWithEmptyBody
import reprator.wipro.factlist.dispatcherWithErrorTimeOut
import reprator.wipro.factlist.screen.FactListKaspressoScreen
import reprator.wipro.factlist.util.launchFragmentInHiltContainer
import javax.inject.Inject

@HiltAndroidTest
class FactListKaspressoTest : TestCase() {

    companion object {
        const val TOTAL_ITEM = 14
        const val SCREEN_TITLE = "About India"
    }
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
    fun `load_item_successfully_in_recyclerview`() =
        before {
            testLogger.i("Before section successfull")
        }.after {
            testLogger.i("After section successfull")
        }.run {
            step("Open App and show Toolbar") {
                testLogger.i("Main section")

                FactListKaspressoScreen {
                    toolBar {
                        isVisible()
                        isCompletelyDisplayed()
                        hasTitle(SCREEN_TITLE)
                    }

                    factList {

                        hasSize(TOTAL_ITEM)

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
    fun load_item_successfully_in_recyclerview_with_error_on_pullToRefresh() =
        before {
            testLogger.i("Before section pullToRefresh")
        }.after {
            testLogger.i("After section pullToRefresh")
        }.run {
            step("show list items with pull to refresh") {

                FactListKaspressoScreen {

                    factList.hasSize(TOTAL_ITEM)

                    mockWebServer.dispatcher = dispatcherWithErrorTimeOut()

                    swipeToRefresh {
                        isDisplayed()
                        swipeDown()
                    }
                }
            }

            step("verify error with snackbar") {

                FactListKaspressoScreen {
                    snackbar {
                        isDisplayed()
                        text.hasText("timeout")
                    }

                }
            }
        }


    @Test
    fun loadErrorViewOnLaunch_withSuccessfulReload() =
        before {
            testLogger.i("Before section loadErrorView")
            mockWebServer.dispatcher = dispatcherWithErrorTimeOut()
        }.after {
            testLogger.i("After section loadErrorView")
        }.run {
            step("show error view with reload button click") {

                FactListKaspressoScreen {

                    factList.isNotDisplayed()

                    mockWebServer.dispatcher = dispatcherWithCustomBody()

                    errorRetry {
                        isDisplayed()
                        click()
                    }
                }
            }

            step("verify items in recyclerview") {

                FactListKaspressoScreen {
                    factList {
                        hasSize(TOTAL_ITEM)
                        isDisplayed()
                    }

                }
            }
        }

    @Test
    fun loadEmptyViewOnLaunch() =
        before {
            testLogger.i("Before section empty")
            mockWebServer.dispatcher = dispatcherWithEmptyBody()
        }.after {
            testLogger.i("After section empty")
        }.run {
            step("show empty view") {

                FactListKaspressoScreen {

                    factList.isNotDisplayed()
                    empty {
                        isDisplayed()
                    }
                }
            }
        }


    @Test
    fun scroll_check_Position7() =
        before {
            testLogger.i("Before section scroll & check position 7")
        }.after {
            testLogger.i("After section scroll & check position 7")
        }.run {
            step("Open App") {
                testLogger.i("Main section")

                FactListKaspressoScreen {

                    factList {

                        scrollTo(7)
                        childAt<FactListKaspressoScreen.Item>(7) {

                            title {
                                hasEmptyText()
                            }
                            description {
                                hasEmptyText()
                            }
                            image {
                                isDisplayed()
                            }
                        }
                    }
                }
            }
        }
}
