package com.example.traveler

import android.content.Context
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.traveler.databinding.MapPopupLayoutBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

import java.io.IOException



import android.location.Address
import android.location.Geocoder



//해당 위치 클릭하면 인터페이스를 통해 위치 정보 전달
interface OnLocationSelectedListener {
    fun onLocationSelected(locationName: String, latitude: Double, longitude: Double)
}


//위치 선택하는 팝업 프래그먼트
class MapPopupFragment : DialogFragment() {

    private var selectedMarker: MapPOIItem? = null // 선택된 마커 정보를 저장할 변수


    private var _binding: MapPopupLayoutBinding? = null
    private val binding get() = _binding!!
    private var onLocationSelectedListener: OnLocationSelectedListener? = null


    fun setOnLocationSelectedListener(listener: OnLocationSelectedListener) {
        onLocationSelectedListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MapPopupLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.let { binding ->
// 여기에서 카카오 지도를 띄우는 로직을 구현
            val mapView = MapView(requireContext())
            binding.popupMapView.addView(mapView)

            mapView.setPOIItemEventListener(object : MapView.POIItemEventListener {
                override fun onPOIItemSelected(mapView: MapView?, marker: MapPOIItem?) {
                    //마커 클릭 이벤트 처리
                }

                override fun onCalloutBalloonOfPOIItemTouched(
                    mapView: MapView?,
                    marker: MapPOIItem?
                ) {
                    // 아무 동작 안함
                }

                override fun onCalloutBalloonOfPOIItemTouched(
                    mapView: MapView?,
                    marker: MapPOIItem?,
                    buttonType: MapPOIItem.CalloutBalloonButtonType?
                ) {
                    // 아무 동작 안함
                }

                override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
                }
            })

            // 검색 버튼 클릭 시 호출되는 함수
            binding.searchButton.setOnClickListener {
//검색어로 입력된거 ->위치정보 받기
                val address = binding.searchEditText.text.toString()
                val location = context?.let { it1 -> getLocationFromAddress(it1, address) }
                if (location != null) {
                    // 이제 얻은 위도와 경도를 활용해서 지도에 마커 등을 표시할 수 있습니다.
                    addMarkerForAddress(mapView, address)

                }

            }


// 완료 버튼 클릭 시, 팝업창 닫히고 text 받아옴
            binding.completebtn.setOnClickListener {

                selectedMarker?.let { marker ->

// 여기서 address, latitude, longitude 값을 원하는 곳에 전달하거나 활용할 수 있습니다.
                    val address = marker.itemName
                    val location = getLocationFromAddress(requireContext(), address)
                    if (location != null) {
                        val latitude = location.first
                        val longitude = location.second

                        onLocationSelectedListener?.onLocationSelected(address, latitude, longitude)
                        dismiss()
                    }
                }


            }


        }

    }

    private fun addMarkerForAddress(mapView: MapView, address: String) {
        val location = getLocationFromAddress(requireContext(), address)
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}










