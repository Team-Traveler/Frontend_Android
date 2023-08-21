package com.example.traveler.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.AddActivity
import com.example.traveler.Interface.DayDialogInterface
import com.example.traveler.R
import com.example.traveler.databinding.OuterCostLayoutBinding
import com.example.traveler.databinding.OuterItemLayoutBinding
import com.example.traveler.dialog.CostDialog
import com.example.traveler.dialog.InnerDialog
import com.example.traveler.model.CostDto
import com.example.traveler.model.DayDto
import com.example.traveler.model.InnerDto
import com.example.traveler.viewmodel.CostViewModel
import com.example.traveler.viewmodel.InnerViewModel

class DayAdapter(private val dayDataList: List<DayDto>, private val costViewModel: CostViewModel) :
    RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    private val costDataList = ArrayList<CostDto>()
    class DayViewHolder
        (
        val binding: OuterCostLayoutBinding,
        private val costViewModel: CostViewModel
    ) :
        RecyclerView.ViewHolder(binding.root), DayDialogInterface {
        val costRecyclerView: RecyclerView = binding.costRecyclerView
        private var costAdapter: CostAdapter = CostAdapter(ArrayList(), costViewModel)

        fun bind(dayDto: DayDto) {
            binding.date.text = dayDto.date
            binding.day.text = dayDto.day

            //자식 리사이클러뷰
//            val costAdapter = CostAdapter(dayDto)
            costRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            costRecyclerView.adapter = costAdapter
            val REQUEST_CODE_ADD = Bundle()

            // 비용추가를 누르면 AddActivity로 이동
            binding.addCostBtn.setOnClickListener {
//                val intent = Intent(binding.root.context, AddActivity::class.java)
//                binding.root.context.startActivity(intent, REQUEST_CODE_ADD)
                onBtnClicked()
            }

            binding.editBtnCost.setOnClickListener {
                if (binding.editBtnCost.text == "편집") {
                    binding.editBtnCost.text = "편집 완료"
                }
                else {
                    binding.editBtnCost.text = "편집"
                }
                costAdapter.updateButtonState()
            }
        }
        private fun onBtnClicked(){
            val costDialog = CostDialog(itemView.context, this)
            costDialog.show()
        }
        override fun onOkButtonClicked(category:String, content: String, cost: String) {
            val cost = CostDto(0, category, content, cost)
            costAdapter.addCostItem(cost)
            Toast.makeText(itemView.context,"추가", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.outer_cost_layout, parent, false)
        val itemBinding = OuterCostLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayViewHolder(itemBinding, costViewModel)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {

        val dayDto = dayDataList[position]
        holder.bind(dayDto)

    }

    override fun getItemCount(): Int {
        return dayDataList.size
    }
}