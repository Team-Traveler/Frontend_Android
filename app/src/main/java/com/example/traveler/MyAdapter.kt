package com.example.traveler

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyAdapter(private var items: ArrayList<Contents>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textView.text= item.title

        item.start_date=checkFormat(item.start_date)
        item.end_date=checkFormat(item.end_date)


        //수정사항 (#박#일 추가)
        val day=setDay(item.start_date,item.end_date).toInt() //몇박몇일
        val res="${day}박 ${day+1}일"
        holder.binding.day.text = "$res"

        //수정사항 (startdate~enddate)  (##/## 으로 형변환 )
        val start_date:String=setDate(item.start_date)
        holder.binding.startDate.text = start_date
        Log.d("start_date","$start_date")

        val end_date:String=setDate(item.end_date)
        holder.binding.endDate.text =end_date
        Log.d("end_date","$end_date")



    }

    inner class ViewHolder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            //text 클릭 시, 이벤트 처리 코드 추가
            //클릭 이벤트 발새 시, onitemclicklistener 호출해
            //해당 아이템의 위치 전달 (상세보기 클릭 시 이벤트 )
            /*      binding.detail.setOnClickListener{
                      val position =adapterPosition
                      if (position != RecyclerView.NO_POSITION) {
                          itemClickListener?.onItemClick(position)


                      }
                  }*/

            // 클릭 이벤트 처리 부분에서 tid 값을 가져와서 Intent에 담아서 DetailActivity로 전환
            binding.detail.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // 해당 아이템의 tid 값 가져오기
                    val tid = getItem(position)?.tid
                    Log.d("myadapter tid","$tid")

                    if (tid != null) {
                        val intent = Intent(binding.root.context, DetailActivity::class.java)
                        intent.putExtra("tid",tid)
                        Log.d("myadapter 전달된값","$tid")

                        binding.root.context.startActivity(intent)
                    }
                }
            }




        }



    }

    override fun getItemCount(): Int = items.size


    fun getItem(position: Int): Contents? {
        return if (position in 0 until items.size) {
            items[position]
        } else {
            null
        }
    }

    // 아이템 리스트를 설정하고 화면을 업데이트하는 메서드
    // 데이터를 갱신하는 메서드
    fun updateData(newItems: List<Contents>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    // 외부에서 아이템 클릭 리스너 설정
    fun setOnItemClickListener(listener:  MyAdapter.OnItemClickListener) {
        itemClickListener = listener
    }

    // 아이템 클릭 리스너 인터페이스
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun checkFormat(input:String):String{
        // "yyyyMMdd" 형식인지 확인
        val isFormatyyyyMMdd = input.matches(Regex("\\d{8}"))

        if (isFormatyyyyMMdd) {
            val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val date: Date = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(input) ?: Date()
            return outputFormat.format(date)
        }

        return input
    }

    fun setDay(start:String,  end:String):String{



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



        Log.d("format","$start_date") //포맷 확인
        Log.d("format","$end_date") //포맷 확인




        val sec=(date_e.time- date_s.time)/1000
        val days=sec/(24*60*60)
        Log.d("days: 날짜!!", "$days 일 차이남!!")


        //날짜 차이
        var calcuDate = (date_e.time- date_s.time) / (60 * 60 * 24 * 1000)

        Log.d("test: 날짜!!", "$calcuDate 일 차이남!!")


        return calcuDate.toString()

    }

    fun setDate(date_before:String):String {

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



