package AzuroProd.mtgcollectionmanager.repositories.deckRepository

import AzuroProd.mtgcollectionmanager.database.Deck
import AzuroProd.mtgcollectionmanager.database.DeckDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeckRepositoryImpl(
    private val deckDao: DeckDao
): DeckRepository {
    private val _deckCount = MutableStateFlow(0)
    override val deckCount = _deckCount.asStateFlow()

    override suspend fun getAll(): List<Deck> {
        return deckDao.getAll()
    }
    override suspend fun insertDeck(deck: Deck) {
        deckDao.insertDeck(deck)
        getDeckCount()
    }

    override suspend fun getNumberOfDecks(): Int {
        return deckDao.getNumberOfDecks()
    }
    private fun getDeckCount(){
        CoroutineScope(Dispatchers.IO).launch {
            _deckCount.update {
                deckDao.getNumberOfDecks()
            }
        }
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _deckCount.update {
                deckDao.getNumberOfDecks()
            }

        }
    }
}