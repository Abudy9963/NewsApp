package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.androiddevs.mvvmnewsapp.Article
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

class ArticalFragment:Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).newsModel

        val article =arguments?.get("article") as Article
        webView.webViewClient= WebViewClient()
        webView.loadUrl(article.url)
fab.setOnClickListener {
    viewModel.insert(article)
    Snackbar.make(view,"Success Saved ",Snackbar.LENGTH_LONG).show()
}




    }
}