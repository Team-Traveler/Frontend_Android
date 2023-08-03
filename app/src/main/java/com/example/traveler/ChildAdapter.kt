package com.example.traveler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.ItemChildRecyclerBinding


class ChildAdapter(private val childDataList: List<ChildData>) : RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    class ChildViewHolder(private val binding: ItemChildRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        // 자식 리사이클러뷰의 각 아이템 뷰 요소 정의
        // 뷰 바인딩에서 사용할 함수 추가
        fun bind(childData: ChildData) {
            // 각 아이템의 데이터를 바인딩하여 표시하는 작업
            // 예시: binding.textView.text = childData.someText
            binding.button.text = childData.number.toString()
            binding.triptext.text = childData.spot
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChildRecyclerBinding.inflate(inflater, parent, false)

/*
        // RecyclerView의 높이를 설정하는 부분
        val layoutParams = binding.root.layoutParams
        layoutParams.height = 180 // 고정된 크기로 설정하거나, 원하는 크기로 설정해주세요.
        binding.root.layoutParams = layoutParams
*/

        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val childData = childDataList[position]
        holder.bind(childData)
    }

    override fun getItemCount(): Int {
        return childDataList.size
    }
}