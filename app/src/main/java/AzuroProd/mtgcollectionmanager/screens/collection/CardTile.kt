package AzuroProd.mtgcollectionmanager.screens.collection

import AzuroProd.mtgcollectionmanager.R
import AzuroProd.mtgcollectionmanager.network.Prices
import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
@Composable
fun CardTile(
    url: String,
    title: String,
    prices: Prices?,
    onClick: () -> Unit,
    onMagnify: () -> Unit,
    modifier: Modifier = Modifier
) {
    val ctx = LocalContext.current

    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium
            )
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column(Modifier.padding(10.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(ctx)
                    .data(url)
                    .addHeader("Accept", "application/json")
                    .addHeader("User-Agent", "MtgCollectionManager/1.0 (Android App)")
                    .build(),
                contentDescription = null,
               // modifier = Modifier.size(300.dp)
            )

            Spacer(Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.clickable(onClick = onClick),
                    textAlign = TextAlign.Center,
                    minLines = 2
                )

            }
            Spacer(Modifier.height(10.dp))


            Spacer(Modifier.height(10.dp))

            if (prices != null){
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("$${prices.usd} / €${prices.eur}")

                }

                Spacer(Modifier.height(10.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = onMagnify,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector =  Icons.Default.Search,
                        contentDescription = "Magnify"
                    )
                }


                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(50)
                        )
                ){
                    Text(
                        text = "ADD",
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
        }

    }


}

@Preview
@Composable
private fun TilePrev() {
    CardTile(
        title = "Niv-Mizzet",
        url = "https://cards.scryfall.io/normal/front/8/6/86c5c337-d25f-4c3e-9762-09ed0c2d36d7.jpg?1783911897",
        onClick = {},
        onMagnify = {},
        prices = Prices(
            usd = "1.0",
            eur = "2.0",
            tix = "3.0",
            usdFoil = "4.0",
        )
    )

}