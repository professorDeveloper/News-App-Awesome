package com.azamovhudstc.newsappwithdagger2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azamovhudstc.newsappwithdagger2.databinding.ItemTopicBinding
import com.azamovhudstc.newsappwithdagger2.utils.Topics.getTopicsListText

class TopicFavoriteAdapter(
    private val list: List<String>
    ) :
    RecyclerView.Adapter<TopicFavoriteAdapter.TFVH>() {

    private var selectedList = arrayListOf<String>()
    private var topics = arrayListOf<String>()

    inner class TFVH(private val itemBinding: ItemTopicBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(topic: String, position: Int) {
            if (position == 0) topics = getTopicsListText()
            itemBinding.checkbox.text = topic
            itemBinding.checkbox.setOnCheckedChangeListener { p0, p1 ->
                if (p1) {
                    selectedList.add(topics[position].lowercase())
                } else selectedList.remove(topics[position].lowercase())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TFVH {
        return TFVH(ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TFVH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    fun getSelectedTopics(): List<String> {
        return selectedList
    }
}