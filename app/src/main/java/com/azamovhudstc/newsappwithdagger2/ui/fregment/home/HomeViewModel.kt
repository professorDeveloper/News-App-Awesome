package com.azamovhudstc.newsappwithdagger2.ui.fregment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azamovhudstc.newsappwithdagger2.data.models.PrimaryNewsUUID
import com.azamovhudstc.newsappwithdagger2.data.models.news_by_category.News
import com.azamovhudstc.newsappwithdagger2.repositories.NewsRepository
import com.azamovhudstc.newsappwithdagger2.utils.NetworkHelper
import com.azamovhudstc.newsappwithdagger2.utils.NewsResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<NewsResource>(NewsResource.Loading)
    val stateFlow: StateFlow<NewsResource> get() = _stateFlow
    private val _recommendedStateFlow = MutableStateFlow<NewsResource>(NewsResource.Loading)
    val recommendedState: StateFlow<NewsResource> get() = _recommendedStateFlow

    fun getNews(category: String, language: String) {
        if (networkHelper.isNetworkConnected()) {
            _stateFlow.value = NewsResource.Loading
            viewModelScope.launch {
                repository.getNewsByCategory(category, language).catch {
                    _stateFlow.value = NewsResource.Error(it.message ?: "")
                }.collect {
                    if (it.isSuccessful) {
                        if (category.lowercase() == "general") {
                            val data = it.body()?.data
                            val uuidList = arrayListOf<String>()
                            data?.forEachIndexed { index, news ->
                                val newsByUUID = repository.getNewsByUUID(news.uuid)
                                if (newsByUUID == null || newsByUUID.uuid.isEmpty()) {
                                    repository.insertNews(news)
                                }
                                uuidList.add(news.uuid)
                            }
                            val primary = PrimaryNewsUUID(list = uuidList)
                            repository.insertPrimaryUUID(primary)
                        }
                        _stateFlow.value = NewsResource.Success(it.body()?.data!!)
                    } else {
                        _stateFlow.value = NewsResource.Error(it.message() ?: "")
                    }
                }
            }
        } else {
            val primaryUUID = repository.getPrimaryUUID()
            if (primaryUUID != null && primaryUUID.list!!.isNotEmpty()) {
                val list = arrayListOf<News>()
                primaryUUID.list.forEach {
                    val primaryNews = repository.getNewsByUUID(it)
                    if (primaryNews != null) {
                        list.add(primaryNews)
                    }
                }
                _stateFlow.value = NewsResource.Success(list)
            } else {
                _stateFlow.value = NewsResource.Error("Internet not connected!")
            }
        }
    }

    fun getRecommendedNews(category: String, language: String) {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                repository.getNewsByCategory(category, language).catch {
                    _recommendedStateFlow.value = NewsResource.Error(it.message ?: "")
                }.collect {
                    if (it.isSuccessful) {
                        _recommendedStateFlow.value = NewsResource.Success(it.body()?.data!!)
                    } else {
                        _recommendedStateFlow.value = NewsResource.Error(it.message() ?: "")
                    }
                }
            }
        } else {
            _recommendedStateFlow.value = NewsResource.Error("Internet not connected!")
        }
    }


}