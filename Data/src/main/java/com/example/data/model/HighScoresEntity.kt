package com.example.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class HighScoresEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "user_id") val userId: String?,
    @ColumnInfo(name = "rank") val rank: String?,
    @ColumnInfo(name = "pictureUrl") val pictureUrl: String?,
    @ColumnInfo(name = "score") val score: Int?,
)
