package reprator.wipro.factlist.datasource.remote.remotemapper

import reprator.wipro.base.util.Mapper
import reprator.wipro.factlist.datasource.remote.modal.FactListEntity
import reprator.wipro.factlist.modals.FactModals
import javax.inject.Inject

class FactListMapper @Inject constructor() :
    Mapper<FactListEntity, Pair<String, List<FactModals>>> {
    override suspend fun map(from: FactListEntity): Pair<String, List<FactModals>> {
        return Pair(from.title, from.rows.map {
            FactModals(it.title ?: "", it.description ?: "", it.imageHref ?: "")
        })
    }
}
