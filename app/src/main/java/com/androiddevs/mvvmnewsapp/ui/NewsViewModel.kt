package com.androiddevs.mvvmnewsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.Article
import com.androiddevs.mvvmnewsapp.models.NewsResponce
import com.androiddevs.mvvmnewsapp.repositories.NewsRepository
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository : NewsRepository) : ViewModel() {

    var breakingNews=MutableLiveData<Resource<NewsResponce>>()


    var searchNews=MutableLiveData<Resource<NewsResponce>>()


    lateinit var savedNews:LiveData<List<Article>>

    fun getBreakingNews(country:String,page :Int){viewModelScope.launch {
        breakingNews.postValue(Resource.Loading<NewsResponce>())
        val response=newsRepository.getBreakingNews(country,page)
        breakingNews.postValue(handleResponse(response))

    }
    }

    fun delete(article: Article){viewModelScope.launch {
        newsRepository.delete(article)
    }}
    fun insert(article: Article){viewModelScope.launch {
        newsRepository.insert(article)
        println("Debug : article inserted")
    }

    }

    fun getAll():LiveData<List<Article>>{
         savedNews=newsRepository.getAll()
        return savedNews

    }

    fun getSearch(txt :String){viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        handleResponse(newsRepository.getSearch(txt)).let {
            searchNews.postValue(it)
        }
    }

    }
    private fun handleResponse(response:Response<NewsResponce> ): Resource<NewsResponce> {
        if (response.isSuccessful){
            response.body()?.let {it->
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
            }


}





