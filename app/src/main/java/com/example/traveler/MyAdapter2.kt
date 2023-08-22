package com.example.traveler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.ApiUtil.sendDeleteRequest
import com.example.traveler.databinding.ItemEditRecyclerBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.OkHttpClient
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MyAdapter2(private var items: ArrayList<Contents>) :
    RecyclerView.Adapter<MyAdapter2.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null
    private lateinit var adapter2: MyAdapter2  // adapter 변수 선언

    init {
        adapter2 = this  // 어댑터 초기화
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEditRecyclerBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textView.text = item.title

        //수정사항 (#박#일 추가)
        val day=setDay(item.start_date,item.end_date).toInt() //몇박몇일
        val res="${day}박 ${day+1}일"
        holder.binding.day.text = "$res"


        //수정사항 (startdate~enddate)  (##/## 으로 형변환 )
        val start_date:String=setDate(item.start_date)
        holder.binding.startDate.text = start_date

        val end_date:String=setDate(item.end_date)
        holder.binding.endDate.text =end_date



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
            /*
                        binding.image.setOnClickListener {
                            val position = adapterPosition
                            if (position != RecyclerView.NO_POSITION) {
                                // 해당 아이템의 tid 값 가져오기
                                val tid = getItem(position)?.tid
                                Log.d("myadapter2 tid","$tid")

                                // tid 값이 null이 아닌 경우에만 삭제 요청 보내기
                                if (tid != null) {
                                    // 아이템 삭제
                                    deleteItem(position)

                                    sendDeleteRequest(tid) {
                                        // 삭제 요청 성공 시 UI 업데이트
                                        CoroutineScope(Dispatchers.IO).launch {
                                           // deleteItem(position)

                                            fetchAndDisplayData()
                                            Log.d("delete", "$tid")
                                        }
                                    }
                                }
                            }
                        }
            */


            binding.image.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // 해당 아이템의 tid 값 가져오기
                    val tid = getItem(position)?.tid
                    Log.d("myadapter2 tid","$tid")

                    // tid 값이 null이 아닌 경우에만 삭제 요청 보내기
                    if (tid != null) {
                        // 아이템 삭제 요청 보내기
                        sendDeleteRequest(tid) {
                            // 응답을 받은 후 실행되는 콜백 함수
                            CoroutineScope(Dispatchers.Main).launch {
                                // 아이템 삭제
                                deleteItem(position)
                                // UI 업데이트
                                adapter2.fetchAndDisplayData()
                                Log.d("delete", "$tid")
                            }
                        }
                    }
                }
            }

        }


    }

    fun fetchAndDisplayData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = Request.Builder()
                    .url("http://15.164.232.95:9000/users/my_travels")
                    .addHeader("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMSIsImV4cCI6MTY5MjY3ODEwMX0.LIZXQcGqTuSrgOr7wDJznhsmVkitbhMNitx8bdLkV6cQE5_7fic9wpskhHg9UK5ZcUfZ1LRk9Cl5wAfZ4itjlw")
                    .get()
                    .build()

                val response = ApiUtil.client.newCall(request).execute()
                Log.d("TAG1", response.toString())



                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val apiResponse = Gson().fromJson(responseBody, ApiResponse::class.java)

                    val travelList = apiResponse.result ?: emptyList()
                    Log.d("CLIENT_success2", travelList.toString())

                    // UI 업데이트 작업은 Main 스레드에서 수행
                    launch(Dispatchers.Main) {
                        // 어댑터에 데이터를 설정하고 업데이트합니다.

                        adapter2.updateData(travelList)
                    }
                } else {
                    Log.e("Network", "Request failed with response code: ${response.code}")
                }
            } catch (e: IOException) {
                e.printStackTrace()

            }
        }}

    fun updateData(newItems: List<Contents>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = items.size

    // 아이템을 얻을 수 있는 getItem 함수 추가
    fun getItem(position: Int): Contents? {
        return if (position in 0 until items.size) {
            items[position]
        } else {
            null
        }
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





    fun setDay(start: String, end: String): String {

        //데이터 값들의 모양확인 (######)로 맞출것
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("yyyyMMdd")

        //원래 형태 -> 원하는 형태로 뱐환

        //출발
        val date_s: Date = inputFormat.parse(start)  //문자열 ->객체 변환
        val start_date: String = outputFormat.format(date_s)
        //도착
        val date_e: Date = inputFormat.parse(end)  //문자열 ->객체 변환
        val end_date: String = outputFormat.format(date_e)



        Log.d("format", "$start_date") //포맷 확인
        Log.d("format", "$end_date") //포맷 확인


        val sec = (date_e.time - date_s.time) / 1000
        val days = sec / (24 * 60 * 60)
        Log.d("days: 날짜!!", "$days 일 차이남!!")


        //날짜 차이
        var calcuDate = (date_e.time - date_s.time) / (60 * 60 * 24 * 1000)

        Log.d("test: 날짜!!", "$calcuDate 일 차이남!!")


        return calcuDate.toString()

    }

    fun setDate(date_before: String): String {

        //데이터 값들의 모양확인 (######)로 맞출것
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("MM/dd")

        //원래 형태 -> 원하는 형태로 뱐환

        //출발
        val date: Date = inputFormat.parse(date_before)  //문자열 ->객체 변환
        val res: String = outputFormat.format(date)


        return res
    }

}






