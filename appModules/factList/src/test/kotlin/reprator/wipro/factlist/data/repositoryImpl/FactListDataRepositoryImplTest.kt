package reprator.wipro.factlist.data.repositoryImpl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.wipro.base.useCases.AppError
import reprator.wipro.base.useCases.AppSuccess
import reprator.wipro.base.util.interent.ConnectionDetector
import reprator.wipro.factlist.TestFakeData.getFakeManipulatedRemoteDataList
import reprator.wipro.factlist.data.datasource.FactListRemoteDataSource
import reprator.wipro.factlist.domain.repository.FactListRepository
import reprator.wipro.factlist.util.MainCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FactListDataRepositoryImplTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val coroutinesTestRule = MainCoroutineRule()

    @MockK
    lateinit var factListRemoteDataSource: FactListRemoteDataSource

    @MockK
    lateinit var connectionDetector: ConnectionDetector

    lateinit var factListRepository: FactListRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)

        factListRepository = FactListDataRepositoryImpl(
            connectionDetector, factListRemoteDataSource)
    }

    @Test
    fun `get fact list from server, on internet connection available`() =
        coroutinesTestRule.runBlockingTest {

            val output = getFakeManipulatedRemoteDataList()

            coEvery {
                connectionDetector.isInternetAvailable
            } returns true

            coEvery {
                factListRemoteDataSource.getFacListRemoteDataSource()
            } returns AppSuccess(output)

            val result = factListRepository.getFactListRepository().single()

            Truth.assertThat(result).isInstanceOf(AppSuccess::class.java)
            Truth.assertThat(result.get()!!.second).hasSize(output.second.size)

            coVerifySequence {
                connectionDetector.isInternetAvailable
                factListRemoteDataSource.getFacListRemoteDataSource()
            }

            coVerify(atMost = 1) {
                connectionDetector.isInternetAvailable
                factListRemoteDataSource.getFacListRemoteDataSource()
            }
        }

    @Test
    fun `No internet available`() =
        coroutinesTestRule.runBlockingTest {

            val output = "No internet connection detected."

            coEvery {
                connectionDetector.isInternetAvailable
            } returns false

            val result = factListRepository.getFactListRepository().single()

            Truth.assertThat(result).isInstanceOf(AppError::class.java)
            Truth.assertThat((result as AppError).message).isEqualTo(output)
        }
}