package com.example.data.di

import com.example.data.provider.GPUImageFilterProviderImpl
import com.example.domain.gpui.GPUImageProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProviderModule {
    @Singleton
    @Binds
    fun bindGPUImageProvider(impl: GPUImageFilterProviderImpl): GPUImageProvider
}
