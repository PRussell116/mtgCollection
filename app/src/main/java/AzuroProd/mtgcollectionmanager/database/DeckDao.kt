package AzuroProd.mtgcollectionmanager.database

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update

@Dao
interface DeckDao {
    @Query("SELECT * FROM deck")
    suspend fun getAll(): List<Deck>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeck(deck: Deck)

    @Update
    suspend fun updateDeck(deck: Deck)

    @Delete
    suspend fun deleteDeck(deck: Deck)

    @Query("SELECT count(*) from deck")
    suspend fun getNumberOfDecks(): Int
}
