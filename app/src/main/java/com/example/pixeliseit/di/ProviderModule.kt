package com.example.pixeliseit.di

import com.example.pixeliseit.data.gpui.GPUImageFilterProviderImpl
import com.example.pixeliseit.data.gpui.GPUImageProvider
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
    fun bindClipboardProvider(impl: GPUImageFilterProviderImpl): GPUImageProvider
}
