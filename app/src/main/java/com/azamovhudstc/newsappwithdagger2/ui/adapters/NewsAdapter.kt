package com.azamovhudstc.newsappwithdagger2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azamovhudstc.newsappwithdagger2.data.models.news_by_category.News
import com.azamovhudstc.newsappwithdagger2.databinding.ItemNewsBinding
import com.squareup.picasso.Picasso

class NewsAdapter constructor(val listener: OnItemClickListener) :
    RecyclerView.Adapter<NewsAdapter.NVH>() {

    private var list: List<News> = arrayListOf()

    inner class NVH(private val itemBinding: ItemNewsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(news: News) {
            itemBinding.apply {
                if (news.image_url!=null && news.image_url.isNotEmpty()){
                Picasso.get().load(news.image_url).into(itemBinding.imageView) }
                if (news.categories!=null && news.categories.isNotEmpty()) {
                    categoryTv.text = news.categories[0] ?: ""
                }
                titleTv.text = news.title ?: news.description
                root.setOnClickListener {
                    listener.onItemClicked(news)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NVH {
        return NVH(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = if (list.size > 6) 6 else list.size

    fun submitList(list: List<News>) {
        this.list = list
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClicked(news: News)
    }
}