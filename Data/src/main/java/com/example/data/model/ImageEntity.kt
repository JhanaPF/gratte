package com.example.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int?,
    @ColumnInfo(name = "image_64") val image64: String?,
    @ColumnInfo(name = "score") val score: Int?,
)