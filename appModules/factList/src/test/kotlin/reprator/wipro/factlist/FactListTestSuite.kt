package reprator.wipro.factlist

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite
import reprator.wipro.factlist.data.repositoryImpl.FactListDataRepositoryImplTest
import reprator.wipro.factlist.datasource.remote.FactListApiServiceTest
import reprator.wipro.factlist.datasource.remote.FactListRemoteDataSourceImplTest
import reprator.wipro.factlist.datasource.remote.remotemapper.FactListMapperTest
import reprator.wipro.factlist.domain.usecase.FactListUseCaseTest

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(Suite::class)
@Suite.SuiteClasses(
    FactListDataRepositoryImplTest::class,
    FactListMapperTest::class,
    FactListApiServiceTest::class,
    FactListRemoteDataSourceImplTest::class,
    FactListUseCaseTest::class,
    FactListViewModalTest::class,
)
class FactListTestSuite