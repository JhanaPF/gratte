package com.example.data.data_source.remote

import com.example.domain.model.ImageModel

interface ImageRemoteDataSource {
    suspend fun insertImage(image: ImageModel): Result<Unit>
}
