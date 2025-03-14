package com.example.network.retrofit

import com.example.network.extenstions.Constants
import com.example.network.model.ResponseApiModel
import com.example.network.model.image.ImageApiModel
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT

private const val IMAGE_URL = "/image"

interface ImageRetrofitService {

    @PUT(IMAGE_URL)
    @Headers(Constants.HEADER_JSON)
    suspend fun sendImage(
        @Body image: ImageApiModel,
    ): ResponseApiModel<Unit>
}
