package com.example.data.data_source.mapper

import com.example.data.model.HighScoresEntity
import com.example.domain.model.HighScoresModel
import com.example.network.model.image.HighScoresApiModel

// Api to Domain
fun HighScoresApiModel.toDomain(): HighScoresModel? {
    return HighScoresModel(
        id = id ?: return null,
        rank = rank ?: return null,
        userid = userid ?: return null,
        score = score?.toInt() ?: return null,
        pictureUrl = picture_url ?: return null,
    )
}

fun List<HighScoresApiModel>.toDomainModel(): List<HighScoresModel> =
    this.mapNotNull {
        it.toDomain()
    }

// Entity to Domain

fun HighScoresEntity.toDomain(): HighScoresModel? {
    val rankValue = rank ?: return null
    val userIdValue = userId ?: return null
    val scoreValue = score ?: return null

    return HighScoresModel(
        id = id,
        rank = rankValue,
        userid = userIdValue,
        score = scoreValue,
        pictureUrl = pictureUrl,
    )
}

fun List<HighScoresEntity>?.toDomain(): List<HighScoresModel>? =
    this?.mapNotNull { it.toDomain() }

// Domain to Entity

fun HighScoresModel.toEntity(): HighScoresEntity? {
    return HighScoresEntity(
        id = id,
        userId = userid,
        rank = rank,
        pictureUrl = pictureUrl,
        score = score,
    )
}

fun List<HighScoresModel>.toEntity(): List<HighScoresEntity> =
    this.mapNotNull { it.toEntity() }
