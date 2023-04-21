package com.azamovhudstc.newsappwithdagger2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azamovhudstc.newsappwithdagger2.databinding.ItemOnBoardingPagerBinding
import com.squareup.picasso.Picasso

class OnBoardingAdapter(
    private val list: List<String>
) : RecyclerView.Adapter<OnBoardingAdapter.OBVH>() {

    inner class OBVH(private val itemBinding: ItemOnBoardingPagerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(image_url: String) {
            Picasso.get().load(image_url).into(itemBinding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OBVH {
        return OBVH(
            ItemOnBoardingPagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OBVH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

}