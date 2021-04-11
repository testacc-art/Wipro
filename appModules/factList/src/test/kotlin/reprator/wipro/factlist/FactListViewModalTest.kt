package reprator.wipro.factlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.wipro.base.useCases.AppSuccess
import reprator.wipro.base_android.util.event.Event
import reprator.wipro.factlist.TestFakeData.getFakeManipulatedRemoteDataList
import reprator.wipro.factlist.domain.usecase.FactListUseCase
import reprator.wipro.factlist.modals.FactModals
import reprator.wipro.factlist.util.MainCoroutineRule
import reprator.wipro.factlist.util.onChangeExtension

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FactListViewModalTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val coroutinesTestRule = MainCoroutineRule()

    @MockK
    lateinit var factListUseCase: FactListUseCase

    lateinit var viewModal: FactListViewModal

    //create mockk object
    val observerLoad = mockk<Observer<Boolean>>()
    val observerError = mockk<Observer<String>>()
    val observerSuccessList = mockk<Observer<List<FactModals>>>()

    //For refresh
    val observerRefreshLoad = mockk<Observer<Boolean>>()
    val observerRefreshError = mockk<Observer<Event<String>>>()

    //create slot
    val slotLoad = slot<Boolean>()
    val slotError = slot<String>()
    val slotSuccess = slot<List<FactModals>>()

    val slotRefreshLoad = slot<Boolean>()
    val slotRefreshError = slot<Event<String>>()

    //create list to store values
    val listError = arrayListOf<String>()
    val listLoader = arrayListOf<Boolean>()
    val listSuccess = arrayListOf<List<FactModals>>()

    val listRefreshError = arrayListOf<Event<String>>()
    val listRefreshLoader = arrayListOf<Boolean>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModal = FactListViewModal(coroutinesTestRule.testDispatcherProvider, factListUseCase)

        observerLoad.onChangeExtension()
        observerError.onChangeExtension()
        observerSuccessList.onChangeExtension()

        observerRefreshLoad.onChangeExtension()
        observerRefreshError.onChangeExtension()

        //start observing
        viewModal.isLoading.observeForever(observerLoad)
        viewModal.errorMsg.observeForever(observerError)
        viewModal._factList.observeForever(observerSuccessList)

        viewModal._swipeLoading.observeForever(observerRefreshLoad)
        viewModal._swipeErrorMsg.observeForever(observerRefreshError)

        every {
            observerLoad.onChanged(capture(slotLoad))
        } answers {
            listLoader.add(slotLoad.captured)
        }

        every {
            observerError.onChanged(capture(slotError))
        } answers {
            listError.add(slotError.captured)
        }

        every {
            observerSuccessList.onChanged(capture(slotSuccess))
        } answers {
            listSuccess.add(slotSuccess.captured)
        }

        every {
            observerRefreshLoad.onChanged(capture(slotRefreshLoad))
        } answers {
            listRefreshLoader.add(slotRefreshLoad.captured)
        }

        every {
            observerRefreshError.onChanged(capture(slotRefreshError))
        } answers {
            listRefreshError.add(slotRefreshError.captured)
        }
    }

    @Test
    fun `get factList successfully on launch`() = coroutinesTestRule.runBlockingTest {

        val output = getFakeManipulatedRemoteDataList()

        coEvery {
            factListUseCase()
        } returns flowOf(AppSuccess(output))

        viewModal.getFactList()

        verifySequence {
            observerLoad.onChanged(any())               //Default Initialization
            observerSuccessList.onChanged(any())        //Default Initialization
            observerLoad.onChanged(any())
            observerSuccessList.onChanged(any())
            observerLoad.onChanged(any())
        }

        Truth.assertThat(listSuccess).isNotEmpty()
        Truth.assertThat(listSuccess).hasSize(output.second.size)
    }
}