package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.dao.ImageDao
import com.example.data.model.ImageEntity

@Database(entities = [ImageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}