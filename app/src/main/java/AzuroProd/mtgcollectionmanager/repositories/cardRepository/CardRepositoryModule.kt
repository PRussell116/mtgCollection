package AzuroProd.mtgcollectionmanager.repositories.cardRepository

import AzuroProd.mtgcollectionmanager.network.ScryfallApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CardRepositoryModule {
    @Provides
    @Singleton
    fun provideCardRepository(): CardRepository {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val userAgentInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val requestWithUserAgent = originalRequest.newBuilder()
                .header("Accept", "application/json")
                .header("User-Agent", "MtgCollectionManager/1.0 (Android App)")
                .build()
            chain.proceed(requestWithUserAgent)
        }
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(userAgentInterceptor)
            .build()

        return CardRepositoryImpl(
            scryfallApi = Retrofit.Builder()
                .baseUrl("https://api.scryfall.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
                .create(ScryfallApiService::class.java)
        )
    }
}