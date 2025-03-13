package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.HighScoresEntity

@Dao
interface HighScoresDao {

    @Query("SELECT * FROM HighScoresEntity")
    suspend fun getAll(): List<HighScoresEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(image: List<HighScoresEntity>)
}
