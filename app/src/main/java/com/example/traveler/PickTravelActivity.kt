package com.example.traveler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PickTravelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_travel)
         lateinit var adapter: PickAdapter


        //리사이클러 뷰 초기화
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        //데이터 임의 저장
        val pickDataList: MutableList<PickData> = mutableListOf(
            PickData("Trip to Paris", "#Travel #France"),
            PickData("Beach Getaway", "#Travel #Relaxation"),
            PickData("Mountain Adventure", "#Travel #Hiking")
        )


        adapter = PickAdapter(pickDataList)
        adapter.setOnItemClickListener(object : PickAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Handle item click here
                // This method will be called when an item is clicked
                // You can put your desired behavior here
                pickDataList.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, pickDataList.size)
            }
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
    }
