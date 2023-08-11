package com.example.traveler

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.traveler.databinding.ActivityMakeBinding
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Calendar
import java.util.Date

class MakeActivity : AppCompatActivity(), OnLocationSelectedListener  {
    private val REQUEST_CODE=100
    // 추가: 출발 날짜와 도착 날짜 변수
    private var departureDate: String = ""
    private var arrivalDate: String = ""

    private var selectedLocation: String = "" // 위치 정보를 저장할 변수
    private var selectedLatitude: Double = 0.0  // 위도정보를 저장할 변수
    private var selectedLongitude: Double=0.0 // 경도 정보를 저장할 변수


    val binding by lazy{ActivityMakeBinding.inflate(layoutInflater)}
    override fun onLocationSelected(locationName: String, latitude: Double, longitude: Double) {
        // 위치 정보를 받아서 처리하는 로직
        selectedLatitude=latitude
        selectedLongitude=longitude
        selectedLocation = locationName
        binding.placetext.setText(locationName)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //출발 날짜 클릭 시,

        // 날짜 - 년도 옵션들
        val spnYear = binding.spnYear
        spnYear.adapter= ArrayAdapter.createFromResource(
            this,
            R.array.year2,
            android.R.layout.simple_spinner_item
        )

        // 날짜 - 월 옵션들
        val spnMonth = binding.spnMonth
        spnMonth.adapter= ArrayAdapter.createFromResource(
            this,
            R.array.month2,
            android.R.layout.simple_spinner_item
        )

        // 날짜 - 일 옵션들
        val spnDay = binding.spnDay
        spnDay.adapter= ArrayAdapter.createFromResource(
            this,
            R.array.day2,
            android.R.layout.simple_spinner_item
        )



        //도착 날짜 클릭 시, (수정필요)


        // 날짜 - 년도 옵션들
        val spnYear2 = binding.spnYear2
        spnYear2.adapter= ArrayAdapter.createFromResource(
            this,
            R.array.year2,
            android.R.layout.simple_spinner_item
        )

        // 날짜 - 월 옵션들
        val spnMonth2 = binding.spnMonth2
        spnMonth2.adapter= ArrayAdapter.createFromResource(
            this,
            R.array.month2,
            android.R.layout.simple_spinner_item
        )

        // 날짜 - 일 옵션들
        val spnDay2 = binding.spnDay2
        spnDay2.adapter= ArrayAdapter.createFromResource(
            this,
            R.array.day2,
            android.R.layout.simple_spinner_item
        )


        //[여행 계획 만들기] 버튼 클릭
        binding.button.setOnClickListener{

//여행 상세 내용 -> mainactivity recyclerview에 전달
            sendMemo()

        }

//[어디] 여행지 입력 시, 지도 팝업 나타나기
        binding.placetext.setOnFocusChangeListener{_, hasFocus->
//여기 지도창 띄움
            if (hasFocus) {
                val mapPopupFragment = MapPopupFragment()
                mapPopupFragment.setOnLocationSelectedListener(this) // 위치 선택 리스너 설정
                mapPopupFragment.show(supportFragmentManager, "MapPopup")
            }
        }

    }



    //출발 날짜 넘겨주기
    private fun updateDepartureDate(): String {
        val selectedYear = binding.spnYear.selectedItem.toString()
        val selectedMonth = binding.spnMonth.selectedItem.toString()
        val selectedDay = binding.spnDay.selectedItem.toString()
        departureDate = "$selectedYear$selectedMonth$selectedDay"
        Log.d("departuredate","$departureDate")
        return departureDate
    }
    //도착 날짜 넘겨주기
    private fun updateArrivalDate() :String{
        val selectedYear = binding.spnYear2.selectedItem.toString()
        val selectedMonth = binding.spnMonth2.selectedItem.toString()
        val selectedDay = binding.spnDay2.selectedItem.toString()
        arrivalDate = "$selectedYear$selectedMonth$selectedDay"
        return arrivalDate
    }



    fun sendMemo() {
        //일정명, 몇박, 기간 받아오기 ~> db 전달 ???? (메인엑티비티에서 처리)
        val course: String =binding.coursetext.getText().toString()
        val day=setDay().toInt() //몇박몇일
        val res="${day}박 ${day+1}일"

        //기간
        val start=updateDepartureDate()    //출발
        val startFormatDate =newFormat(start)  //새로운 포맷으로
        val end=updateArrivalDate()      //도착
        val endFormatDate=newFormat(end)        //새로운 포맷으로
        val date="$startFormatDate~$endFormatDate"    // 2019/01/01~2019/01/02
        //01/01~/01/02 화면에 바로 뜨도록 값 넘겨줌


        //이전 페이지에 전달
        val resulIntent=Intent()

        // 위치 정보도 Intent에 추가 detailact으로 전달
        //다른 포맷도 전달 (출발, 도착 각각 저장)
        resulIntent.putExtra("start", start) //출발
        resulIntent.putExtra("end", end) //도착


        resulIntent.putExtra("course", course) //일정명
        resulIntent.putExtra("day",res )       //몇박몇일
        resulIntent.putExtra("date",date )       //2019/01/01~2019/01/02
        resulIntent.putExtra("location", selectedLocation)  //위치
        resulIntent.putExtra("latitude",selectedLatitude)
        resulIntent.putExtra("longtitude",selectedLongitude)


        setResult(Activity.RESULT_OK,resulIntent)
        finish()

    }

    //몇박몇일 계산
    fun setDay():String{
        val start=updateDepartureDate()
        val end=updateArrivalDate()

        Log.d("format","$start") //포맷 확인
        Log.d("format","$end") //포맷 확인

        val dateFormat = SimpleDateFormat("yyyyMMdd")

        val startDate=dateFormat.parse(start)
        Log.d("startDate","$startDate") //포맷 확인
        val endDate=dateFormat.parse(end)
        Log.d("endDate","$endDate") //포맷 확인

        val sec=(endDate.time- startDate.time)/1000
        val days=sec/(24*60*60)
        Log.d("days: 날짜!!", "$days 일 차이남!!")


        //날짜 차이
        var calcuDate = (endDate.time- startDate.time) / (60 * 60 * 24 * 1000)

        Log.d("test: 날짜!!", "$calcuDate 일 차이남!!")


        return calcuDate.toString()

    }

    fun newFormat( input :String):String{
        //포맷 변환
        // 1) 현재 포맷
        // 2) 새로운 포맷으로 파싱
        val dt = SimpleDateFormat("yyyyMMdd")
        val n_date= dt.parse(input)
        val newstring = SimpleDateFormat("MM/dd").format(n_date)

        Log.d("format test",newstring)

        return newstring

        //
    }



}









