package com.bugima.rickandmortycharacters.di

import com.bugima.rickandmortycharacters.domain.repository.CharacterRepository
import com.bugima.rickandmortycharacters.domain.usecase.AddCharacterToFavoritesUseCase
import com.bugima.rickandmortycharacters.domain.usecase.FetchCharactersUseCase
import com.bugima.rickandmortycharacters.domain.usecase.GetAllFavoriteCharactersUseCase
import com.bugima.rickandmortycharacters.domain.usecase.GetCharacterPageCountUseCase
import com.bugima.rickandmortycharacters.domain.usecase.RemoveCharacterFromFavoritesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideFetchCharactersUseCase(
        repository: CharacterRepository
    ): FetchCharactersUseCase {
        return FetchCharactersUseCase(repository)
    }

    @Provides
    fun provideGetCharacterPageCountUseCase(
        repository: CharacterRepository
    ): GetCharacterPageCountUseCase {
        return GetCharacterPageCountUseCase(repository)
    }

    @Provides
    fun provideAddCharacterToFavoritesUseCase(
        repository: CharacterRepository
    ): AddCharacterToFavoritesUseCase {
        return AddCharacterToFavoritesUseCase(repository)
    }

    @Provides
    fun provideRemoveCharacterFromFavoritesUseCase(
        repository: CharacterRepository
    ): RemoveCharacterFromFavoritesUseCase {
        return RemoveCharacterFromFavoritesUseCase(repository)
    }

    @Provides
    fun provideGetAllFavoriteCharactersUseCase(
        repository: CharacterRepository
    ): GetAllFavoriteCharactersUseCase {
        return GetAllFavoriteCharactersUseCase(repository)
    }
}
