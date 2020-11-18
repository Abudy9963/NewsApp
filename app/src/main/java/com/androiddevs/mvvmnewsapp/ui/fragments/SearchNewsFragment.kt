package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.util.Resource
import com.androiddevs.mvvmnewsapp.adapter.NewsAdapters
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.android.synthetic.main.fragment_search_news.paginationProgressBar
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class SearchNewsFragment:Fragment(R.layout.fragment_search_news) {

    lateinit var newsAdapters: NewsAdapters
    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).newsModel
        setUpRecyclerView()


        var job:Job? = null
        etSearch.addTextChangedListener {
            job?.cancel()
            job=CoroutineScope(IO).launch {
                delay(500L)
                if (it!=null){
                    viewModel.getSearch(it.toString())

                    println("Debug : ${ Thread.currentThread().name}" )
                }

            }


        }
        newsAdapters.setOnItemClickListener { article ->
            val bundle = Bundle()
            bundle.putSerializable("article", article)
            findNavController().navigate(R.id.action_searchNewsFragment_to_articalFragment, bundle)
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer {

            when(it){
                is Resource.Success ->{
                    paginationProgressBar.visibility=View.INVISIBLE
                    newsAdapters.differ.submitList(it.data?.articles)

                }
                is Resource.Loading ->{
                    paginationProgressBar.visibility=View.VISIBLE
                }
                is Resource.Error ->{
                    paginationProgressBar.visibility=View.INVISIBLE
                    Toast.makeText(activity,"Error Connection",Toast.LENGTH_SHORT).show()
                }
            }
        })







    }

    private fun setUpRecyclerView() {
         newsAdapters= NewsAdapters()
        rvSearchNews.apply {
            adapter=newsAdapters
            layoutManager=LinearLayoutManager(activity)
        }
    }
}