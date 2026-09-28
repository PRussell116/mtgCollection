package AzuroProd.mtgcollectionmanager.screens.collection

import AzuroProd.mtgcollectionmanager.network.Card
import AzuroProd.mtgcollectionmanager.repositories.cardRepository.CardRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val cardRepository: CardRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(uiState(cards = emptyList()))
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

    }
    companion object {
        private const val TAG = "CollectionViewModel"
    }
}

data class uiState(
    val cards: List<Card>
)
