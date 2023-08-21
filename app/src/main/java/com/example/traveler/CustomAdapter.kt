package com.example.traveler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.ItemFirstBinding

class CustomAdapter(private val dataSet: List<ItemData>) : RecyclerView.Adapter<CustomAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemFirstBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = dataSet[position]
        holder.bind(currentItem)

        currentItem.selectedImageUri?.let { selectedImageUri ->
            holder.binding.imgFirst.setImageURI(selectedImageUri)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ItemViewHolder(val binding: ItemFirstBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemData) {
            binding.tvImpact.text = item.impact
            binding.tvNameDate.text = item.nameDate
            binding.tvHashMain.text = item.hashTags

            // Set selected image URI to imgFirst
            item.selectedImageUri?.let { selectedImageUri ->
                binding.imgFirst.setImageURI(selectedImageUri)
            }

            // Set click listener for the whole item
            binding.root.setOnClickListener {
                //val selectedImageUri: Uri? = /* Get the selected image URI here */
                //item.selectedImageUri = selectedImageUri
                //notifyItemChanged(adapterPosition)
            }
        }
    }
}