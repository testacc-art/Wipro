package reprator.wipro.factlist.datasource.remote

import reprator.wipro.factlist.datasource.remote.modal.FactListEntity
import retrofit2.Response
import retrofit2.http.GET

interface FactListApiService {
    @GET("2iodh4vg0eortkl/facts.json")
    suspend fun factList(): Response<FactListEntity>
}
