package com.example.traveler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.R
import com.example.traveler.model.TravelDto

class ChoiceAdapter(private val travelList: List<TravelDto>) :
    RecyclerView.Adapter<ChoiceAdapter.ChoiceViewHolder>() {

    class ChoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ViewHolder 내부에서 필요한 뷰들을 findViewById 등으로 초기화
        val textView1: TextView = itemView.findViewById(R.id.textView5)
        val textView2: TextView = itemView.findViewById(R.id.textView16)
        val textView3: TextView = itemView.findViewById(R.id.textView17)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ChoiceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChoiceViewHolder, position: Int) {
        val currentItem = travelList[position]

        holder.textView1.text = currentItem.title
        holder.textView2.text = currentItem.day
        holder.textView3.text = currentItem.date
    }

    override fun getItemCount() = travelList.size
}
