package com.example.persistence.di

import android.content.Context
import androidx.room.Room
import com.example.persistence.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "room-database",
    ).build()

    @Provides
    fun providesImageDao(database: AppDatabase): com.example.persistence.dao.ImageDao = database.imageDao()

    @Provides
    fun providesHighScoreDao(database: AppDatabase): com.example.persistence.dao.HighScoresDao = database.highScoresDao()
}
