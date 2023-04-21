package com.azamovhudstc.newsappwithdagger2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.azamovhudstc.newsappwithdagger2.data.local.dao.NewsDao
import com.azamovhudstc.newsappwithdagger2.data.models.PrimaryNewsUUID
import com.azamovhudstc.newsappwithdagger2.data.models.news_by_category.StringListConverter
import com.azamovhudstc.newsappwithdagger2.data.models.news_by_category.News

@Database(entities = [News::class, PrimaryNewsUUID::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase :RoomDatabase(){
    abstract fun newsDao(): NewsDao

}