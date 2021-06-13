package reprator.wipro.factlist.test

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.test.espresso.IdlingRegistry
import androidx.test.filters.MediumTest
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import reprator.wipro.factlist.Factlist
import reprator.wipro.factlist.dispatcherWithCustomBody
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

    private lateinit var contextTest: Context

    @Before
    fun setUp() {
        hiltRule.inject()

        mockWebServer.start(8080)

        IdlingRegistry.getInstance().register(okHttp3IdlingResource)

        mockWebServer.dispatcher = dispatcherWithCustomBody()

        launchFragmentInHiltContainer<Factlist>(){
            contextTest = requireContext()
        }
    }

    @Test
    fun recyclerview_second_item_should_be_visible() {

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
                        val drawable = ResourcesCompat.getDrawable(
                            contextTest.resources,
                            R.drawable.ic_error,
                            contextTest.theme
                        )
                        //hasDrawable(drawable!!)
                    }
                }
            }
        }
    }

    @After
    fun cleanup() {
        mockWebServer.close()
        IdlingRegistry.getInstance().unregister(okHttp3IdlingResource)
    }
}