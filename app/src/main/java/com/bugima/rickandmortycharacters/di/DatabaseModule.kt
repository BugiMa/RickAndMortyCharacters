package com.bugima.rickandmortycharacters.di

import android.content.Context
import androidx.room.Room
import com.bugima.rickandmortycharacters.data.local.dao.FavoriteCharacterDao
import com.bugima.rickandmortycharacters.data.local.database.RickAndMortyDatabase
import com.bugima.rickandmortycharacters.util.Const.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): RickAndMortyDatabase {
        return Room.databaseBuilder(
            context = appContext,
            klass = RickAndMortyDatabase::class.java,
            name = DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideFavoriteCharacterDao(database: RickAndMortyDatabase): FavoriteCharacterDao {
        return database.favoriteCharacterDao()
    }
}
