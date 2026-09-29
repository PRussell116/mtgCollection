package AzuroProd.mtgcollectionmanager.database

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "deck")
data class Deck(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: String,
//    @ColumnInfo(name = "cards")
//    val cards: List<Int>,
    @ColumnInfo(name = "img")
    val img: String?
)
