package com.example.traveler.adapter

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.OuterItemLayoutBinding
import com.example.traveler.dialog.InnerDialog
import com.example.traveler.model.OuterDto
import com.example.traveler.dialogInterface.OuterDialogInterface
import com.example.traveler.model.InnerDto
import com.example.traveler.viewmodel.InnerViewModel
import com.example.traveler.viewmodel.OuterViewModel

class OuterAdapter(private var outerList: List<OuterDto>, private val innerViewModel: InnerViewModel, private val outerViewModel: OuterViewModel) :
    RecyclerView.Adapter<OuterAdapter.OuterViewHolder>() {

    private val innerList = ArrayList<InnerDto>()

    inner class OuterViewHolder(
        val itemBinding: OuterItemLayoutBinding,
        private val innerViewModel: InnerViewModel
    ) :
        RecyclerView.ViewHolder(itemBinding.root), OuterDialogInterface {
        private var innerAdapter: InnerAdapter = InnerAdapter(innerList, innerViewModel)

        fun bind(outer: OuterDto) {
            itemBinding.etCategoryTitle.setText(outer.title)

            itemBinding.innerRecyclerView.layoutManager = LinearLayoutManager(itemView.context,
                LinearLayoutManager.VERTICAL,false)
            itemBinding.innerRecyclerView.adapter = innerAdapter

            itemBinding.addImgBtn.setOnClickListener {
                onBtnClicked()
            }
            itemBinding.editBtn.setOnClickListener {
                if (itemBinding.editBtn.text == "편집") {
                    itemBinding.editBtn.text = "편집 완료"
                    itemBinding.addImgBtn.visibility = View.GONE
                    itemBinding.deleteCategoryBtn.visibility = View.VISIBLE
                }
                else {
                    itemBinding.editBtn.text = "편집"
                    itemBinding.deleteCategoryBtn.visibility = View.GONE
                    itemBinding.addImgBtn.visibility = View.VISIBLE
                }
                innerAdapter.updateButtonState()
            }
            // 처음에 editText 눌러도 키보드 안 뜨게 설정
            itemBinding.etCategoryTitle.setOnTouchListener { v, event ->
                v.performClick()
                val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                imm.hideSoftInputFromWindow(v.windowToken, 0)
                true
            }
            // 카테고리 제목 수정 버튼
            itemBinding.editImgBtn.setOnClickListener {
                itemBinding.etCategoryTitle.requestFocus()
                // 키보드를 자동으로 보이게 설정
                val imm = it.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            }
            // 카테고리 전체삭제 버튼
            itemBinding.deleteCategoryBtn.setOnClickListener {
                outerViewModel.deleteOuter(outer)
            }
        }
        // Fab 클릭시 다이얼로그 띄움
        private fun onBtnClicked(){
            val innerDialog = InnerDialog(itemView.context, this)
            innerDialog.show()
        }
        override fun onOkButtonClicked(title: String) {
            val inner = InnerDto(0, false, title)
            // inner 리스트 추가하는 부분 다시 해야함. ROOM에 저장하기. 지금은 단순 추가
            innerViewModel.addInner(inner)
            //innerList에 항목 추가
            innerList.add(inner)
            //추가된 항목을 RecyclerView에 적용
            innerAdapter.notifyItemInserted(innerList.size - 1)
            Toast.makeText(itemView.context,"추가", Toast.LENGTH_SHORT).show()
        }
    }
    // RecyclerView.Adapter 상속 시 무조건 override 해야하는 fun - viewHolder에 layout inflate 하는 함수 (ViewBinding 사용)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OuterViewHolder {
        val itemBinding = OuterItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OuterViewHolder(itemBinding, innerViewModel)
    }

    // RecyclerView.Adapter 상속 시 무조건 override 해야하는 fun - viewHolder에 각 view를 bind하는 함수
    // ToDoViewHolder내에 bind함수 정의했으므로, 각 Todos[position]인 item data랑 view를 bind하면 됨
    override fun onBindViewHolder(holder: OuterViewHolder, position: Int) {
        val currentItem = outerList[position]
        holder.bind(currentItem)
        val currentTitle = currentItem.title

        holder.itemBinding.etCategoryTitle.setText(currentTitle)
    }

    // RecyclerView.Adapter 상속 시 무조건 override 해야하는 fun - 보통 Todos.size를 return, RecyclerView내의 item 개수 return하는 함수
    override fun getItemCount(): Int {
        return outerList.size
    }

    // Todos list의 각 item id return
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setOuterData(outerData: List<OuterDto>){
        outerList = outerData
        notifyDataSetChanged()
    }
}