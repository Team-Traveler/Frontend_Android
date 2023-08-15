//데이터 넘어가고, 편집 완료 텍스트변경 but 리사이클러 뷰 그대로
package com.example.traveler



import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.traveler.databinding.FragmentMyTravelBinding

//서버 연결
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


class MyTravelFragment : Fragment(), MyAdapter.OnItemClickListener, MyAdapter2.OnItemClickListener {
    private var selectedPosition: Int = RecyclerView.NO_POSITION


    // 뷰 바인딩 객체를 저장할 멤버 변수
    private var _binding: FragmentMyTravelBinding? = null
    private val binding get() = _binding!!

    private val list = ArrayList<Contents>()
    val REQUEST_CODE = 100
    private val SECOND_ACTIVITY_REQUEST_CODE = 2
    private val DETAIL_ACTIVITY_REQUEST_CODE=3
    private var isEditMode = false


    val adapter = MyAdapter(list)
    val adapter2 = MyAdapter2(list)

    private var recyclerView1: RecyclerView? = null
    private var recyclerView2: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyTravelBinding.inflate(inflater, container, false)
        val view = binding.root
//
        recyclerView1 = binding.recyclerView
        recyclerView2 = binding.editedView

        // 플로팅 버튼 클릭시 에니메이션 동작 기능
        var fab = binding.fab
        fab.setOnClickListener{
// SecondActity 화면으로 이동하게 Intent 사용
            val myIntent = Intent(activity, MakeActivity::class.java)
            // startActivity(myIntent)
            startActivityForResult(myIntent, REQUEST_CODE)

        }


        //[찜한 여행]클릭 시, 이동
        binding.pickTravel.setOnClickListener {
            val intent=Intent(requireContext(),PickTravelActivity::class.java)
            startActivity(intent)
        }

        //[프로필 편집] 클릭
        binding.editProfile.setOnClickListener{
        //editProfile 로 이동
            val myIntent = Intent(activity, EditProfile::class.java)
            startActivityForResult(myIntent, SECOND_ACTIVITY_REQUEST_CODE)
        }


//[편집] 클릭
        binding.delete.setOnClickListener{
//item 크기 변경 및 x 표시 나타남
            isEditMode = true
            recyclerView1?.visibility= View.GONE//false 일땐 원래 목록
            recyclerView2?.visibility= View.VISIBLE

            val editButton = binding.delete
            val completeButton = binding.complete
            editButton.isVisible= !isEditMode
            completeButton.isVisible= isEditMode

            // 편집 모드로 전환할 때 어댑터 및 레이아웃 매니저 설정
            recyclerView2?.layoutManager= LinearLayoutManager(requireContext())
            recyclerView2?.adapter= adapter2
            adapter2.setOnItemClickListener(this)

        }

//[편집완료] 클릭
        binding.complete.setOnClickListener{
//원래 화면으로 돌아와야함
            isEditMode = false
            recyclerView2?.visibility= View.GONE//원래 화면 뜨기
            recyclerView1?.visibility= View.VISIBLE//

            val editButton = binding.delete
            val completeButton = binding.complete
            editButton.isVisible= !isEditMode
            completeButton.isVisible= isEditMode

            // 편집 완료 모드로 전환할 때 어댑터 및 레이아웃 매니저 설정
            recyclerView1?.layoutManager= LinearLayoutManager(requireContext())
            recyclerView1?.adapter= adapter
            adapter.setOnItemClickListener(this)

        }


//[편집] 클릭 전,
        val linearLayoutManager = LinearLayoutManager(requireContext())
        val editedLayoutManager = LinearLayoutManager(requireContext())

        if (isEditMode) {
            //[편집 ] 클릭 후, 화면에 [편집완료] - x 표시 나타남
            recyclerView2?.layoutManager= editedLayoutManager
            recyclerView2?.adapter= adapter2
            adapter2.setOnItemClickListener(this)


        } else {
            //[편집 완료]클릭 전, [편집] - 원래 목록 나타남
            recyclerView1?.layoutManager= linearLayoutManager
            recyclerView1?.adapter= adapter
            adapter.setOnItemClickListener(this)

        }




