package com.example.traveler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.ItemPickRecyclerBinding

class PickAdapter(private var items: MutableList<PickData>) :
RecyclerView.Adapter<PickAdapter.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPickRecyclerBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.title.text = item.title  //여행타이틀
        holder.binding.hashtag.text = item.hashtag  //여행해시태그


    }

    inner class ViewHolder(val binding: ItemPickRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
/*        init {
            //text 클릭 시, 이벤트 처리 코드 추가
            //클릭 이벤트 발새 시, onitemclicklistener 호출해
            //해당 아이템의 위치 전달 (상세보기 클릭 시 이벤트 )
            binding.detail.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(position)


                }
            }
        }*/



    }

    override fun getItemCount(): Int = items.size

    // 아이템을 얻을 수 있는 getItem 함수 추가
    fun getItem(position: Int): PickData {
        return items[position]
    }

    // 외부에서 아이템 클릭 리스너 설정
    fun setOnItemClickListener(listener:  PickAdapter.OnItemClickListener) {
        itemClickListener = listener
    }

    // 아이템 클릭 리스너 인터페이스
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}



