package AzuroProd.mtgcollectionmanager.screens.deckCreation

import AzuroProd.mtgcollectionmanager.database.Deck
import AzuroProd.mtgcollectionmanager.enums.DeckType
import AzuroProd.mtgcollectionmanager.network.Card
import AzuroProd.mtgcollectionmanager.repositories.cardRepository.CardRepository
import AzuroProd.mtgcollectionmanager.repositories.deckRepository.DeckRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckCreationViewModel @Inject constructor(
    private val cardRepository: CardRepository,
    private val deckRepository: DeckRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(
        DeckCreationUiState(
            dropDownCards = emptyList()
        )
    )
    val uiState = _uiState.asStateFlow()

    suspend fun search(query: String){
        if (query.isBlank()) return
        val cards = cardRepository.searchCards(query)
        _uiState.update { currentState ->
            currentState.copy(
                dropDownCards = cards.data.take(20)
            )
        }
    }

    fun createDeck(
        name: String,
        type: DeckType?,
        img: String?
    ){
        if (name.isBlank() || type == null) return

        viewModelScope.launch {
            val deckCountBefore = deckRepository.getNumberOfDecks()
            deckRepository.insertDeck(
                Deck(
                    name = name,
                    type = type.name,
                   // cards = emptyList(),
                    img = img
                )
            )
            val deckCount = deckRepository.getNumberOfDecks()

            Log.d(TAG, "createDeck: before $deckCountBefore after $deckCount ")
        }


    }

    companion object{
        const val TAG = "DeckCreationViewModel"
    }
}


data class DeckCreationUiState(
    val dropDownCards: List<Card>
)

