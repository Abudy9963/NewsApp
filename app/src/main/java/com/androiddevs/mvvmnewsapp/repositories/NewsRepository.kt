package com.androiddevs.mvvmnewsapp.repositories

import androidx.lifecycle.LiveData
import com.androiddevs.mvvmnewsapp.Article
import com.androiddevs.mvvmnewsapp.models.NewsResponce
import com.androiddevs.mvvmnewsapp.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.db.ArticleDatabase
import retrofit2.Response

class NewsRepository (val db : ArticleDatabase){

    suspend fun getBreakingNews(country:String,page:Int ):Response<NewsResponce>{
        return RetrofitInstance.api.getBreakingNews(country,page)
    }

    suspend fun getSearch(txt :String):Response<NewsResponce>{
       return RetrofitInstance.api.searchForNews(txt)
    }
    suspend fun insert(article: Article){
        db.getArticleDao()?.upsert(article)
    }

    fun getAll():LiveData<List<Article>>{
        return db.getArticleDao()?.getAll()!!
    }
    suspend fun delete(article: Article){
        db.getArticleDao().deleteArticle(article)
    }


}