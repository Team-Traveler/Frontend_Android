package com.example.traveler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveler.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var parentAdapter: ParentAdapter
    private val parentDataList = ArrayList<ParentData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 부모 리사이클러뷰 설정
        parentAdapter = ParentAdapter(parentDataList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = parentAdapter


        // 부모 데이터 리스트에 데이터 추가
        val childDataList1 = listOf(
            ChildData(1, "부산"),
            ChildData(2, "호텔"),
            ChildData(3, "운동")
        )
        val parentData1 = ParentData("1일차", "2/2~2/3", childDataList1)
        parentDataList.add(parentData1)

        // 추가적인 데이터를 생성하여 부모 데이터 리스트에 추가
        val childDataList2 = listOf(
            ChildData(4, "해변"),
            ChildData(5, "산책"),
            ChildData(6, "맛집")
        )
        val parentData2 = ParentData("2일차", "2/4~2/5", childDataList2)
        parentDataList.add(parentData2)

        // 데이터가 변경되었음을 어댑터에 알려줍니다.
        parentAdapter.notifyDataSetChanged()
    }
}