        return view
    }    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 뷰 바인딩 객체 해제
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // 결과 처리
            // 받아온 데이터를 처리하는 로직 작성

            //secondactivity에서 값 받아오기
            val name = data?.getStringExtra("course")
            val day = data?.getStringExtra("day")
            val date = data?.getStringExtra("date")

            //위치정보 저장해두고 -위경도 넘기기
            val location = data?.getStringExtra("location")

            //출발, 도착
            val start = data?.getStringExtra("start")
            val end = data?.getStringExtra("end")

            //로그 확인
            Log.d("start", "start 는 ${start}")
            Log.d("end", "emd 는 ${end}")

            Log.d("day", "day 는 ${day}")
            Log.d("date", "date 는 ${date}")


            list.add(Contents("$name", "$day", "$date","$location","$start","$end"))  //넘겨받은 값 db에 추가

            //추가사항 ~ data.json으로 값 넘겨주기 및 변환
            val trip = TripData(
                title = "test",
                destination = "test",
                start_date = "2023-07-31",
                end_date = "2023-08-02",
                write_status = 0
            )


            val gson = Gson()
            val jsonData = gson.toJson(trip)

            val url = "http://15.164.232.95:9000/travel"
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = RequestBody.create(mediaType, jsonData)

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()



            //여기까지


            Log.d("contents", list.toString())
            /*adapter?.notifyItemInserted((list.size -1))
             adapter2?.notifyItemInserted((list.size -1))*/
            adapter.notifyDataSetChanged() // 어댑터에게 데이터 변경을 알려 업데이트
            adapter2.notifyDataSetChanged() // adapter2도 마찬가지로 업데이트
        }
        else if (requestCode == SECOND_ACTIVITY_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {

            // SecondActivity에서 결과를 받아와서 처리(프로핇변경)
            val resultValue = data?.getStringExtra("name")
            binding.username.text= resultValue
            // SecondActivity에서 받아온 결과 처리

        }
        else if(requestCode == EDIT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // EditActivity에서 수정된 데이터를 받아온 경우 처리
            val modifiedName = data?.getStringExtra("selectedItemName")
            val modifiedDay = data?.getStringExtra("selectedItemDay")
            val modifiedLocation = data?.getStringExtra("selectedItemLocation")
            val modifiedStart = data?.getStringExtra("selectedItemStart")
            val modifiedEnd = data?.getStringExtra("selectedItemEnd")
            Log.d("contents2", "modifiedEnd는 $modifiedEnd")


            // 데이터 수정 후 RecyclerView 아이템 업데이트
            Log.d("contents2", list.toString())

            if (selectedPosition != RecyclerView.NO_POSITION) {
                val modifiedItem = Contents("$modifiedName", "$modifiedDay", "$modifiedDay","$modifiedLocation","$modifiedStart","$modifiedEnd")
                list[selectedPosition] = modifiedItem
                adapter.notifyItemChanged(selectedPosition)
            }

        }
    }


    override fun onItemClick(position: Int) {
        // Handle text click event here
        selectedPosition = position
        val selectedItem = adapter.getItem(position)
        //val selectedItem = list[position]

        // 버튼마다 다른 동작을 수행하도록 구분
        if (isEditMode) {
            // [편집모드]일 때 [상세보기] 버튼 클릭 시 동작
            //deleteItem(position)
        } else {
            // [일반모드]일 때 [상세보기] 버튼 클릭 시 동작
            showDetailActivity(selectedItem)
        }



    }

    // 상세보기 액티비티로 이동하는 함수
    private fun showDetailActivity(selectedItem: Contents) {
        //contents칼럼
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("selectedItemName", selectedItem.name)
        intent.putExtra("selectedItemDate", selectedItem.date)
        intent.putExtra("selectedItemDay", selectedItem.day)
        intent.putExtra("selectedItemLocation",selectedItem.location)

        intent.putExtra("selectedItemStart",selectedItem.start)
        intent.putExtra("selectedItemEnd",selectedItem.end)

       // startActivity(intent)

        // 클릭한 아이템 정보를 상세보기 페이지로 전달
        startActivityForResult(intent, DETAIL_ACTIVITY_REQUEST_CODE)

    }






}