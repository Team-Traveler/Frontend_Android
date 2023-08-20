package com.example.traveler

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.ItemTripBinding

class TriplistAdapter(
    private val context: Context,
    private val tripList: List<Tripname>,
    private val name: String,
    private val dayNight: String,
    private val date: String
) : RecyclerView.Adapter<TriplistAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = tripList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = tripList.size

    inner class ViewHolder(private val binding: ItemTripBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trip: Tripname) {
            binding.tvName.text = "$name"
            binding.tvDayNight.text = "$dayNight"
            binding.tvDate.text = "$date"

            binding.btnShare.setOnClickListener {
                val detailIntent = Intent(context, DetailActivity::class.java)
                context.startActivity(detailIntent)
            }
        }
    }
}