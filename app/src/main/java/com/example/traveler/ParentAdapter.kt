package com.example.traveler

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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

            if (parentData.childDataList.isNotEmpty()) { // childDataList가 비어있지 않은 경우
                val childAdapter = ChildAdapter(parentData.childDataList)
                binding.childRecyclerView.layoutManager = LinearLayoutManager(binding.childRecyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                binding.childRecyclerView.adapter = childAdapter
                childAdapter.notifyDataSetChanged()

                binding.childRecyclerView.visibility = View.VISIBLE
                binding.placeholderTextView.visibility = View.GONE
            } else {
                // childDataList가 비어있는 경우에 대한 처리
                // 예를 들어, childRecyclerView를 숨기거나 플레이스홀더를 표시할 수 있습니다.
                binding.childRecyclerView.visibility = View.GONE
                binding.placeholderTextView.visibility = View.VISIBLE

            }

            //[편집] 클릭 시,
            binding.edit.setOnClickListener {
                //장소 추가 페이지로 이동

                val intent = Intent(itemView.context, AddPlaceActivity::class.java)

                // 데이터 전달 (예시: 문자열 데이터)
                intent.putExtra("date", binding.date.text)
                intent.putExtra("day", binding.date2.text)


                itemView.context.startActivity(intent)

            }

            //자식 리사이클러뷰
            val childAdapter = ChildAdapter(parentData.childDataList)
            childRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            childRecyclerView.adapter = childAdapter
            childAdapter.notifyDataSetChanged() // 새로 추가한 부분

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
