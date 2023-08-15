package com.example.traveler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.InnerCostLayoutBinding
import com.example.traveler.model.CostDto

class CostAdapter(private var costDataList: List<CostDto>) : RecyclerView.Adapter<CostAdapter.CostViewHolder>() {

    private var isEditButtonCostClicked = false

    fun addCostItem(costDto: CostDto) {
        costDataList = costDataList + costDto
        notifyDataSetChanged()
    }

    class CostViewHolder(val binding: InnerCostLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(costDto: CostDto) {
            binding.tvCategory.text = costDto.category
            binding.tvContent.text = costDto.content
            binding.tvCost.text = costDto.cost
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = InnerCostLayoutBinding.inflate(inflater, parent, false)

        return CostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CostViewHolder, position: Int) {
        val costDto = costDataList[position]
        holder.bind(costDto)

        if (isEditButtonCostClicked) {
            holder.binding.deleteImgBtn2.visibility = View.VISIBLE
        } else {
            holder.binding.deleteImgBtn2.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return costDataList.size
    }

    fun updateButtonState() {
        isEditButtonCostClicked = !isEditButtonCostClicked
        notifyDataSetChanged()
    }
}