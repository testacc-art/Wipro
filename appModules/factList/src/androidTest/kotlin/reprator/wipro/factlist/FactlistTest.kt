package reprator.wipro.factlist

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import reprator.wipro.factlist.di.FactListModule
import reprator.wipro.factlist.util.EspressoUriIdlingResource
import reprator.wipro.factlist.util.launchFragmentInHiltContainer

@MediumTest
@UninstallModules(FactListModule::class)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FactlistTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoUriIdlingResource.uriIdlingResource)
    }

    @After
    fun unregister() {
        IdlingRegistry.getInstance().unregister(EspressoUriIdlingResource.uriIdlingResource)
    }

    /**
     * Test to check, that when the fragment launches,
     * then the list of news is displayed on the screen.
     */
    @Test
    fun start_showNews() {
        launchFragmentInHiltContainer<Factlist>(Bundle(), R.style.Theme_Wipro)
        onView(withId(R.id.factListRecyclerView)).check(matches(isDisplayed()))
    }
}