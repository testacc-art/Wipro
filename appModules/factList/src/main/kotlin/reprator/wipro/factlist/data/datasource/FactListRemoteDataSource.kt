package reprator.wipro.factlist.data.datasource

import reprator.wipro.base.useCases.AppResult
import reprator.wipro.factlist.modals.FactModals

interface FactListRemoteDataSource {
    suspend fun getFacListRemoteDataSource(): AppResult<Pair<String, List<FactModals>>>
}