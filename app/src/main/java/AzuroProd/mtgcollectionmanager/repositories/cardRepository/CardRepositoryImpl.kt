package AzuroProd.mtgcollectionmanager.repositories.cardRepository

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

    override suspend fun autoComplete(query: String): ScryfallSearchResponse {
        return scryfallApi.autoComplete(query)
    }

    companion object{
        const val TAG = "CardRepositoryImpl"
    }
}