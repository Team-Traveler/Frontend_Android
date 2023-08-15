package com.example.traveler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.Interface.DayInterface
import com.example.traveler.R
import com.example.traveler.databinding.OuterCostLayoutBinding
import com.example.traveler.model.CostDto
import com.example.traveler.model.DayDto

class DayAdapter(private val dayDataList: List<DayDto>) :
    RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    private val costDataList = ArrayList<CostDto>()

    class DayViewHolder
        (
        val binding: OuterCostLayoutBinding
    ) :
        RecyclerView.ViewHolder(binding.root), DayInterface {
        val costRecyclerView: RecyclerView = binding.costRecyclerView
        private var costAdapter: CostAdapter = CostAdapter(ArrayList())

        fun bind(dayDto: DayDto) {
            binding.date.text = dayDto.date
            binding.day.text = dayDto.day

            //자식 리사이클러뷰
            val costAdapter = CostAdapter(dayDto.costDataList)
            costRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            costRecyclerView.adapter = costAdapter
        }
        override fun onOkButtonClicked(category: String, content: String, cost: String) {
            val cost = CostDto(0, category, content, cost)
            costAdapter.addCostItem(cost)
            Toast.makeText(itemView.context,"추가", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.outer_cost_layout, parent, false)
        return DayViewHolder(OuterCostLayoutBinding.bind(view))
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {

        val dayDto = dayDataList[position]
        holder.bind(dayDto)

    }

    override fun getItemCount(): Int {
        return dayDataList.size
    }
}