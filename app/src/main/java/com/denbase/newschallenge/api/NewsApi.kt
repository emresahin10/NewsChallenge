package com.denbase.newschallenge.api

import com.denbase.newschallenge.data.model.ArticleItem
import com.denbase.newschallenge.data.model.NewsSourcesItem
import com.denbase.newschallenge.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsSourcesItem>

    @GET("top-headlines")
    suspend fun getNewsWithSource(
        @Query("sources") source: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<ArticleItem>
}