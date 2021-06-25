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

package reprator.wipro.factlist

import reprator.wipro.factlist.datasource.remote.modal.FactListEntity
import reprator.wipro.factlist.datasource.remote.modal.Row
import reprator.wipro.factlist.modals.FactModals

object TestFakeData {

    private const val TITLE = "About India"
    private const val ITEM_TITLE = "A"
    private const val ITEM_DESCRIPTION = "Beavers are second only to " +
            "humans in their ability to manipulate and change their " +
            "environment. They can measure up to 1.3 metres long. " +
            "A group of beavers is called a colony"
    private const val ITEM_URL = "http://upload.wikimedia.org/wikipedia/commons" +
            "/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"

    fun getFakeRemoteDataList(): FactListEntity {
        val rowList = listOf(Row(ITEM_TITLE, ITEM_DESCRIPTION, ITEM_URL))
        return FactListEntity(TITLE, rowList)
    }

    fun getFakeManipulatedRemoteDataList(): Pair<String, List<FactModals>> {
        val rowList = listOf(FactModals(ITEM_TITLE, ITEM_DESCRIPTION, ITEM_URL))
        return Pair(TITLE, rowList)
    }

    fun getFakeManipulatedUIItem(): FactModals {
        return FactModals(ITEM_TITLE, ITEM_DESCRIPTION, ITEM_URL)
    }

    fun getFakeManipulatedUIListItem(): List<FactModals> {
        return listOf(FactModals(ITEM_TITLE, ITEM_DESCRIPTION, ITEM_URL))
    }

    fun getFakeManipulatedUITitle(): String {
        return TITLE
    }
}
