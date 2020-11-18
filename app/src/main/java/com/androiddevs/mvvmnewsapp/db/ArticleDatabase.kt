package com.androiddevs.mvvmnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androiddevs.mvvmnewsapp.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticleDatabase :RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object{
        @Volatile
        private var Instance : ArticleDatabase?=null


        fun getInstance(context :Context): ArticleDatabase?{
            if (Instance ==null) {
                synchronized(this){
                  Instance =  Room.databaseBuilder(context.applicationContext, ArticleDatabase::class.java,"MyDatabase")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()

                }


            }

            return Instance
        }


    }
}