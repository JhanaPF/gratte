package com.example.network.okhttp

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject

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
            val jsonObject = JSONObject(jsonString)
            val appStatus = jsonObject.optInt("status", 200)
            val isSuccess = jsonObject.optBoolean("success", true)

            val httpCode = if (!isSuccess || appStatus == 400) {
                400
            } else {
                200
            }

            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(httpCode)
                .message(if (httpCode == 200) "OK" else "Bad Request")
                .body(jsonString.toResponseBody("application/json".toMediaTypeOrNull()))
                .build()
        } else {
            chain.proceed(request)
        }
    }
}
