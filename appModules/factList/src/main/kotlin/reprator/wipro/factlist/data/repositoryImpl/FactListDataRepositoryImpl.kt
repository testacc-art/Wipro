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

    override suspend fun getFactListRepository(): Flow<AppResult<List<FactModals>>> {
        return if (connectionDetector.isInternetAvailable)
            flowOf(factListRemoteDataSource.getFacListRemoteDataSource())
        else {
            flowOf(AppError(message = NO_INTERNET))
        }
    }

}