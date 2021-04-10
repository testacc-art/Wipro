package reprator.wipro.factlist.domain.usecase

import kotlinx.coroutines.flow.Flow
import reprator.wipro.base.useCases.AppResult
import reprator.wipro.factlist.domain.repository.FactListRepository
import reprator.wipro.factlist.modals.FactModals
import javax.inject.Inject

class FactListUseCase @Inject constructor(
    private val factListRepository: FactListRepository
) {
    suspend operator fun invoke(): Flow<AppResult<List<FactModals>>> {
        return factListRepository.getFactListRepository()
    }
}