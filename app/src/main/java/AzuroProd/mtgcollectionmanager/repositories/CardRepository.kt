package AzuroProd.mtgcollectionmanager.repositories

import AzuroProd.mtgcollectionmanager.network.ScryfallSearchResponse

interface CardRepository {
    fun getCards(ids: List<Int>)
    suspend fun searchCards(query: String) : ScryfallSearchResponse
}