package reprator.wipro.factlist

import android.os.Bundle
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import reprator.wipro.factlist.util.launchFragmentInHiltContainer

@ExperimentalCoroutinesApi
@HiltAndroidTest
class FactlistTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun hiltTest() {
        launchFragmentInHiltContainer<Factlist>(Bundle(), R.style.Theme_Wipro)
    }
}