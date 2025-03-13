package com.example.network.exception

import retrofit2.Retrofit

inline fun <reified T : Any> Retrofit.createService(): Lazy<T> = lazy { create(T::class.java) }
