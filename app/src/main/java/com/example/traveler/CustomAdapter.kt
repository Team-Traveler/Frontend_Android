package com.example.traveler

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val context: Context, private val dataList: ArrayList<Contents>)
    : RecyclerView.Adapter<ItemViewHolder>(){
    //RecyclerView에 binding 해줄 Adapter를 연결시킨다.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler,parent, false)
        return ItemViewHolder(view)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position], context)
        //recycleView에 있는 각각의 아이템과 아이템의 위치
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val userName = itemView.findViewById<TextView>(R.id.textView)
    private val userPay = itemView.findViewById<TextView>(R.id.date)
    private val userAddress = itemView.findViewById<TextView>(R.id.day)

    //data -> resource (binding)
    fun bind(dataVo: Contents,context: Context) {



        //TextView 데이터 세팅 작업
        userName.text = dataVo.name
        userPay.text = dataVo.date
        userAddress.text = dataVo.day

        // itemView를 클릭시 이벤트 발생
/*
        itemView.setOnClickListener {
            println(dataVo.name + " " + dataVo.photo)

            //ProfileDetailActivity로 이동
            Intent(context, ProfileDetailActivity::class.java).apply {

                //짐을 챙긴다
                putExtra("data", dataVo)

                //짐을 보낸다
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) //새로운 액티비티를 추가하라
            }.run { context.startActivity(this) }
        }
*/
    }
    }