package com.example.traveler


import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveler.databinding.ActivityAddPlaceBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import xParentAdapter
import java.io.IOException


class AddPlaceActivity : AppCompatActivity() {
    private val MAX_DISPLAYED_KEYWORDS = 5
    private val keywordList = ArrayList<String>()
    private var onLocationSelectedListener: OnLocationSelectedListener? = null
    private var selectedMarker: MapPOIItem? = null // 선택된 마커 정보를 저장할 변수
    private lateinit var mapView: MapView  // MapView 인스턴스 선언

    val binding by lazy{ ActivityAddPlaceBinding.inflate(layoutInflater)}
    private lateinit var adapter: xParentAdapter // 본인의 어댑터 클래스로 변경
    private val childDataList = ArrayList<ChildData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val date = intent.getStringExtra("date")
        val day = intent.getStringExtra("day")

        //날짜 설정
        binding.date.text = date
        binding.date2.text = day


        //리사이클러뷰 불러오기 (이전페이지에서 넘겨받아야함)
        // 리사이클러뷰와 어댑터 초기화
        adapter = xParentAdapter(childDataList)
        adapter.setOnItemClickListener(object : xParentAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                childDataList.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, childDataList.size)
                Toast.makeText(this@AddPlaceActivity, "Item at position $position deleted.", Toast.LENGTH_SHORT).show()
            }
        })

        binding.xrecyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.xrecyclerview.adapter = adapter

        //  리스트에 데이터 추가  --> 데이터 변경 필요
        val childDataList1 = listOf(        //여행 경로 뜨는 부분
            ChildData(1, "부산"),
            ChildData(2, "호텔"),
            ChildData(3, "운동")
        )
        childDataList.addAll(childDataList1)
        adapter.notifyDataSetChanged()


        //searchview 검색 이벤트
        val searchview=binding.searchView

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    // 검색 버튼을 눌렀을 때 처리할 작업

                    //검색어 밑에 저장
                    keywordList.add(0, query) // 검색한 내용 추가함

                    // 여기서 검색 결과를 childDataList에 추가
                    childDataList.add(ChildData(childDataList.size + 1, query))
                    adapter.notifyDataSetChanged()
                    // 검색어로 입력된 주소에 대한 위치 정보 얻기
                    val location = getLocationFromAddress(this@AddPlaceActivity,query)
                    if (location != null) {
                        // 검색한 주소의 위치 정보를 기반으로 마커 추가하는 함수 호출
                        addMarkerForAddress(mapView, query)
                    }

                    updateKeywordList()
                    searchview.setQuery("", false) // 검색창 지움
                    searchview.clearFocus() // 검색어를 입력하세요 같은 초기 화면 뜸



                    binding.button.text="완료"
                    binding.button.setOnClickListener {
                        finish()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색어가 변경될 때 처리할 작업
                return false
            }
        })



        //2.카카오맵 연결

        mapView = MapView(this)
        binding.mapView.addView(mapView)
        //수원 위치로 지도 나타나게
        val mapPoint= MapPoint.mapPointWithGeoCoord(37.28730797086605, 127.01192716921177)


        val marker=MapPOIItem()


        marker.mapPoint= mapPoint
        marker.markerType= MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType= MapPOIItem.MarkerType.RedPin
        marker.isShowCalloutBalloonOnTouch= true // 이 부분 추가
        mapView.setMapCenterPoint(mapPoint, true)
        mapView.setZoomLevel(1, true)

        mapView.addPOIItem(marker)
        mapView.invalidate()


        binding.button.setOnClickListener {
            val query = binding.searchView.query.toString()
            if (!query.isNullOrBlank()) {
                // 검색한 내용을 childDataList에 추가
                childDataList.add(ChildData(childDataList.size + 1, query))
                adapter.notifyDataSetChanged()
                updateKeywordList()
                searchview.setQuery("", false) // 검색창 지움
                searchview.clearFocus() // 검색어를 입력하세요 같은 초기 화면 뜸
                binding.button.text="완료"

                //좌표 매핑되어야함
                //검색어로 입력된거 ->위치정보 받기 (새로입력된값)

                // 여기서 address, latitude, longitude 값을 원하는 곳에 전달하거나 활용할 수 있습니다.
                //검색어로 입력된거 ->위치정보 받기
                // 검색어로 입력된 주소에 대한 위치 정보 얻기
                val location = getLocationFromAddress(this, query)
                if (location != null) {
                    // 검색한 주소의 위치 정보를 기반으로 마커 추가하는 함수 호출
                    addMarkerForAddress(mapView, query)
                }


                //이전 페이지 이동
                binding.button.setOnClickListener {
                    finish()
                }
            }
        }

        //좌표매핑 (저장되어있는 위치정보 집어넣기 )
        // 여러 개의 좌표를 리스트에 저장 (나중에 값 불러올것)
        //기존 값들 찍어두기
        //새로운 값 찍기
        val locations = listOf(
            Pair(37.28730797086605, 127.01192716921177), // 수원 화성 위치
            //Pair(37.12345, 126.54321), // 다른 위치 1
            //Pair(37.67890, 128.76543) // 다른 위치 2
            // ... 추가적인 위치
        )


    }

    //내가 검색한 내용 보이게 하는 함수
    fun updateKeywordList() {
        // 최대 표시할 검색어 개수를 제한하여 리스트에 추가

        val recent=binding.recent
        recent.text = keywordList.take(MAX_DISPLAYED_KEYWORDS).joinToString("\n")
        recent.visibility = View.VISIBLE
    }


    private fun addMarkerForAddress(mapView: MapView, address: String) {
        val location = getLocationFromAddress(mapView.context, address)
        if (location != null) {
            val latitude = location.first
            val longitude = location.second
            val mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)


            mapView.setMapCenterPoint(mapPoint, true)
            mapView.setZoomLevel(1, true)
            //addMarker(mapView,latitude, longitude,"test")

            //마커생성
            val marker = MapPOIItem()
            marker.itemName= address
            marker.mapPoint= mapPoint
            marker.markerType= MapPOIItem.MarkerType.BluePin
            marker.selectedMarkerType= MapPOIItem.MarkerType.RedPin
            marker.isShowCalloutBalloonOnTouch= true // 이 부분 추가

            mapView.addPOIItem(marker)
            selectedMarker = marker // selectedMarker 설정 추가
            // 선택된 마커의 위치 정보를 이용해서 어떤 작업을 수행하고자 할 때 selectedMarker를 통해 해당 정보를 활용
            onLocationSelectedListener?.onLocationSelected(address, latitude, longitude)
        }

    }



    // EditText에 입력된 주소 문자열을 받아서 해당 주소의 위치 정보(위도, 경도)를 반환하는 함수
    fun getLocationFromAddress(context: Context, inputAddress: String): Pair<Double, Double>? {
        val geocoder = Geocoder(context)
        Log.d("위치","$inputAddress")
        try {
            val addresses: List<Address> = geocoder.getFromLocationName(inputAddress, 1) as List<Address>
            if (addresses.isNotEmpty()) {
                val latitude = addresses[0].latitude
                val longitude = addresses[0].longitude
                Log.d("위치","$latitude")

                return Pair(latitude, longitude)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }


}




