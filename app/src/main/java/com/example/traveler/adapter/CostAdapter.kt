package com.example.traveler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.InnerCostLayoutBinding
import com.example.traveler.model.CostDto
import com.example.traveler.model.InnerDto
import com.example.traveler.viewmodel.CostViewModel
import com.example.traveler.viewmodel.InnerViewModel

class CostAdapter(private var costDataList: MutableList<CostDto>, private var costViewModel: CostViewModel) : RecyclerView.Adapter<CostAdapter.CostViewHolder>() {

    private var isEditButtonCostClicked = false

    fun addCostItem(cost: CostDto) {
        costDataList = (costDataList + cost).toMutableList()
        notifyDataSetChanged()
    }
    fun deleteCostItem(position: Int) {
        if (position in 0 until costDataList.size) {
            costDataList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    class CostViewHolder(val binding: InnerCostLayoutBinding, private val costViewModel: CostViewModel, private val costAdapter: CostAdapter) : RecyclerView.ViewHolder(binding.root) {
        fun bind(costDto: CostDto) {
            binding.tvCategory.text = costDto.category
            binding.tvContent.text = costDto.content
            binding.tvCost.text = costDto.cost
            // Delete 버튼을 클릭할 때의 동작 설정
            binding.deleteImgBtn2.setOnClickListener {
                val position = adapterPosition
                costAdapter.deleteCostItem(position)
                costViewModel.deleteCost(costDto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = InnerCostLayoutBinding.inflate(inflater, parent, false)

        return CostViewHolder(binding, costViewModel, this)
    }

    override fun onBindViewHolder(holder: CostViewHolder, position: Int) {
        val costDto = costDataList[position]
        holder.bind(costDto)

        if (isEditButtonCostClicked) {
            holder.binding.deleteImgBtn2.visibility = View.VISIBLE
        } else {
            holder.binding.deleteImgBtn2.visibility = View.GONE
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