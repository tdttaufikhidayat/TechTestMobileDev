package com.taufikhidayat.techtestmobiledev.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.taufikhidayat.techtestmobiledev.data.local.dao.ArticleDao
import com.taufikhidayat.techtestmobiledev.data.local.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}