package com.azamovhudstc.newsappwithdagger2.di.module

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.room.Room
import com.azamovhudstc.newsappwithdagger2.data.local.AppDatabase
import com.azamovhudstc.newsappwithdagger2.data.local.dao.NewsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "nuntium_database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(appDatabase: AppDatabase): NewsDao = appDatabase.newsDao()


    @Provides
    @Singleton
    fun provideSharedPreference(context: Context) =
        context.getSharedPreferences("shared", MODE_PRIVATE)!!

}