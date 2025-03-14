package com.example.network.okhttp

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class AssetsMockInterceptorImpl(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        val fileName = when {
            path.contains("highscores") -> "high_scores.json"
            path.contains("image") -> "image_send.json"
            else -> null
        }

        return if (fileName != null) {
            // Read the file content from assets.
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(jsonString.toResponseBody("application/json".toMediaTypeOrNull()))
                .build()
        } else {
            chain.proceed(request)
        }
    }
}
