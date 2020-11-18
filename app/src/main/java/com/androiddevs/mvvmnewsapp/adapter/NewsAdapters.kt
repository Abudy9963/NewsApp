package com.androiddevs.mvvmnewsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.Article
import com.androiddevs.mvvmnewsapp.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapters :RecyclerView.Adapter<NewsAdapters.ArticleViewHolder>() {
    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    private val differCallBack= object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem

        }

    }
    val differ =AsyncListDiffer(this,differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
     val v =LayoutInflater.from(parent.context).inflate(
         R.layout.item_article_preview,
         parent,
         false)
        return ArticleViewHolder(
            v
        )
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
       var article  =differ.currentList.get(position)
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)

            tvDescription.text=article.description
            tvPublishedAt.text=article.publishedAt
            tvSource.text=article.source.name
            title.text=article.title
            setOnClickListener {
               onClickListener?.let { it(article) }
            }
        }
    }
    private var onClickListener: ((Article) -> Unit)? =null

    fun setOnItemClickListener(listener :((Article)->Unit)){
        onClickListener=listener
    }
}