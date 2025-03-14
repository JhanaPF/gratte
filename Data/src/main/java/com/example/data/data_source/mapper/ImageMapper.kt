package com.example.data.data_source.mapper

import com.example.data.model.ImageEntity
import com.example.domain.model.ImageModel
import com.example.network.model.image.ImageApiModel

// To Domain

fun ImageEntity.toDomain(): ImageModel? {
    return ImageModel(
        id = id,
        userId = userId ?: return null,
        image = image64 ?: return null,
        score = score ?: 0,
    )
}

fun List<ImageEntity>?.toDomain(): List<ImageModel>? =
    this?.mapNotNull { it.toDomain() }

// To Entity

fun ImageModel.toEntity(): ImageEntity {
    return ImageEntity(
        id = this.id,
        userId = this.userId,
        image64 = this.image,
        score = this.score,
    )
}

fun List<ImageModel>.toEntity(): List<ImageEntity> =
    this.map { it.toEntity() }

// To Api

fun ImageModel.toApiModel(): ImageApiModel {
    return ImageApiModel(
        image = this.image,
        id = this.id,
        userId = this.userId,
        score = this.score,
    )
}

fun List<ImageModel>.toApiModelList(): List<ImageApiModel> =
    this.map { it.toApiModel() }

// From Api

fun ImageApiModel.toDomain(): ImageModel? {
    val imageValue = image ?: return null

    return ImageModel(
        image = imageValue,
        id = id,
        userId = userId,
        score = score,
    )
}

fun List<ImageApiModel>?.toDomainList(): List<ImageModel>? =
    this?.mapNotNull { it.toDomain() }
