package com.example.data.data_source.mapper

import com.example.domain.model.HighScoresModel
import com.example.network.model.image.HighScoresApiModel

fun HighScoresApiModel.toDomain(): HighScoresModel? {
    return HighScoresModel(
        id = id?.toInt() ?: return null,
        userid = userid ?: return null,
        score = score?.toInt() ?: return null,
        pictureUrl = picture_url ?: return null,
    )
}

fun List<HighScoresApiModel>.toDomain(): List<HighScoresModel> =
    this.mapNotNull {
        it.toDomain()
    }
