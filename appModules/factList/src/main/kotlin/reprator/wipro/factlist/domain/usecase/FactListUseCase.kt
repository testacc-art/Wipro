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

package reprator.wipro.factlist.domain.usecase

import kotlinx.coroutines.flow.Flow
import reprator.wipro.base.useCases.AppResult
import reprator.wipro.factlist.domain.repository.FactListRepository
import reprator.wipro.factlist.modals.FactModals
import javax.inject.Inject

class FactListUseCase @Inject constructor(
    private val factListRepository: FactListRepository
) {
    suspend operator fun invoke(): Flow<AppResult<Pair<String, List<FactModals>>>> {
        return factListRepository.getFactListRepository()
    }
}
