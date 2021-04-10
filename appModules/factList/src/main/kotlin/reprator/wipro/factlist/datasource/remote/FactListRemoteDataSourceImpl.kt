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