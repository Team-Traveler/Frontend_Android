package com.example.traveler

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveler.databinding.ActivityDetailBinding
import net.daum.mf.map.api.MapView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


// 상수 정의
const val EDIT_REQUEST_CODE= 1
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var parentAdapter: ParentAdapter
    private val parentDataList = ArrayList<ParentData>()
    private lateinit var mapView: MapView  // MapView 인스턴스 선언
    private val client = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Intent로 전달받은 tid 값 가져오기
        val tid = intent.getIntExtra("tid", -1)
        if (tid != -1) {
            // 서버에서 해당 tid의 여행 데이터를 가져와 화면에 표시하는 작업 수행
            //1.여행 명, 날짜 표기
            //binding.travelName=Contents
            // Intent로 전달받은 데이터 확인
            Log.d("detail tid 전달","$tid")
            fetchTravelData(tid)

        } else {
            // tid가 전달되지 않은 경우에 대한 처리
            Log.d("detail tid 전달","전달안됨")

        }






        //2.카카오맵 연결
        //카카오지도 추가

        mapView = MapView(this)
        binding.mapView.addView(mapView)
///        val mapPoint= MapPoint.mapPointWithGeoCoord(37.28730797086605, 127.01192716921177)


        //[이미지버튼]클릭 시, 수정 페이지로 이동
        //이전 페이지에서 정보 가져올 것
        binding.imageButton.setOnClickListener{
            // ImageButton이 클릭되었을 때 실행되는 코드
            val intent = Intent(this, EditActivity::class.java)

            // 여행 제목, 날짜, 기간을 Intent에 추가 ~>edit에 넘겨줘
            intent.putExtra("selectedItemName", binding.travelName.text.toString())  //코스명
            //intent.putExtra("selectedItemDate", binding.travelDate.text.toString())
            // intent.putExtra("selectedItemLocation",selectedItemLocation) //장소
            intent.putExtra("selectedItemStart", binding.travelDate1.text.toString()) //기간 (##~##)
            intent.putExtra("selectedItemEnd", binding.travelDate2.text.toString()) //기간 (##~##)
            intent.putExtra("selectedItemDay", binding.travelDay.text.toString()) //몇박며칠

            // EditActivity로 전환
            startActivityForResult(intent,EDIT_REQUEST_CODE)
        }



    }





    private fun fetchTravelData(tid: Int) {
        val request = Request.Builder()
            .url("http://15.164.232.95:9000/travel/$tid") // 실제 API URL로 대체
            .addHeader("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMSIsImV4cCI6MTY5MjY3ODEwMX0.LIZXQcGqTuSrgOr7wDJznhsmVkitbhMNitx8bdLkV6cQE5_7fic9wpskhHg9UK5ZcUfZ1LRk9Cl5wAfZ4itjlw")
            .get() // GET 요청을 명시
            .build()

        Log.d("detail tid","$tid")

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.d("detail body", "살퍄")


            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()

                Log.d("detail body", responseBody.toString())

                if (response.isSuccessful && responseBody != null) {
                    try {
                        val jsonObject = JSONObject(responseBody)
                        val isSuccess = jsonObject.getBoolean("isSuccess")
                        val resultObject = jsonObject.getJSONObject("result")




                        if (isSuccess) {
                            val travelTitle = resultObject.getString("title")
                            val startDate = resultObject.getString("start_date")
                            val endDate = resultObject.getString("end_date")

                            //추가
                            // 수행할 작업: 여행 기간에 맞게 아이템을 생성하고 부모 데이터 리스트에 추가
                            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                            val startDateObj: Date = dateFormat.parse(startDate)
                            val endDateObj: Date = dateFormat.parse(endDate)
                            val coursesArray = resultObject.getJSONArray("courses")



                            val dateDifference = (endDateObj.time - startDateObj.time) / (24 * 60 * 60 * 1000) // 일 수 차이 계산

                            // 부모 데이터 리스트를 초기화
                            parentDataList.clear()

                            // 아이템 생성 및 추가
                            //추가
                            for (i in 0 until coursesArray.length()) {
                                val course = coursesArray.getJSONObject(i)
                                val spot1 = course.optJSONObject("spot1")
                                val spot2 = course.optJSONObject("spot2")
                                val spot3 = course.optJSONObject("spot3")

                                val childDataList = ArrayList<ChildData>()

                                spot1?.let {
                                    val title = it.getString("title")
                                    val latitude = it.getDouble("latitude")
                                    val longitude = it.getDouble("longitude")
                                    val childData = ChildData(i.toLong(), title, latitude, longitude)
                                    childDataList.add(childData)
                                }

                                spot2?.let {
                                    val title = it.getString("title")
                                    val latitude = it.getDouble("latitude")
                                    val longitude = it.getDouble("longitude")
                                    val childData = ChildData(i.toLong(), title, latitude, longitude)
                                    childDataList.add(childData)
                                }

                                spot3?.let {
                                    val title = it.getString("title")
                                    val latitude = it.getDouble("latitude")
                                    val longitude = it.getDouble("longitude")
                                    val childData = ChildData(i.toLong(), title, latitude, longitude)
                                    childDataList.add(childData)
                                }

                                val parentData = ParentData("${i + 1}일차", "$startDate ~ $endDate", childDataList)
                                parentDataList.add(parentData)
                            }
                            Log.d("detail body2", parentDataList.toString())
                            Log.d("course 갯수","${coursesArray.length()}")

                            runOnUiThread {
                                // 부모 어댑터 초기화 및 아이템 추가 작업
                                parentAdapter = ParentAdapter(parentDataList)
                                binding.recyclerView.layoutManager =
                                    LinearLayoutManager(this@DetailActivity, LinearLayoutManager.VERTICAL, false)
                                binding.recyclerView.adapter = parentAdapter

                                ///

                                parentAdapter.notifyDataSetChanged()
                                binding.travelName.text = travelTitle
                                val day = setDay(startDate, endDate).toInt()
                                val res = "${day}박 ${day + 1}일"
                                binding.travelDay.text = res
                                binding.travelDate1.text = setDate(startDate)
                                binding.travelDate2.text = setDate(endDate)

                                binding.root.invalidate()
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
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