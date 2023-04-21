package com.azamovhudstc.newsappwithdagger2.data.models.news_by_category

import com.azamovhudstc.newsappwithdagger2.data.models.news_by_category.Meta
import com.azamovhudstc.newsappwithdagger2.data.models.news_by_category.News

data class NewsByCategory(
    val `data`: List<News>?,
    val meta: Meta?
)