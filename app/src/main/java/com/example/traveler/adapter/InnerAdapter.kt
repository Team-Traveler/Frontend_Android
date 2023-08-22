package com.example.traveler.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.Interface.InnerDialogInterface
import com.example.traveler.Interface.UpdateDialogInterface
import com.example.traveler.databinding.InnerItemLayoutBinding
import com.example.traveler.dialog.InnerDialog
import com.example.traveler.dialog.UpdateDialog
import com.example.traveler.model.InnerDto
import com.example.traveler.viewmodel.InnerViewModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class InnerAdapter(private var innerList: MutableList<InnerDto>, private var innerViewModel: InnerViewModel, private val uiUpdateListener: OuterAdapter.UiUpdateListener) :
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
        private val innerAdapter: InnerAdapter,
        private val uiUpdateListener: OuterAdapter.UiUpdateListener
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
            itemBinding.root.setOnClickListener {
                val updateDialog = UpdateDialog(itemBinding.root.context, object :
                    UpdateDialogInterface {
                    override fun onOkButtonClicked(id: Int, title: String) {
                        // 업데이트 버튼이 클릭되었을 때 처리 로직을 여기에 구현합니다.
                        // newTitle 값에 변경된 제목이 전달됩니다.

                        val client = OkHttpClient()
                        val mediaType = "application/json".toMediaType()
                        val body = "{\r\n    \"name\": \"새로 바꿀 이름\"\r\n}".toRequestBody(mediaType)
                        val request = Request.Builder()
                            .url("http://15.164.232.95:9000/checklist/142/items/135")
                            .patch(body)
                            .addHeader("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiZXhwIjoxNjg5Njk1NTE0fQ.mXTd2f1NwwTKygOxknRJTp-NnAinpE_w1IHAnGTDya-aWQuQDXT_E0a8i1NP4Qd8vRrkmdD9Nie41Mx4ruLb1w")
                            .addHeader("Content-Type", "application/json")
                            .build()

                        client.newCall(request).enqueue(object : Callback {
                            override fun onResponse(call: Call, response: Response) {
                                if (response.isSuccessful) {
                                    val responseBody = response.body?.string()
                                    uiUpdateListener.onUiUpdateSuccess(responseBody ?: "")
                                    // 업데이트 성공 시 아이템 제목을 업데이트
                                    val updatedInner = inner.copy(title = title)
                                    innerViewModel.updateInner(updatedInner)
                                } else {
                                    uiUpdateListener.onUiUpdateFailure()
                                }
                            }

                            override fun onFailure(call: Call, e: IOException) {
                                uiUpdateListener.onUiUpdateFailure()
                                Log.e("OuterAdapter", "네트워크 오류")
                                Toast.makeText(itemBinding.root.context, "네트워크 오류", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                })
                updateDialog.show()
            }
        }
    }

    // RecyclerView.Adapter 상속 시 무조건 override 해야하는 fun - viewHolder에 layout inflate 하는 함수 (ViewBinding 사용)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val itemBinding = InnerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val innerAdapter: InnerAdapter by lazy { InnerAdapter(innerList, innerViewModel, uiUpdateListener) }
        return InnerViewHolder(itemBinding, innerViewModel, innerAdapter, uiUpdateListener)
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