package reprator.wipro.factlist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import reprator.wipro.base.util.interent.ConnectionDetector
import reprator.wipro.factlist.data.datasource.FactListRemoteDataSource
import reprator.wipro.factlist.data.repositoryImpl.FactListDataRepositoryImpl
import reprator.wipro.factlist.datasource.remote.FactListApiService
import reprator.wipro.factlist.datasource.remote.FactListRemoteDataSourceImpl
import reprator.wipro.factlist.datasource.remote.remotemapper.FactListMapper
import reprator.wipro.factlist.domain.repository.FactListRepository
import reprator.wipro.factlist.domain.usecase.FactListUseCase
import retrofit2.Retrofit

@InstallIn(ViewModelComponent::class)
@Module
class FactListModule {

    @Provides
    fun provideFactListRemoteDataSource(
        factListApiService: FactListApiService,
        factListMapper: FactListMapper
    ): FactListRemoteDataSource {
        return FactListRemoteDataSourceImpl(
            factListApiService,
            factListMapper
        )
    }

    @Provides
    fun provideFactListRepository(
        factListRemoteDataSource: FactListRemoteDataSource,
        connectionDetector: ConnectionDetector,
    ): FactListRepository {
        return FactListDataRepositoryImpl(
            connectionDetector,
            factListRemoteDataSource
        )
    }

    @Provides
    fun provideFactListUseCase(
        factListRepository: FactListRepository
    ): FactListUseCase {
        return FactListUseCase(factListRepository)
    }

    @Provides
    fun provideFactListApiService(
        retrofit: Retrofit
    ): FactListApiService {
        return retrofit
            .create(FactListApiService::class.java)
    }
}