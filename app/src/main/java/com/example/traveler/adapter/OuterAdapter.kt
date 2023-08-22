package com.example.traveler.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.Interface.OuterDialogInterface
import com.example.traveler.Interface.UpdateDialogInterface
import com.example.traveler.databinding.OuterItemLayoutBinding
import com.example.traveler.dialog.InnerDialog
import com.example.traveler.dialog.UpdateDialog
import com.example.traveler.model.InnerDto
import com.example.traveler.model.OuterDto
import com.example.traveler.viewmodel.InnerViewModel
import com.example.traveler.viewmodel.OuterViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class OuterAdapter(private var outerList: List<OuterDto>, private val innerViewModel: InnerViewModel, private val outerViewModel: OuterViewModel,  private val uiUpdateListener: UiUpdateListener) :
    RecyclerView.Adapter<OuterAdapter.OuterViewHolder>() {

    interface UiUpdateListener {
        fun onUiUpdateSuccess(responseBody: String)
        fun onUiUpdateFailure()
    }

    inner class OuterViewHolder(
        val itemBinding: OuterItemLayoutBinding,
        private val innerViewModel: InnerViewModel
    ) :
        RecyclerView.ViewHolder(itemBinding.root), OuterDialogInterface {
        private val innerList = ArrayList<InnerDto>()
        private var innerAdapter: InnerAdapter = InnerAdapter(ArrayList(), innerViewModel, uiUpdateListener)

        fun bind(outer: OuterDto) {
            itemBinding.etCategoryTitle.setText(outer.title)
            val layoutManager = GridLayoutManager(itemView.context, 2) // 한 줄에 2개의 열로 설정
            itemBinding.innerRecyclerView.layoutManager = layoutManager
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

            itemBinding.editImgBtn.setOnClickListener {
                val updateDialog = UpdateDialog(itemBinding.root.context, object :
                    UpdateDialogInterface {
                    override fun onOkButtonClicked(id: Int, title: String) {
                        // 업데이트 버튼이 클릭되었을 때 처리 로직을 여기에 구현합니다.
                        // newTitle 값에 변경된 제목이 전달됩니다.

                        // 체크리스트 제목 변경 API 호출
                        val client = OkHttpClient()
                        val mediaType = "application/json".toMediaType()
                        val body = "{\"newTitle\": \" 새로운 체크리스트 제목 \"}".toRequestBody(mediaType)
                        val request = Request.Builder()
                            .url("http://15.164.232.95:9000/checklist/111/title")
                            .patch(body)
                            .addHeader("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiZXhwIjoxNjg5Njk1NTE0fQ.mXTd2f1NwwTKygOxknRJTp-NnAinpE_w1IHAnGTDya-aWQuQDXT_E0a8i1NP4Qd8vRrkmdD9Nie41Mx4ruLb1w")
                            .addHeader("Content-Type", "application/json")
                            .build()

                        client.newCall(request).enqueue(object : okhttp3.Callback {
                            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                                if (response.isSuccessful) {
                                    val responseBody = response.body?.string()
                                    uiUpdateListener.onUiUpdateSuccess(responseBody ?: "")
                                    // 업데이트 성공 시 카테고리 제목을 업데이트
                                    val updatedOuter = outer.copy(title = title)
                                    outerViewModel.updateOuter(updatedOuter)
                                } else {
                                    uiUpdateListener.onUiUpdateFailure()
                                }
                            }

                            override fun onFailure(call: okhttp3.Call, e: IOException) {
                                uiUpdateListener.onUiUpdateFailure()
                                Log.e("OuterAdapter", "네트워크 오류")
                                Toast.makeText(itemBinding.root.context, "네트워크 오류", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                })
                updateDialog.show()
            }

            // 카테고리 전체삭제 버튼
            itemBinding.deleteCategoryBtn.setOnClickListener {
                outerViewModel.deleteOuter(outer)

                //카테고리 생성 api에서 생성되는 cid값을 삭제 api cid값으로 넣어주고 실행후 카테고리를 삭제하면 requestbody 잘 뜸.
                val client = OkHttpClient()
                val mediaType = "text/plain".toMediaType()
                val body = "".toRequestBody(mediaType)
                val request = Request.Builder()
                    .url("http://15.164.232.95:9000/checklist/110")
                    .method("DELETE", body)
                    .addHeader("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiZXhwIjoxNjg5Njk1NTE0fQ.mXTd2f1NwwTKygOxknRJTp-NnAinpE_w1IHAnGTDya-aWQuQDXT_E0a8i1NP4Qd8vRrkmdD9Nie41Mx4ruLb1w")
                    .build()

                client.newCall(request).enqueue(object : okhttp3.Callback {
                    override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                        if (response.isSuccessful) {
                            val responseBody = response.body?.string()
                            uiUpdateListener.onUiUpdateSuccess(responseBody ?: "")
                        } else {
                            uiUpdateListener.onUiUpdateFailure()
                        }
                    }

                    override fun onFailure(call: okhttp3.Call, e: IOException) {
                        uiUpdateListener.onUiUpdateFailure()
                        Log.e("OuterAdapter", "네트워크 오류")
                        Toast.makeText(itemView.context, "네트워크 오류", Toast.LENGTH_SHORT).show()
                    }
                })

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
//            innerViewModel.addInner(inner)
            //innerList에 항목 추가
//            innerList.add(inner)

            //추가된 항목을 RecyclerView에 적용
//            innerAdapter.notifyItemInserted(innerList.size - 1)
            innerAdapter.addInnerItem(inner)
            Toast.makeText(itemView.context,"추가", Toast.LENGTH_SHORT).show()

            val client = OkHttpClient()
            val mediaType = "text/plain".toMediaType()
            val body = "".toRequestBody(mediaType)
            val request = Request.Builder()
                .url("http://15.164.232.95:9000/checklist/142/items")
                .post(body)
                .addHeader("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiZXhwIjoxNjg5Njk1NTE0fQ.mXTd2f1NwwTKygOxknRJTp-NnAinpE_w1IHAnGTDya-aWQuQDXT_E0a8i1NP4Qd8vRrkmdD9Nie41Mx4ruLb1w")
                .build()

            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        uiUpdateListener.onUiUpdateSuccess(responseBody ?: "")
                    } else {
                        uiUpdateListener.onUiUpdateFailure()
                    }
                }

                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    uiUpdateListener.onUiUpdateFailure()
                    Log.e("OuterAdapter", "네트워크 오류")
                    Toast.makeText(itemView.context, "네트워크 오류", Toast.LENGTH_SHORT).show()
                }
            })
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