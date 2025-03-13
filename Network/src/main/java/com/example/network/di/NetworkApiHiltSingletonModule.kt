package com.example.network.di

import com.example.network.api.HighScoresApi
import com.example.network.api.HighScoresApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkApiHiltSingletonModule {
    @Binds
    fun bindHighScoresApi(impl: HighScoresApiImpl): HighScoresApi
}
