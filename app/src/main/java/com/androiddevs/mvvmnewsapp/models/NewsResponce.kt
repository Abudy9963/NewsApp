package com.androiddevs.mvvmnewsapp.models

import com.androiddevs.mvvmnewsapp.Article

data class NewsResponce(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)