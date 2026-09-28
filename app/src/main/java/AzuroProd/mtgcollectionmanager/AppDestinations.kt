package AzuroProd.mtgcollectionmanager

enum class AppDestinations(
    val label: String
) {
    HOME("Home"),
    COLLECTION("Collection"),
    DECKCREATION("Deck creation"),
    SETTINGS("Settings");

    val icon: Int
        get() = when (this) {
            HOME -> R.drawable.ic_home
            COLLECTION -> R.drawable.ic_favorite
            SETTINGS -> R.drawable.ic_account_box
            else -> {
                R.drawable.ic_home
            }
        }
}
