package AzuroProd.mtgcollectionmanager.repositories.deckRepository

import AzuroProd.mtgcollectionmanager.database.DeckDao
import AzuroProd.mtgcollectionmanager.database.DeckDatabase
import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeckRepositoryModule {
    @Singleton
    @Provides
    fun provideDb(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder<DeckDatabase>(
        context = context,
        name = "deck-database"
    )
        .setDriver(AndroidSQLiteDriver())
        .build()



    @Singleton
    @Provides
    fun provideDeckDao(deckDatabase: DeckDatabase): DeckDao = deckDatabase.deckDao()

    @Singleton
    @Provides
    fun provideDeckRepository(deckDao: DeckDao): DeckRepository = DeckRepositoryImpl(deckDao)


}