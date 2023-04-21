package com.azamovhudstc.newsappwithdagger2.utils

import com.azamovhudstc.newsappwithdagger2.data.models.news_by_category.News

sealed class NewsResource {
    object Idle : NewsResource()
    object Loading : NewsResource()
    data class Success(val list: List<News>) : NewsResource()
    data class Error(val message: String) : NewsResource()
}