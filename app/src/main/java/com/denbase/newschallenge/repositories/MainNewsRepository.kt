package com.denbase.newschallenge.repositories

import androidx.lifecycle.LiveData
import com.denbase.newschallenge.api.NewsApi
import com.denbase.newschallenge.data.model.Article
import com.denbase.newschallenge.data.model.ArticleItem
import com.denbase.newschallenge.data.model.NewsSourcesItem
import com.denbase.newschallenge.data.db.ArticleDao
import com.denbase.newschallenge.utils.Resource
import javax.inject.Inject

class MainNewsRepository @Inject constructor(private val api: NewsApi, private val newsDao: ArticleDao): NewsRepository {

    override suspend fun getSources(): Resource<NewsSourcesItem> {
        try {
            val response = api.getSources()
            if (response.isSuccessful){
                val body = response.body()
                return Resource.Success(body)
            }
            return Resource.Error(response.message())
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
    }

    override suspend fun getSourceNews(sourceId: String): Resource<ArticleItem> {
        try {
            val response = api.getNewsWithSource(sourceId)
            if (response.isSuccessful){
                val body = response.body()
                return Resource.Success(body)
            }
            return Resource.Error(response.message())
        }catch (e: Exception){
            return Resource.Error(e.message.toString())
        }
    }

    override suspend fun insertArticleToFavorite(article: Article) {
        newsDao.upsert(article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsDao.deleteArticle(article)
    }

    override  fun getFavorites(): LiveData<List<Article>> {
      return  newsDao.getAllArticles()
    }
}