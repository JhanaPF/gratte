package com.example.data.di

import com.example.data.processor.ImageProcessorImpl
import com.example.data.provider.GPUImageProvider
import com.example.data.provider.GPUImageProviderImpl
import com.example.domain.gpui.ImageProcessor
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
    fun bindGPUImageProvider(impl: GPUImageProviderImpl): GPUImageProvider

    @Singleton
    @Binds
    fun bindImageProcessor(impl: ImageProcessorImpl): ImageProcessor
}
