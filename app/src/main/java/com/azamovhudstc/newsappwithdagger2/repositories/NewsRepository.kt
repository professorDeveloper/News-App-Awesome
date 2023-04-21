package com.azamovhudstc.newsappwithdagger2.repositories

import com.azamovhudstc.newsappwithdagger2.data.local.dao.NewsDao
import com.azamovhudstc.newsappwithdagger2.data.models.PrimaryNewsUUID
import com.azamovhudstc.newsappwithdagger2.data.models.news_by_category.News
import com.azamovhudstc.newsappwithdagger2.data.remote.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao
) {

    suspend fun getNewsByCategory(category: String, language: String) =
        flow {
            if (language.equals("cn"))
                emit(apiService.getNews(category = category, language = "zh"))
            else emit(apiService.getNews(category = category, language = language))
        }

    fun insertPrimaryUUID(primaryNewsUUID: PrimaryNewsUUID) = newsDao.insert(primaryNewsUUID)
    fun getPrimaryUUID() = newsDao.getPrimaryUUID()


    fun insertNews(news: News) = newsDao.insert(news)
    fun getNewsByUUID(uuid: String) = newsDao.getNewsByUUID(uuid)

    suspend fun getSearchedNews(string: String, language: String) = flow {
        if (language.equals("cn"))

        emit(apiService.getSearchedNews(search = string, language = "zh"))
        else
            emit(apiService.getSearchedNews(search = string, language = language))

    }


}