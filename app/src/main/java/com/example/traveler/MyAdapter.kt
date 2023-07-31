package com.example.traveler


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.ItemRecyclerBinding

//리사이클러뷰

class MyAdapter(private val items: ArrayList<Contents>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null



    class ViewHolder(val binding: ItemRecyclerBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler,parent,false)
        return ViewHolder(ItemRecyclerBinding.bind(view))

    }
    override fun onBindViewHolder(viewHolder: ViewHolder,position:Int){
        viewHolder.binding.textView.text=items[position].name
        viewHolder.binding.day.text=items[position].day
        viewHolder.binding.date.text=items[position].date

    }

    override fun getItemCount(): Int = items.size

    // 외부에서 아이템 클릭 리스너 설정
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    // 아이템 클릭 리스너 인터페이스
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }





}