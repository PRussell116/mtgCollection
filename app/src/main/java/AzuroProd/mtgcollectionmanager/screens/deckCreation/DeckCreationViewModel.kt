package AzuroProd.mtgcollectionmanager.screens.deckCreation

import AzuroProd.mtgcollectionmanager.network.Card
import AzuroProd.mtgcollectionmanager.repositories.cardRepository.CardRepository
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DeckCreationViewModel @Inject constructor(
    private val cardRepository: CardRepository
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

    fun createDeck(){

    }
}


data class DeckCreationUiState(
    val dropDownCards: List<Card>
)

