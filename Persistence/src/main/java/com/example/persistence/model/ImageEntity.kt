package com.example.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    @ColumnInfo(name = "user_id") val userId: String?,
    @ColumnInfo(name = "image_64") val image64: String?,
    @ColumnInfo(name = "score") val score: Int?,
)
