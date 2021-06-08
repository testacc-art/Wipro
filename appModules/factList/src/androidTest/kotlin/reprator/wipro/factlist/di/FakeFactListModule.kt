package reprator.wipro.factlist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import reprator.wipro.base.util.interent.ConnectionDetector
import reprator.wipro.factlist.data.datasource.FactListRemoteDataSource
import reprator.wipro.factlist.datasource.remote.FactListApiService
import reprator.wipro.factlist.datasource.remote.remotemapper.FactListMapper
import reprator.wipro.factlist.domain.repository.FactListRepository
import reprator.wipro.factlist.domain.usecase.FactListUseCase

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [FactListModule::class]
)
class FakeFactListModule {

    @Provides
    fun provideFactListRemoteDataSource(
        factListApiService: FactListApiService,
        factListMapper: FactListMapper
    ): FactListRemoteDataSource {
        return mockk()
    }

    @Provides
    fun provideFactListRepository(
        factListRemoteDataSource: FactListRemoteDataSource,
        connectionDetector: ConnectionDetector,
    ): FactListRepository {
        return mockk()
    }

    @Provides
    fun provideFactListUseCase(
        factListRepository: FactListRepository
    ): FactListUseCase {
        return mockk()
    }

    @Provides
    fun provideFactListApiService(
    ): FactListApiService {
        return mockk()
    }
}