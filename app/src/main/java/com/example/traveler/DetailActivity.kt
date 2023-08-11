package com.example.traveler

import android.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveler.databinding.ActivityDetailBinding
import net.daum.mf.map.api.MapView


// 상수 정의
const val EDIT_REQUEST_CODE= 1
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var parentAdapter: ParentAdapter
    private val parentDataList = ArrayList<ParentData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*       // 커스텀 액션 바 레이아웃 인플레이트
               supportActionBar?.apply {
                   displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
                   setCustomView(R.layout.custom_action_bar)
                   setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 활성화
               }*/


        //1.여행 명, 날짜 표기
        //binding.travelName=Contents
        // Intent로 전달받은 데이터 확인
        val selectedItemName =intent.getStringExtra("selectedItemName")
        val selectedItemDate =intent.getStringExtra("selectedItemDate")
        val selectedItemDay =intent.getStringExtra("selectedItemDay")
        val selectedItemStart =intent.getStringExtra("selectedItemStart") //추가
        val selectedItemEnd =intent.getStringExtra("selectedItemEnd")

        val selectedItemLocation =intent.getStringExtra("selectedItemLocation") //추가


        if (selectedItemName != null|| selectedItemDate != null||selectedItemDay!= null)
        {
            // 데이터를 사용하여 원하는 작업 수행
            // 예를 들면, TextView에 데이터를 설정하는 등의 작업을 할 수 있습니다.
            val textView = binding.travelName  //여행명
            textView.text= selectedItemName
            val textView2 = binding.travelDay  //여행기간 ( 몇박)
            textView2.text= selectedItemDay
            //여행기간  (수정) ##~##
            val textView3=binding.travelDate1
            val textView4=binding.travelDate2
            textView3.text=selectedItemStart
            textView4.text=selectedItemEnd

            // 여행 장소 받아와서 다음페이지(편집)에 넘겨야함

            /* val textView3 = binding.travelDate
             textView3.text = selectedItemDate*/

        }

        //2.카카오맵 연결
        //카카오지도 추가

        val mapView = MapView(this)
        binding.mapView.addView(mapView)




        //3. 부모 리사이클러뷰 설정
        parentAdapter = ParentAdapter(parentDataList)
        binding.recyclerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter= parentAdapter


        // 부모 데이터 리스트에 데이터 추가  --> 데이터 변경 필요
        val childDataList1 =listOf(        //여행 경로 뜨는 부분
            ChildData(1, "부산"),
            ChildData(2, "호텔"),
            ChildData(3, "운동")
        )
        val parentData1 = ParentData("1일차", "2/2~2/3", childDataList1)
        parentDataList.add(parentData1)

        // 추가적인 데이터를 생성하여 부모 데이터 리스트에 추가
        val childDataList2=listOf<ChildData>()  //값이 비었을떄?

        /*       val childDataList2 = listOf(
                   ChildData(4, "해변"),
                   ChildData(5, "산책"),
                   ChildData(6, "맛집")
               )*/
        val parentData2 = ParentData("2일차", "2/4~2/5", childDataList2)
        parentDataList.add(parentData2)


        val childDataList3 =listOf(
            ChildData(4, "해변"),
            ChildData(5, "산책"),
            ChildData(6, "맛집")
        )
        val parentData3 = ParentData("2일차", "2/4~2/5", childDataList3)
        parentDataList.add(parentData3)
        // 데이터가 변경되었음을 어댑터에 알려줍니다.
        parentAdapter.notifyDataSetChanged()


        //[이미지버튼]클릭 시, 수정 페이지로 이동
        //이전 페이지에서 정보 가져올 것
        binding.imageButton.setOnClickListener{
            // ImageButton이 클릭되었을 때 실행되는 코드
            val intent = Intent(this, EditActivity::class.java)

            // 여행 제목, 날짜, 기간을 Intent에 추가 ~>edit에 넘겨줘
            intent.putExtra("selectedItemName", binding.travelName.text.toString())  //코스명
            //intent.putExtra("selectedItemDate", binding.travelDate.text.toString())
            intent.putExtra("selectedItemLocation",selectedItemLocation) //장소
            intent.putExtra("selectedItemStart", binding.travelDate1.text.toString()) //기간 (##~##)
            intent.putExtra("selectedItemEnd", binding.travelDate2.text.toString()) //기간 (##~##)
            intent.putExtra("selectedItemDay", binding.travelDay.text.toString()) //몇박며칠

            // EditActivity로 전환
            startActivityForResult(intent,EDIT_REQUEST_CODE)
        }







    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode ==EDIT_REQUEST_CODE&& resultCode == Activity.RESULT_OK) {
            val modifiedSelectedItemName = data?.getStringExtra("selectedItemName")
            val modifiedSelectedItemDay = data?.getStringExtra("selectedItemDay")
            val modifiedSelectedItemStart = data?.getStringExtra("selectedItemStart")
            val modifiedSelectedItemEnd = data?.getStringExtra("selectedItemEnd")

            // 수정된 데이터를 사용하여 화면 업데이트(상세보기 화면 수정)
            binding.travelName.text= modifiedSelectedItemName
            binding.travelDay.text= modifiedSelectedItemDay
            binding.travelDate1.text= modifiedSelectedItemStart
            binding.travelDate2.text= modifiedSelectedItemEnd





        }


    }




    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 뒤로가기 버튼 동작 설정
        return true
    }



}