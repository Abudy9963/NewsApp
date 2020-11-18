package com.androiddevs.mvvmnewsapp.api

import com.androiddevs.mvvmnewsapp.util.Constants.Companion.API_KEY
import com.androiddevs.mvvmnewsapp.models.NewsResponce
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode:String ="us",
        @Query("page")
        category :Int =1,
        @Query("apiKey")
        ApiKey :String=API_KEY
    ):Response<NewsResponce>


    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        q :String,
        @Query("page")
        pageNumber :Int =1,
        @Query("apiKey")
        ApiKey :String=API_KEY
    ):Response<NewsResponce>

}