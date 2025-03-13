package com.example.network.di

import android.content.Context
import com.example.network.di.qualifier.RetrofitCoroutineQualifier
import com.example.network.okhttp.AssetsMockInterceptorImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkHiltSingletonModule {

    @Provides
    @RetrofitCoroutineQualifier
    fun provideCoroutineRetrofit(
        @RetrofitCoroutineQualifier retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit = retrofitBuilder
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Module
    @InstallIn(SingletonComponent::class)
    object SerializationModule {
        @Provides
        fun provideJson(): Json = Json { ignoreUnknownKeys = true }
    }

    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AssetsMockInterceptorImpl(context))
            .build()
    }
}
