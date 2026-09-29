package AzuroProd.mtgcollectionmanager.screens.collection

import AzuroProd.mtgcollectionmanager.database.Deck
import AzuroProd.mtgcollectionmanager.network.Card
import AzuroProd.mtgcollectionmanager.repositories.cardRepository.CardRepository
import AzuroProd.mtgcollectionmanager.repositories.deckRepository.DeckRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val cardRepository: CardRepository,
    private val deckRepository: DeckRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        uiState(
            cards = emptyList(),
            decks = emptyList()
        )
    )
    val uiState: StateFlow<uiState> = _uiState.asStateFlow()
    fun search(query: String){
        viewModelScope.launch {
            if (query.isNotBlank()){
                val res = cardRepository.searchCards(query)
                Log.d(TAG, "search: cards ${res.data} ")
                _uiState.update { currentState ->
                    currentState.copy(
                        cards = res.data
                    )
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    decks = deckRepository.getAll()
                )
            }
        }

        viewModelScope.launch {
            deckRepository.deckCount.collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(
                        decks = deckRepository.getAll()
                    )
                }
            }
        }

    }
    companion object {
        private const val TAG = "CollectionViewModel"
    }
}

data class uiState(
    val decks: List<Deck>,
    val cards: List<Card>
)
