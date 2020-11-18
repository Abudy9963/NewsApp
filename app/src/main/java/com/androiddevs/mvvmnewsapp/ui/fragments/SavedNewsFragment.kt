package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapter.NewsAdapters
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedNewsFragment:Fragment(R.layout.fragment_saved_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapters: NewsAdapters

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).newsModel
        setupRecyclerView()

        viewModel.getAll().observe(viewLifecycleOwner, Observer {
            newsAdapters.differ.submitList(it)
        })

        val itemTouchHelper=object:ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
               return  true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val position =viewHolder.adapterPosition
               val article= newsAdapters.differ.currentList[position]
                viewModel.delete(article)
                Snackbar.make(view,"Success deleted ", Snackbar.LENGTH_LONG).setAction("Undo"){
                    viewModel.insert(article)
                }.show()
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rvSavedNews)





        /*  on Item Click */
        newsAdapters.setOnItemClickListener { article ->
            val bundle = Bundle()
            bundle.putSerializable("article", article)
            findNavController().navigate(R.id.action_savedNewsFragment_to_articalFragment, bundle)
        }



    }
    private fun setupRecyclerView(){
        rvSavedNews.apply {
            newsAdapters= NewsAdapters()
            adapter=newsAdapters
            layoutManager= LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
        }
    }
}