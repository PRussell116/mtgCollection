package AzuroProd.mtgcollectionmanager

import AzuroProd.mtgcollectionmanager.screens.CollectionScreen
import AzuroProd.mtgcollectionmanager.screens.deckCreation.DeckCreationScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import AzuroProd.mtgcollectionmanager.ui.theme.MtgCollectionManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MtgCollectionManagerTheme {
                MtgCollectionManagerApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun MtgCollectionManagerApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.filter { it != AppDestinations.DECKCREATION} .forEach { dest ->
                item(
                    icon = {
                        Icon(
                            painterResource(dest.icon),
                            contentDescription = dest.label
                        )
                    },
                    label = { Text(dest.label) },
                    selected = dest == currentDestination,
                    onClick = { currentDestination = dest }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when(currentDestination){
                AppDestinations.HOME,
                AppDestinations.SETTINGS -> {
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                AppDestinations.COLLECTION -> {
                    CollectionScreen(
                        onAddDeckClick = {
                            currentDestination = AppDestinations.DECKCREATION
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                AppDestinations.DECKCREATION -> {
                    DeckCreationScreen(
                        navigateBack = {
                            currentDestination = AppDestinations.COLLECTION
                        },
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MtgCollectionManagerApp()
}
