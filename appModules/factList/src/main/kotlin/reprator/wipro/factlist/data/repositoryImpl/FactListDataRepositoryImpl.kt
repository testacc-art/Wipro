package reprator.wipro.factlist.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import reprator.wipro.base.useCases.AppResult
import reprator.wipro.base.util.interent.ConnectionDetector
import reprator.wipro.factlist.domain.repository.FactListRepository
import reprator.wipro.factlist.modals.FactModals
import javax.inject.Inject

class FactListDataRepositoryImpl @Inject constructor(
    private val connectionDetector: ConnectionDetector
) : FactListRepository {

    override suspend fun getFactListRepository(): Flow<AppResult<List<FactModals>>> {
        TODO("Not yet implemented")
    }

}