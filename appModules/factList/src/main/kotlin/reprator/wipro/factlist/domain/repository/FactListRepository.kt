package reprator.wipro.factlist.domain.repository

import kotlinx.coroutines.flow.Flow
import reprator.wipro.base.useCases.AppResult
import reprator.wipro.factlist.modals.FactModals

interface FactListRepository {
    suspend fun getFactListRepository():
            Flow<AppResult<Pair<String, List<FactModals>>>>
}