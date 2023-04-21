package com.azamovhudstc.newsappwithdagger2.data.models.news_by_category

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity
data class News(
    @TypeConverters(StringListConverter::class)
    val categories: List<String>?,
    val description: String?,
    val image_url: String?,
    val keywords: String?,
    val language: String?,
    val published_at: String?,
    val snippet: String?,
    val source: String?,
    val title: String?,
    val url: String?,
    @PrimaryKey
    var uuid: String,
    var isSaved:Boolean = false
):Serializable