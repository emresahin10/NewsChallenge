package com.denbase.newschallenge.repositories

import androidx.lifecycle.LiveData
import com.denbase.newschallenge.data.model.Article
import com.denbase.newschallenge.data.model.ArticleItem
import com.denbase.newschallenge.data.model.NewsSourcesItem
import com.denbase.newschallenge.utils.Resource

interface NewsRepository {

    suspend fun getSources(): Resource<NewsSourcesItem>

    suspend fun getSourceNews(sourceId: String): Resource<ArticleItem>

    suspend fun insertArticleToFavorite(article: Article)

    suspend fun deleteArticle(article: Article)

     fun getFavorites(): LiveData<List<Article>>
}