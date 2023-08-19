package com.example.traveler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.InnerCostLayoutBinding
import com.example.traveler.model.CostDto

class CostAdapter(private var costDataList: List<CostDto>) : RecyclerView.Adapter<CostAdapter.CostViewHolder>() {

    private var isEditButtonCostClicked = false

    class CostViewHolder(val binding: InnerCostLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(costDto: CostDto) {
            binding.tvCategory.text = costDto.category
            binding.tvContent.text = costDto.content
            binding.tvCost.text = costDto.cost
        }
        init {
            // Delete 버튼을 클릭할 때의 동작 설정
            binding.deleteImgBtn2.setOnClickListener {
                // 버튼 동작 처리
                // 이 부분에서 해당 아이템 삭제 등의 동작을 수행하면 됩니다.
            }
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