package AzuroProd.mtgcollectionmanager.database


import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(entities = [Deck::class], version = 3, exportSchema = false)
abstract class DeckDatabase: RoomDatabase() {
    abstract fun deckDao(): DeckDao
}
