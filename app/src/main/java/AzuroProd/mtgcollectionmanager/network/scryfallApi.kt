package AzuroProd.mtgcollectionmanager.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.Interceptor

private val retrofit = Retrofit.Builder()
    .baseUrl("https://api.scryfall.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface ScryfallApiService {
    @GET("cards/search?order=edhrec&")
    suspend fun search(
        @Query("q") query: String
    ): ScryfallSearchResponse
}

object ScryfallApi {
    val retrofitService: ScryfallApiService by lazy {
        retrofit.create(ScryfallApiService::class.java)
    }
}
