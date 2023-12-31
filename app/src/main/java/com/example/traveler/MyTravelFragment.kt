// detailact  intent 로 전달 (contents -> travel item으로 수정)

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

val client = OkHttpClient()

class MyTravelFragment : Fragment(), MyAdapter.OnItemClickListener, MyAdapter2.OnItemClickListener {
    private var selectedPosition: Int = RecyclerView.NO_POSITION


    // 뷰 바인딩 객체를 저장할 멤버 변수
    private var _binding: FragmentMyTravelBinding? = null
    private val binding get() = _binding!!

    private val list = ArrayList<Contents>()
    //private val list = ArrayList<TripData>()


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
        recyclerView2 = binding.editedView // [편집] 버튼 눌렀을때

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


        // 앱 시작 시에 전체 데이터 조회 및 UI 업데이트
        fetchAndDisplayData()


        return view
    }    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 뷰 바인딩 객체 해제
    }


    //전체 데이터 목록 조회
    private fun fetchAndDisplayData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = Request.Builder()
                    .url("http://15.164.232.95:9000/users/my_travels")
                    .addHeader("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMSIsImV4cCI6MTY5MjY3ODEwMX0.LIZXQcGqTuSrgOr7wDJznhsmVkitbhMNitx8bdLkV6cQE5_7fic9wpskhHg9UK5ZcUfZ1LRk9Cl5wAfZ4itjlw")
                    .get()
                    .build()

                val response = client.newCall(request).execute()
                Log.d("TAG1", response.toString())



                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val apiResponse = Gson().fromJson(responseBody, ApiResponse::class.java)

                    val travelList = apiResponse.result ?: emptyList()
                    Log.d("CLIENT_success", travelList.toString())

                    // UI 업데이트 작업은 Main 스레드에서 수행
                    launch(Dispatchers.Main) {
                        // tid 값을 순차적으로 부여하여 Contents 객체를 생성
                        /*           val contentsList = travelList.mapIndexed { index, item ->
                                       Contents(
                                           tid = index + 1, // 예시로 index + 1 값을 사용
                                           title = item.title,
                                           destination = item.destination,
                                           start_date = item.start_date,
                                           end_date = item.end_date,
                                           write_status = item.write_status
                                       )
                                   }*/

                        val contentsList = travelList.map { item ->
                            Contents(
                                tid = item.tid, // 서버 응답에서 실제 tid 사용
                                title = item.title,
                                destination = item.destination,
                                start_date = item.start_date,
                                end_date = item.end_date,
                                write_status = item.write_status
                            )
                        }

                        // 어댑터에 데이터를 설정하고 업데이트합니다.
                        adapter.updateData(contentsList)
                        adapter2.updateData(contentsList)

                    }
                } else {
                    Log.e("Network", "Request failed with response code: ${response.code}")
                }
            } catch (e: IOException) {
                e.printStackTrace()

            }
        }}


    //새로운 여행 추가
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


            list.add(Contents("$name", "$location","$start","$end" ))  //넘겨받은 값 db에 추가

            val dateFormat = SimpleDateFormat("yyyyMMdd")  //string원래 형태
            val outputDateFormat = SimpleDateFormat("yyyy-MM-dd")
            //출발
            val date_s: Date = dateFormat.parse(start)  //문자열 ->객체 변환
            val start_date: String = outputDateFormat.format(date_s)
            //도착
            val date_e: Date = dateFormat.parse(end)  //문자열 ->객체 변환
            val end_date: String = outputDateFormat.format(date_e)


            //코루틴으로 네트워크 작업 실행
            //추가사항 ~ data.json으로 값 넘겨주기 및 변환
            val trip = Contents(
                title = "$name",
                destination = "$location",
                start_date = "$start_date",
                end_date = "$end_date",
                write_status = 0

            )


            val gson = Gson()
            val jsonData = gson.toJson(trip)   //json파일 불러오기
            Log.d("TAG", jsonData.toString())



            val client = OkHttpClient()
            val mediaType = "application/json".toMediaType()
            val requestBody = jsonData.toRequestBody(mediaType)

            val request = Request.Builder()
                .url("http://15.164.232.95:9000/travel")
                .post(requestBody)
                .addHeader("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMSIsImV4cCI6MTY5MjY3ODEwMX0.LIZXQcGqTuSrgOr7wDJznhsmVkitbhMNitx8bdLkV6cQE5_7fic9wpskhHg9UK5ZcUfZ1LRk9Cl5wAfZ4itjlw")
                .addHeader("Content-Type", "application/json")
                .build()



            // 코루틴으로 백그라운드에서 네트워크 요청 실행
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = client.newCall(request).execute()
                    if (response.isSuccessful) {
                        // 성공 처리
                        // responseBody를 가지고 원하는 작업을 수행합니다.
                        // 예를 들어, 서버에서 반환한 메시지를 로그로 출력하거나 UI에 표시할 수 있습니다.
                        val responseBody = response.body?.string()

                        Log.d("Network", "Response Body: $responseBody")



                        // UI 업데이트가 필요한 경우, Main 스레드에서 처리합니다.
                        withContext(Dispatchers.Main) {
                            // UI 업데이트 작업 수행
                            // 예: Toast 메시지 띄우기 또는 화면 갱신
                            Log.d("contents", list.toString())
                            /*adapter?.notifyItemInserted((list.size -1))
                             adapter2?.notifyItemInserted((list.size -1))*/
                            adapter.notifyDataSetChanged() // 어댑터에게 데이터 변경을 알려 업데이트
                            adapter2.notifyDataSetChanged() // adapter2도 마찬가지로 업데이트


                        }
                    } else {
                        // 실패 처리
                        Log.e("Network", "Request failed with response code: ${response.code}")

                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }


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
                val modifiedItem = Contents("$modifiedName",  "$modifiedDay","$modifiedStart","$modifiedEnd",)
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
//            deleteTravelItem(position)


        } else {
            // [일반모드]일 때 [상세보기] 버튼 클릭 시 동작
            if (selectedItem != null) {
                showDetailActivity(selectedItem)
            }
        }



    }




    // 상세보기 액티비티로 이동하는 함수
    private fun showDetailActivity(selectedItem: Contents) {
        //contents칼럼
        val intent = Intent(activity, DetailActivity::class.java)
        /* intent.putExtra("selectedItemName", selectedItem.title)
         intent.putExtra("selectedItemDate", selectedItem.date)
         intent.putExtra("selectedItemDay", selectedItem.day)
         intent.putExtra("selectedItemLocation",selectedItem.destination)

         intent.putExtra("selectedItemStart",selectedItem.start_date)
         intent.putExtra("selectedItemEnd",selectedItem.end_date)
 */
        // intent.putExtra("tripId", selectedItem.tid) // tid 값 전달
        // startActivity(intent)

        // 클릭한 아이템 정보를 상세보기 페이지로 전달
        startActivityForResult(intent, DETAIL_ACTIVITY_REQUEST_CODE)

    }}





