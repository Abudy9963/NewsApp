package com.androiddevs.mvvmnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.androiddevs.mvvmnewsapp.Article


@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend  fun upsert(article: Article) :Long

    @androidx.room.Query("SELECT * FROM articles" )
    fun getAll():LiveData<List<Article>>


    @Delete
    suspend fun deleteArticle(article: Article)






}