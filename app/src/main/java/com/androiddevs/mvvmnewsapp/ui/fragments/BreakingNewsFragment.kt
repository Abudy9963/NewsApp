package com.androiddevs.mvvmnewsapp.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.util.Resource
import com.androiddevs.mvvmnewsapp.adapter.NewsAdapters
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment:Fragment(R.layout.fragment_breaking_news) {

     lateinit var viewModel: NewsViewModel
    lateinit var newsAdapters: NewsAdapters


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).newsModel

        setupRecyclerView()
        var page=1

        isOnline(activity!!)
        if (isOnline(activity!!)){
            try {
                viewModel.getBreakingNews("us", page)
            }catch (t :Throwable){
                println("No Internet $t")
            }

        }else{
            Snackbar.make(view,"no connecting",Snackbar.LENGTH_LONG).show()
        }






        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {

            when (it){
                is Resource.Success ->{
                    paginationProgressBar.visibility=View.INVISIBLE
                    newsAdapters.differ.submitList(it.data?.articles)
                }

                is Resource.Loading ->{
                    paginationProgressBar.visibility=View.VISIBLE
                }
                is Resource.Error ->{
                    paginationProgressBar.visibility=View.INVISIBLE
                    println("Error ${it.message}")
                }

            }
        })

        newsAdapters.setOnItemClickListener {article ->
            val bundle=Bundle()
            bundle.putSerializable("article",article)
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articalFragment,bundle)


        }


    }
    private fun setupRecyclerView(){
        rvBreakingNews.apply {
            newsAdapters= NewsAdapters()
            adapter=newsAdapters
            layoutManager= LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
        }
        return false
    }

}