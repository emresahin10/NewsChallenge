package com.denbase.newschallenge.data.model


data class ArticleItem(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)