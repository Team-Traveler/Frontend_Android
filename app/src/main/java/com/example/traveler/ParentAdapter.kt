package com.example.traveler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.ItemTravelRecyclerBinding


//n일차 만큼 큰 틀 생성
//Contents[position]의 day 만큼.. 생성해주기

class ParentAdapter(private val parentDataList: List<ParentData>) :
    RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {

    class ParentViewHolder(val binding: ItemTravelRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        val childRecyclerView: RecyclerView = binding.childRecyclerView
        fun bind(parentData: ParentData) {
            binding.date.text = parentData.date
            binding.date2.text = parentData.day

            //[편집] 클릭 시,
            binding.edit.setOnClickListener {


            }

            //자식 리사이클러뷰
            val childAdapter = ChildAdapter(parentData.childDataList)
            childRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            childRecyclerView.adapter = childAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_travel_recycler, parent, false)
        return ParentViewHolder(ItemTravelRecyclerBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {

        val parentData = parentDataList[position]
        holder.bind(parentData)

    }


    override fun getItemCount(): Int {
        return parentDataList.size
    }
}
