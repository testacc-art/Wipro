/*
 * Copyright 2021 Vikram LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
