package AzuroProd.mtgcollectionmanager.network

import com.google.gson.annotations.SerializedName

data class ScryfallSearchResponse(
    @SerializedName("object") val objectType: String?,
    @SerializedName("total_cards") val totalCards: Int?,
    @SerializedName("has_more") val hasMore: Boolean?,
    @SerializedName("data") val data: List<Card>
)

data class Card(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image_uris") val imageUris: ImageUris?,
    @SerializedName("prices") val prices: Prices?
)

data class ImageUris(
    @SerializedName("small") val small: String?,
    @SerializedName("normal") val normal: String?,
    @SerializedName("large") val large: String?,
    @SerializedName("png") val png: String?,
    @SerializedName("art_crop") val artCrop: String?,
    @SerializedName("border_crop") val borderCrop: String?
)

data class Prices(
    @SerializedName("usd") val usd: String?,
    @SerializedName("usd_foil") val usdFoil: String?,
    @SerializedName("eur") val eur: String?,
    @SerializedName("tix") val tix: String?
)
