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

package reprator.wipro.factlist.datasource.remote.remotemapper

import reprator.wipro.base.util.Mapper
import reprator.wipro.factlist.datasource.remote.modal.FactListEntity
import reprator.wipro.factlist.modals.FactModals
import javax.inject.Inject

class FactListMapper @Inject constructor() :
    Mapper<FactListEntity, Pair<String, List<FactModals>>> {
    override suspend fun map(from: FactListEntity): Pair<String, List<FactModals>> {
        return Pair(
            from.title,
            from.rows.map {
                FactModals(it.title ?: "", it.description ?: "", it.imageHref ?: "")
            }
        )
    }
}
