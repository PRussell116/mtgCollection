package AzuroProd.mtgcollectionmanager.screens

import AzuroProd.mtgcollectionmanager.screens.collection.CardTile
import AzuroProd.mtgcollectionmanager.screens.collection.CollectionViewModel
import AzuroProd.mtgcollectionmanager.ui.theme.MtgCollectionManagerTheme
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.time.Duration.Companion.milliseconds


@Composable
fun CollectionScreen(
    onAddDeckClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CollectionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState.cards) {
        Log.d("DELETEME", "cards ${uiState.cards.map { it.imageUris?.normal }}")
    }
    Column(modifier.background(MaterialTheme.colorScheme.background)) {

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Button(
                onClick = onAddDeckClick
            ){
                Text(
                    text = "Add deck"
                )
            }

        }
        SearchBar(
            search = viewModel::search
        )

        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier

        ){
            items(uiState.cards){
//                val state= rememberAsyncImagePainter(
//                    model = it.imageUris?.small,
//                )
                CardTile(
                    url = it.imageUris?.normal.toString(),
                    title = it.name,
                    onClick = {},
                    onMagnify = {},
                    prices = it.prices
                )


            }

        }

    }
}

@OptIn(FlowPreview::class)
@Composable
fun SearchBar(
    search: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val textFieldState = rememberTextFieldState()
    TextField(
        state = textFieldState
    )
    LaunchedEffect(textFieldState) {
        snapshotFlow { textFieldState.text.toString() }
            .debounce(750.milliseconds)
            .distinctUntilChanged()
            .collect { query ->
                search(query)
            }
    }


}