package com.bugima.rickandmortycharacters.di

import com.bugima.rickandmortycharacters.ui.mapper.MainScreenViewMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewMapperModule {

    @Provides
    @Singleton
    fun provideMainScreenViewMapper(): MainScreenViewMapperImpl {
        return MainScreenViewMapperImpl()
    }
}