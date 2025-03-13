package com.example.network.di

import com.example.network.api.ImageApi
import com.example.network.api.ImageApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkApiHiltSingletonModule {
    @Binds
    fun bindImageApi(impl: ImageApiImpl): ImageApi
}
