package com.example.network.retrofit

import com.example.network.extenstions.Constants
import com.example.network.model.ResponseApiModel
import com.example.network.model.image.ImageApiModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT

private const val IMAGE_URL = "/image"
private const val VOTE_URL = "${IMAGE_URL}/vote"
private const val VOTE_POSITIVE = "${VOTE_URL}/positive"
private const val VOTE_NEGATIVE = "${VOTE_URL}/negative"

interface ImageRetrofitService {

    @PUT(IMAGE_URL)
    @Headers(Constants.HEADER_JSON)
    suspend fun sendImage(
        @Body image: ImageApiModel,
    ): ResponseApiModel<Unit>

    @GET(VOTE_URL)
    @Headers(Constants.HEADER_JSON)
    suspend fun getDailyVoteUsersImages(): ResponseApiModel<List<ImageApiModel>>

    @PUT(VOTE_POSITIVE)
    @Headers(Constants.HEADER_JSON)
    suspend fun votePositive(): ResponseApiModel<Unit>

    @PUT(VOTE_NEGATIVE)
    @Headers(Constants.HEADER_JSON)
    suspend fun voteNegative(): ResponseApiModel<Unit>
}
