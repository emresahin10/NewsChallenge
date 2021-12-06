package com.denbase.newschallenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.denbase.newschallenge.data.model.Article
import com.denbase.newschallenge.databinding.RvItemNewsBinding
import com.denbase.newschallenge.utils.downloadImage

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(binding: RvItemNewsBinding): RecyclerView.ViewHolder(binding.root){
        val articleTitle = binding.txtArticleTitle
        val articleDescription = binding.txtArticleDescription
        val articleImage = binding.imgArticle
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    private var onItemClickListener: ((Article) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvItemNewsBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differ.currentList[position]

        holder.apply {
            articleTitle.text = article.title
            articleDescription.text = article.description
            articleImage.downloadImage(article.urlToImage)
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

}
