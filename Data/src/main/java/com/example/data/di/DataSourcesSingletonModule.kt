package com.example.data.di

import com.example.data.data_source.local.HighScoresDataSourceImpl
import com.example.data.data_source.local.HighScoresLocalDataSource
import com.example.data.data_source.local.ImageLocalDataSource
import com.example.data.data_source.local.ImageLocalDataSourceImpl
import com.example.data.data_source.remote.HighScoresRemoteDataSource
import com.example.data.data_source.remote.HighScoresRemoteDataSourceImpl
import com.example.data.repository.HighScoresRepositoryImpl
import com.example.data.repository.ImageRepositoryImpl
import com.example.domain.repository.HighScoresRepository
import com.example.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourcesSingletonModule {

    @Binds
    @Singleton
    fun bindImageRepository(impl: ImageRepositoryImpl): ImageRepository

    @Binds
    @Singleton
    fun bindHighScoreRepository(impl: HighScoresRepositoryImpl): HighScoresRepository

    @Binds
    fun bindImageLocalDataSource(impl: ImageLocalDataSourceImpl): ImageLocalDataSource

    @Binds
    fun bindHighScoresRemoteDataSource(impl: HighScoresRemoteDataSourceImpl): HighScoresRemoteDataSource

    @Binds
    fun bindHighScoresLocalDataSource(impl: HighScoresDataSourceImpl): HighScoresLocalDataSource
}
