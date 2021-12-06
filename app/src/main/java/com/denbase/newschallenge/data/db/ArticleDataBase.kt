package com.denbase.newschallenge.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.denbase.newschallenge.data.model.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticleDataBase: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

}