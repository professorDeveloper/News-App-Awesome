package com.azamovhudstc.newsappwithdagger2.data.local.dao

import androidx.room.*
import com.azamovhudstc.newsappwithdagger2.data.models.PrimaryNewsUUID
import com.azamovhudstc.newsappwithdagger2.data.models.news_by_category.News

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: News)

    @Query("select * from news")
    fun getAllNews(): List<News>

    @Query("select * from news where uuid = :uuid")
    fun getNewsByUUID(uuid: String): News?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(primaryNewsUUID: PrimaryNewsUUID)

    @Query("select * from newEntity where id = 0")
    fun getPrimaryUUID(): PrimaryNewsUUID?
}