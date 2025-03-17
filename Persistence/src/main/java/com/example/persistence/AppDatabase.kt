package com.example.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.persistence.model.HighScoresEntity
import com.example.persistence.model.ImageEntity
import com.example.persistence.dao.HighScoresDao
import com.example.persistence.dao.ImageDao

@Database(
    entities = [
        ImageEntity::class,
        HighScoresEntity::class,
    ],
    exportSchema = false,
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun highScoresDao(): HighScoresDao
}
