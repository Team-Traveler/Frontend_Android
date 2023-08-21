package com.example.traveler

import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.ItemFirstBinding

class ItemViewHolder(private var binding: ItemFirstBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemData) {
        binding.tvImpact.text = item.impact
        binding.tvNameDate.text = item.nameDate
        binding.tvHashMain.text = item.hashTags
    }
}