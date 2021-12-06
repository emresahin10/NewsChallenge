package com.denbase.newschallenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denbase.newschallenge.data.model.Article
import com.denbase.newschallenge.data.model.ArticleItem
import com.denbase.newschallenge.data.model.NewsSourcesItem

import com.denbase.newschallenge.repositories.NewsRepository
import com.denbase.newschallenge.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {

    private val _newsSourceResponse = MutableLiveData<Resource<NewsSourcesItem>>()
    val newsSourceResponse: LiveData<Resource<NewsSourcesItem>> = _newsSourceResponse

    private val _newsResponse = MutableLiveData<Resource<ArticleItem>>()
    val newsResponse: LiveData<Resource<ArticleItem>> = _newsResponse

    fun getSources(){
        _newsSourceResponse.postValue(Resource.Loading())

        viewModelScope.launch {
            _newsSourceResponse.postValue(repository.getSources())
        }
    }

    fun getNewsWithSource(sourceId: String){
        _newsResponse.postValue(Resource.Loading())

        viewModelScope.launch {
            _newsResponse.postValue(repository.getSourceNews(sourceId))
        }
    }

    fun saveArticle(article: Article){
        viewModelScope.launch {
            repository.insertArticleToFavorite(article)
        }
    }

    fun getSavedNews() = repository.getFavorites()


    fun deleteArticle(article: Article){
        viewModelScope.launch {
            repository.deleteArticle(article)
        }
    }

}