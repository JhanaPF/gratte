package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Query("SELECT * FROM ImageEntity")
    suspend fun getAll(): List<ImageEntity>

    @Query("SELECT * FROM ImageEntity WHERE id = :id")
    suspend fun getById(id: Int): ImageEntity

    @Query("SELECT * FROM ImageEntity WHERE user_id = :userId")
    suspend fun getByUserId(userId: Int): List<ImageEntity>

    @Query("SELECT * FROM ImageEntity WHERE user_id = :userId")
    fun observeByUserId(userId: Int): Flow<List<ImageEntity>>

    @Insert
    suspend fun insertAll(image: List<ImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: ImageEntity)

    @Query("UPDATE ImageEntity SET score = :score WHERE id = :imageId")
    suspend fun updateScoreById(imageId: Int, score: Int)

    @Query("DELETE FROM ImageEntity WHERE id = :imageId")
    suspend fun deleteById(imageId: Int)

    @Delete
    suspend fun delete(user: ImageEntity)
}
