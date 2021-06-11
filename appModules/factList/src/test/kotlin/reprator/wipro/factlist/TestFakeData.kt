package reprator.wipro.factlist

import reprator.wipro.factlist.datasource.remote.modal.FactListEntity
import reprator.wipro.factlist.datasource.remote.modal.Row
import reprator.wipro.factlist.modals.FactModals

object TestFakeData {

    private const val TITLE = "About India"
    private const val ITEM_TITLE = "A"
    private const val ITEM_DESCRIPTION = "Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony"
    private const val ITEM_URL = "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"

    fun getFakeRemoteDataList(): FactListEntity {
        val rowList = listOf(Row(ITEM_TITLE, ITEM_DESCRIPTION, ITEM_URL))
        return FactListEntity(TITLE, rowList);
    }

    fun getFakeManipulatedRemoteDataList(): Pair<String, List<FactModals>> {
        val rowList = listOf(FactModals(ITEM_TITLE, ITEM_DESCRIPTION, ITEM_URL))
        return Pair(TITLE, rowList);
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