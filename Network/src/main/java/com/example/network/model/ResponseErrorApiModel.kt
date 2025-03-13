package com.example.network.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
class ResponseErrorApiModel(
    val status: Int? = 0,
    val error_code: Int? = 0,
    val data: JsonElement? = null,
    val str: String? = null,
    val link: String? = null,
    val error: String? = null,
)
