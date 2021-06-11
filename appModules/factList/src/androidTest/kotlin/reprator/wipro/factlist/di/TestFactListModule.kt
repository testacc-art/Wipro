package reprator.wipro.factlist.di

import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class TestFactListModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideIdlingResource(okHttpClient: OkHttpClient): OkHttp3IdlingResource {
        return OkHttp3IdlingResource.create(
            "okhttp",
            okHttpClient
        )
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
