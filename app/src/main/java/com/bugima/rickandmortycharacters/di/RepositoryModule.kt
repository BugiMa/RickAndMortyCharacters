package com.bugima.rickandmortycharacters.di

import com.bugima.rickandmortycharacters.data.local.dao.FavoriteCharacterDao
import com.bugima.rickandmortycharacters.data.remote.api.RickAndMortyApi
import com.bugima.rickandmortycharacters.data.repository.CharacterRepositoryImpl
import com.bugima.rickandmortycharacters.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideCharacterRepository(
        repository: CharacterRepositoryImpl
    ): CharacterRepository
}