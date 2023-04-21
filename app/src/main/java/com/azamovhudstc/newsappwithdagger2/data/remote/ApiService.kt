package com.azamovhudstc.newsappwithdagger2.data.remote

import com.azamovhudstc.newsappwithdagger2.App.Companion.API_KEY
import com.azamovhudstc.newsappwithdagger2.data.models.news_by_category.NewsByCategory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("all")
    suspend fun getNews(
        @Query("api_token") api_key: String = API_KEY,
        @Query("categories") category: String,
        @Query("locale") locale: String = "eu",
        @Query("language") language: String,
        @Query("page") page: Int = 1,
    ): Response<NewsByCategory>


    @GET("all")
    suspend fun getSearchedNews(
        @Query("api_token") api_key: String = API_KEY,
        @Query("search") search: String,
        @Query("locale") locale: String = "eu",
        @Query("language") language: String,
        @Query("sort") sort: String = "published_at",
        @Query("page") page: Int = 1,
    ): Response<NewsByCategory>

}