package com.example.data.data_source.mapper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Base64.DEFAULT
import androidx.room.TypeConverter
import com.example.data.model.ImageEntity
import com.example.domain.model.ImageModel
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

// To Domain

fun ImageEntity.toDomain(): ImageModel? {
    return ImageModel(
        id = id,
        userId = userId ?: return null,
        image = base64ToBitmap(image64 ?: return null),
        score = score ?: 0
    )
}

fun List<ImageEntity>?.toDomain(): List<ImageModel>? =
    this?.mapNotNull { it.toDomain() }

// To Entity

fun ImageModel.toEntity(): ImageEntity {
    return ImageEntity(
        id = this.id,
        userId = this.userId,
        image64 = bitmapToBase64(this.image),
        score = this.score
    )
}

fun List<ImageModel>.toEntity(): List<ImageEntity> =
    this.map { it.toEntity() }

fun scaleBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val scale = minOf(maxWidth.toFloat() / width, maxHeight.toFloat() / height)
    return Bitmap.createScaledBitmap(bitmap, (width * scale).toInt(), (height * scale).toInt(), true)
}

@TypeConverter
fun bitmapToBase64(bitmap: Bitmap) : String{
    val outputStream = ByteArrayOutputStream()
    // Compress the bitmap as PNG or JPEG
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, DEFAULT)
}

@TypeConverter
fun base64ToBitmap(base64String: String):Bitmap{
    //convert Base64 String into byteArray
    val byteArray = Base64.decode(base64String, DEFAULT)
    //byteArray to Bitmap
    return BitmapFactory.decodeByteArray(byteArray,
        0, byteArray.size)
}