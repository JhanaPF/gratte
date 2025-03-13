package com.example.network.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
class ResponseApiModel<out T : Any>(
    val status: Int? = null,
    val success: Boolean? = null,
    val data: T? = null,
)
