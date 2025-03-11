package com.example.data.di

import com.example.data.data_source.local.ImageLocalDataSource
import com.example.data.data_source.local.ImageLocalDataSourceImpl
import com.example.data.repository.ImageRepositoryImpl
import com.example.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface ImageSingletonModule {

    @Binds
    @Singleton
    fun bindImageRepository(impl: ImageRepositoryImpl): ImageRepository

    @Binds
    fun bindUserLocalDataSource(impl: ImageLocalDataSourceImpl): ImageLocalDataSource
}
