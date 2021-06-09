package reprator.wipro.factlist.test

import androidx.test.espresso.IdlingRegistry
import androidx.test.filters.MediumTest
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
import reprator.wipro.factlist.screen.FactListScreen
import reprator.wipro.factlist.util.OkHttpProvider
import reprator.wipro.factlist.util.dispatcherWithCustomBody
import reprator.wipro.factlist.util.launchFragmentInHiltContainer

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class FactListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        hiltRule.inject()

        mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                OkHttpProvider.getOkHttpClient()
            )
        )
        mockWebServer.dispatcher = dispatcherWithCustomBody()

        launchFragmentInHiltContainer<Factlist> {
            this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                }
            }
        }
    }

    @Test
    fun recyclerview_second_item_should_be_visible() {
        onScreen<FactListScreen> {
            moviesRv {
                childAt<FactListScreen.Item>(2) {
                    title {
                        isVisible()
                        //hasAnyText()
                    }
                    image { isVisible() }
                }
            }
        }
    }

    @After
    fun cleanup() {
        mockWebServer.close()
    }
}
