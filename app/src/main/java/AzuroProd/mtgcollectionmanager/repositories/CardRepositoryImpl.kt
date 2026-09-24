package AzuroProd.mtgcollectionmanager.repositories

import AzuroProd.mtgcollectionmanager.network.ScryfallApi
import AzuroProd.mtgcollectionmanager.network.ScryfallApiService
import AzuroProd.mtgcollectionmanager.network.ScryfallSearchResponse
import android.util.Log

class CardRepositoryImpl(
    val scryfallApi: ScryfallApiService

) : CardRepository {

    override fun getCards(ids: List<Int>) {
        TODO("Not yet implemented")
    }

    override suspend fun searchCards(query: String): ScryfallSearchResponse {
        val res = scryfallApi.search(query)
        Log.d(TAG, "searchCards: $res ")
        return res

    }

    companion object{
        const val TAG = "CardRepositoryImpl"
    }
}