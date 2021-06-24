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

package reprator.wipro.factlist.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import reprator.wipro.base.useCases.AppError
import reprator.wipro.base.useCases.AppResult
import reprator.wipro.base.util.interent.ConnectionDetector
import reprator.wipro.factlist.data.datasource.FactListRemoteDataSource
import reprator.wipro.factlist.domain.repository.FactListRepository
import reprator.wipro.factlist.modals.FactModals
import javax.inject.Inject

class FactListDataRepositoryImpl @Inject constructor(
    private val connectionDetector: ConnectionDetector,
    private val factListRemoteDataSource: FactListRemoteDataSource
) : FactListRepository {

    companion object {
        private const val NO_INTERNET = "No internet connection detected."
    }

    override suspend fun getFactListRepository(): Flow<AppResult<Pair<String, List<FactModals>>>> {
        return if (connectionDetector.isInternetAvailable)
            flowOf(factListRemoteDataSource.getFacListRemoteDataSource())
        else {
            flowOf(AppError(message = NO_INTERNET))
        }
    }
}
