package AzuroProd.mtgcollectionmanager.repositories.deckRepository

import AzuroProd.mtgcollectionmanager.database.Deck
import kotlinx.coroutines.flow.StateFlow

interface DeckRepository {
    suspend fun getAll(): List<Deck>
    suspend fun insertDeck(deck: Deck)
    suspend fun getNumberOfDecks(): Int
    val deckCount: StateFlow<Int>
}