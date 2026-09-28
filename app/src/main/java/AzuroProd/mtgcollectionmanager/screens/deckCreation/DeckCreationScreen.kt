package AzuroProd.mtgcollectionmanager.screens.deckCreation

import AzuroProd.mtgcollectionmanager.enums.DeckType
import android.content.res.Resources
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.math.exp
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@Composable
fun DeckCreationScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DeckCreationViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var imgDropDownExpanded by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    var backgroundImg by remember { mutableStateOf("") }
    val ctx = LocalContext.current


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(ctx)
                .data( backgroundImg)
                .addHeader("Accept", "application/json")
                .addHeader("User-Agent", "MtgCollectionManager/1.0 (Android App)")
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )



        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),
           // modifier = Modifier.align(alignment = Alignment.Center)
        ) {
            Text(
                text = "New collection",
                style = MaterialTheme.typography.headlineMedium
            )


            val titleTextFieldState = rememberTextFieldState()
            TextField(
                state = titleTextFieldState,
                label = {
                    Text("Name")
                },
                lineLimits = TextFieldLineLimits.SingleLine
            )
            Box(
                modifier = Modifier.clickable {
                    expanded = true
                }
            ) {
                val deckTypeTextFieldState = rememberTextFieldState()
                TextField(
                    readOnly = true,
                    state = deckTypeTextFieldState,
                    label = {
                        Text("Collection type")
                    },
                    lineLimits = TextFieldLineLimits.SingleLine,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                expanded = !expanded
                            }
                        ) {
                            Crossfade(expanded) {
                                if (!it) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowDown,
                                        contentDescription = "Collection type"
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowUp,
                                        contentDescription = "Collection type"
                                    )
                                }
                            }

                        }
                    },
                    modifier = Modifier.clickable {
                        expanded = true
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    for (entry in DeckType.entries) {
                        DropdownMenuItem(
                            text = { Text(entry.name) },
                            onClick = {
                                deckTypeTextFieldState.setTextAndPlaceCursorAtEnd(entry.name)
                                expanded = false
                            }
                        )
                    }
                }
            }

            val imgTextFieldState = rememberTextFieldState()

            Box{
                TextField(
                    state = imgTextFieldState,
                    readOnly = false,
                    label = {
                        Text("Image")
                    },
                    lineLimits = TextFieldLineLimits.SingleLine,
                    trailingIcon = {
                        IconButton(
                            onClick = { }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = "Collection type"
                            )
                        }

                    }
                )
                DropdownMenu(
                    expanded = imgDropDownExpanded,
                    onDismissRequest = { imgDropDownExpanded = false }
                ) {
                    for (card in uiState.dropDownCards) {
                        DropdownMenuItem(
                            text = { Text(card.name) },
                            leadingIcon = {
                                AsyncImage(
                                    model = ImageRequest.Builder(ctx)
                                        .data( card.imageUris?.artCrop)
                                        .addHeader("Accept", "application/json")
                                        .addHeader("User-Agent", "MtgCollectionManager/1.0 (Android App)")
                                        .build(),
                                    contentDescription = card.name,
                                    modifier = Modifier.size(60.dp)
                                )
                            },
                            onClick = {
                                imgDropDownExpanded = false
                                backgroundImg = card.imageUris?.artCrop.toString()
                            }
                        )
                    }
                }
            }
            LaunchedEffect(uiState.dropDownCards) {
                if (uiState.dropDownCards.isNotEmpty()) imgDropDownExpanded = true
            }
            LaunchedEffect(imgTextFieldState) {
                snapshotFlow { imgTextFieldState.text.toString() }
                    .debounce(750.milliseconds)
                    .distinctUntilChanged()
                    .collect { query ->
                        viewModel.search(query)
                    }
            }


            Button(
                onClick = navigateBack
            ) {
                Text(
                    text = "Create",
                    style = MaterialTheme.typography.bodySmall
                )
            }


        }
    }
}