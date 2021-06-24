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

package reprator.wipro.factlist.datasource.remote

import reprator.wipro.base.useCases.AppError
import reprator.wipro.base.useCases.AppResult
import reprator.wipro.base.useCases.AppSuccess
import reprator.wipro.base.util.network.safeApiCall
import reprator.wipro.base.util.network.toResult
import reprator.wipro.factlist.data.datasource.FactListRemoteDataSource
import reprator.wipro.factlist.datasource.remote.remotemapper.FactListMapper
import reprator.wipro.factlist.modals.FactModals
import javax.inject.Inject

class FactListRemoteDataSourceImpl @Inject constructor(
    private val factListApiService: FactListApiService,
    private val factListMapper: FactListMapper
) : FactListRemoteDataSource {

    private suspend fun getFactListRemoteDataSourceApi():
        AppResult<Pair<String, List<FactModals>>> {

            return when (val data = factListApiService.factList().toResult()) {
                is AppSuccess -> {
                    AppSuccess(factListMapper.map(data.data))
                }
                is AppError -> {
                    AppError(message = data.message, throwable = data.throwable)
                }
                else -> throw IllegalArgumentException()
            }
        }

    override suspend fun getFacListRemoteDataSource(): AppResult<Pair<String, List<FactModals>>> =
        safeApiCall(call = { getFactListRemoteDataSourceApi() })
}
