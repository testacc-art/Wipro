package reprator.wipro.factlist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECTION_TIME = 90L

@Module
@InstallIn(SingletonComponent::class)
class TestFactListModule {

    @Provides
    fun provideOkHttpAuth(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
            .writeTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    fun provideRetrofit(
        converterFactory: JacksonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}
