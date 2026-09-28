package AzuroProd.mtgcollectionmanager.repositories.cardRepository

import AzuroProd.mtgcollectionmanager.network.ScryfallSearchResponse

interface CardRepository {
    fun getCards(ids: List<Int>)
    suspend fun searchCards(query: String) : ScryfallSearchResponse

    suspend fun autoComplete(query:String) : ScryfallSearchResponse
}