package com.example.data.data_source.mapper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Base64.DEFAULT
import androidx.room.TypeConverter
import com.example.data.model.ImageEntity
import com.example.domain.model.ImageModel

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

@TypeConverter
fun base64ToBitmap(base64String: String): Bitmap {
    // convert Base64 String into byteArray
    val byteArray = Base64.decode(base64String, DEFAULT)
    // byteArray to Bitmap
    return BitmapFactory.decodeByteArray(
        byteArray,
        0,
        byteArray.size,
    )
}
