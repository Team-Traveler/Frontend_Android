package com.example.traveler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.InnerItemLayoutBinding
import com.example.traveler.model.InnerDto
import com.example.traveler.viewmodel.InnerViewModel

class InnerAdapter(private var innerList: MutableList<InnerDto>, private var innerViewModel: InnerViewModel) :
    RecyclerView.Adapter<InnerAdapter.InnerViewHolder>() {

    private var isEditButtonClicked = false

    fun addInnerItem(inner: InnerDto) {
        innerList = (innerList + inner).toMutableList()
        notifyDataSetChanged()
    }
    fun deleteInnerItem(position: Int) {
        if (position in 0 until innerList.size) {
            innerList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    class InnerViewHolder(
        val itemBinding: InnerItemLayoutBinding,
        private val innerViewModel: InnerViewModel,
        private val innerAdapter: InnerAdapter
    ):
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(inner: InnerDto) {
            itemBinding.tvTitle.text = inner.title
//            itemBinding.cbCheck.text = inner.check.toString()

            itemBinding.deleteImgBtn.setOnClickListener {
                val position = adapterPosition
                innerAdapter.deleteInnerItem(position)
                innerViewModel.deleteInner(inner)
            }
        }
    }

    // RecyclerView.Adapter 상속 시 무조건 override 해야하는 fun - viewHolder에 layout inflate 하는 함수 (ViewBinding 사용)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val itemBinding = InnerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InnerViewHolder(itemBinding, innerViewModel, this)
    }

    // RecyclerView.Adapter 상속 시 무조건 override 해야하는 fun - viewHolder에 각 view를 bind하는 함수
    // ToDoViewHolder내에 bind함수 정의했으므로, 각 Todos[position]인 item data랑 view를 bind하면 됨
    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val currentItem = innerList[position]
        holder.bind(currentItem)
        val currentTitle = currentItem.title
//        val currentCheck = currentItem.check

        holder.itemBinding.tvTitle.text = currentTitle

        if (isEditButtonClicked) {
            holder.itemBinding.cbCheck.visibility = View.INVISIBLE
            holder.itemBinding.deleteImgBtn.visibility = View.VISIBLE
        } else {
            holder.itemBinding.deleteImgBtn.visibility = View.INVISIBLE
            holder.itemBinding.cbCheck.visibility = View.VISIBLE
        }
     }

    // RecyclerView.Adapter 상속 시 무조건 override 해야하는 fun - 보통 Todos.size를 return, RecyclerView내의 item 개수 return하는 함수
    override fun getItemCount(): Int {
        return innerList.size
    }

    // Todos list의 각 item id return
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun updateButtonState() {
        isEditButtonClicked = !isEditButtonClicked
        notifyDataSetChanged()
    }
    fun setInnerData(innerData: List<InnerDto>){
        innerList = innerData.toMutableList()
        notifyDataSetChanged()
    }
}