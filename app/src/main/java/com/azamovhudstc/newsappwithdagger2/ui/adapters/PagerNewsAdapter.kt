package com.azamovhudstc.newsappwithdagger2.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azamovhudstc.newsappwithdagger2.R
import com.azamovhudstc.newsappwithdagger2.data.local.dao.NewsDao
import com.azamovhudstc.newsappwithdagger2.data.models.news_by_category.News
import com.azamovhudstc.newsappwithdagger2.databinding.ItemNewsPagerBinding
import com.squareup.picasso.Picasso

class PagerNewsAdapter(
    private val listener: OnItemClickListener,
    private val newsDao: NewsDao
)
    : RecyclerView.Adapter<PagerNewsAdapter.NPVH>() {

    private var list: List<News> = arrayListOf()

    inner class NPVH(private val itemBinding: ItemNewsPagerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(news: News) {
            itemBinding.apply {
                if (news.image_url != null && news.image_url.isNotEmpty()) {
                    Picasso.get().load(news.image_url).into(itemBinding.imageView)
                }
                categoryTv.text = news.categories?.get(0) ?: ""
                descTv.text = news.title ?: news.description
                var local = newsDao.getNewsByUUID(news.uuid)
                if (local != null && local.isSaved) {
                    saveIv.setImageResource(R.drawable.ic_baseline_bookmark_24)
                } else {
                    local = news
                    saveIv.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                }

                root.setOnClickListener {
                    listener.onItemClicked(news)
                }
                saveIv.setOnClickListener {
                    if (local.isSaved) {
                        saveIv.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                        local.isSaved = false
                        newsDao.insert(local)
                    } else {
                        saveIv.setImageResource(R.drawable.ic_baseline_bookmark_24)
                        local.isSaved = true
                        newsDao.insert(local)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NPVH {
        return NPVH(
            ItemNewsPagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NPVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = if (list.size > 8) 8 else list.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<News>) {
        this.list = list
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClicked(news: News)
    }
}