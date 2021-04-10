package reprator.wipro.factlist.datasource.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import reprator.wipro.base.useCases.AppSuccess
import reprator.wipro.factlist.TestFakeData.getFakeManipulatedRemoteDataList
import reprator.wipro.factlist.TestFakeData.getFakeManipulatedUIItem
import reprator.wipro.factlist.TestFakeData.getFakeManipulatedUIListItem
import reprator.wipro.factlist.TestFakeData.getFakeManipulatedUITitle
import reprator.wipro.factlist.TestFakeData.getFakeRemoteDataList
import reprator.wipro.factlist.data.datasource.FactListRemoteDataSource
import reprator.wipro.factlist.datasource.remote.remotemapper.FactListMapper
import reprator.wipro.factlist.util.MainCoroutineRule
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

@RunWith(JUnit4::class)
class FactListRemoteDataSourceImplTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val coroutinesTestRule = MainCoroutineRule()

    @MockK
    lateinit var factListApiService: FactListApiService

    @MockK
    lateinit var factListMapper: FactListMapper

    private lateinit var factListRemoteDataSource: FactListRemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, true)

        factListRemoteDataSource = FactListRemoteDataSourceImpl(
            factListApiService,
            factListMapper
        )
    }

    @Test
    fun `fetch list successfully from server and map it to UI pojo`() = coroutinesTestRule.runBlockingTest {

        val output = getFakeManipulatedRemoteDataList()
        coEvery {
            factListApiService.factList()
        } returns Response.success(getFakeRemoteDataList())

        coEvery {
            factListMapper.map(any())
        } returns output

        val result = factListRemoteDataSource.getFacListRemoteDataSource()

        Truth.assertThat(result).isInstanceOf(AppSuccess::class.java)
        Truth.assertThat(result.get()).isEqualTo(output)

        Truth.assertThat(result.get()!!.first).isEqualTo(getFakeManipulatedUITitle())
        Truth.assertThat(result.get()!!.second).isEqualTo(getFakeManipulatedUIListItem())

        coVerifySequence {
            factListApiService.factList()
            factListMapper.map(any())
        }
    }

}