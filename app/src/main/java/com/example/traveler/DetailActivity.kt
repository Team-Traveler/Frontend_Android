package com.example.traveler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveler.databinding.ActivityDetailBinding
import net.daum.android.map.MapView
import java.security.Provider

/*import com.kakao.sdk.maps.MapView
import com.kakao.sdk.maps.MapView.Companion.Provider*/


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var parentAdapter: ParentAdapter
    private val parentDataList = ArrayList<ParentData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 커스텀 액션 바 레이아웃 인플레이트
        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.custom_back_bar)
            setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 활성화
        }


        //1.여행 명, 날짜 표기
        //binding.travelName=Contents
        // Intent로 전달받은 데이터 확인
        val selectedItemName = intent.getStringExtra("selectedItemName")
        val selectedItemDate = intent.getStringExtra("selectedItemDate")
        val selectedItemDay = intent.getStringExtra("selectedItemDay")


        if (selectedItemName != null|| selectedItemDate != null||selectedItemDay!= null)
        {
            // 데이터를 사용하여 원하는 작업 수행
            // 예를 들면, TextView에 데이터를 설정하는 등의 작업을 할 수 있습니다.
            val textView = binding.travelName  //여행명
            textView.text = selectedItemName
            val textView2 = binding.travelDay  //여행기간
            textView2.text = selectedItemDay
            val textView3 = binding.travelDate  //여행기간
            textView3.text = selectedItemDate

        }

        //2.카카오맵 연결
        val mapView = MapView(this)
        binding.clKakaoMapView.addView(mapView)

        //3. 부모 리사이클러뷰 설정
        parentAdapter = ParentAdapter(parentDataList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = parentAdapter


        // 부모 데이터 리스트에 데이터 추가  --> 데이터 변경 필요
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
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 뒤로가기 버튼 동작 설정
        return true
    }
}