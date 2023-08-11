package com.example.traveler

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.ItemEditRecyclerBinding

class MyAdapter2(private var items: ArrayList<Contents>) :
RecyclerView.Adapter<MyAdapter2.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEditRecyclerBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textView.text = item.name
        holder.binding.day.text = item.day
        holder.binding.date.text = item.date


    }

    inner class ViewHolder(val binding: ItemEditRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            //text 클릭 시, 이벤트 처리 코드 추가
            //클릭 이벤트 발새 시, onitemclicklistener 호출해
            //해당 아이템의 위치 전달 (상세보기 클릭 시 이벤트 )
            binding.detail.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(position)

                }
            }
            //삭제버튼 클릭 시 동작
            binding.image.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // 아이템 삭제
                    deleteItem(position)
                }
            }
        }



    }

    override fun getItemCount(): Int = items.size

    // 아이템을 얻을 수 있는 getItem 함수 추가
    fun getItem(position: Int): Contents {
        return items[position]
    }

    // 외부에서 아이템 클릭 리스너 설정
    fun setOnItemClickListener(listener: MyTravelFragment) {
        itemClickListener = listener
    }
    // 아이템 삭제하는 함수
    fun deleteItem(position: Int) {
        if (position in 0 until items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }


    // 아이템 클릭 리스너 인터페이스
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}


