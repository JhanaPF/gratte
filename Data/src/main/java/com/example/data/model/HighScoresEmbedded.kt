package com.example.data.model

import androidx.room.Embedded
import androidx.room.Relation

class HighScoresEmbedded(

    @Embedded
    val highScoresEntity: HighScoresEntity? = null,

    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id",
    )
    val image: ImageEntity? = null,
)
