package com.example.traveler

import android.R
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.traveler.databinding.ActivityEditBinding
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Calendar

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding // binding 변수 선언
    private var departureDate: String = ""
    private var arrivalDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //detail페이지 에서 정보 전달받아서, 입력된 상태로
        val intent =intent
        val selectedItemName = intent.getStringExtra("selectedItemName") //코스명
        //val selectedItemDate = intent.getStringExtra("selectedItemDate")
        val selectedItemLocation = intent.getStringExtra("selectedItemLocation")    //위치
        val selectedItemStart = intent.getStringExtra("selectedItemStart")          //시작
        val selectedItemEnd = intent.getStringExtra("selectedItemEnd")              //끝




        Log.d("selectedItemStart","$selectedItemStart")
        Log.d("selectedItemEnd","$selectedItemEnd")

        //받아온 값들을 넣어주기
        binding.coursetext.setText(selectedItemName)
        binding.placetext.setText(selectedItemLocation)

        // 스피너 초기화 및 값 설정
        initializeSpinners(selectedItemStart)  //출발
        initializeSpinners2(selectedItemEnd)    //도착



        //[수정]버튼 클릭 시, 데이터 변경 --앞페이지로 전달(detailact)
        binding.editbtn.setOnClickListener{
                // 수정된 데이터를 인텐트에 담아서 결과로 전달
            val modifiedDataIntent = Intent().apply{
                putExtra("selectedItemName", binding.coursetext.text.toString())
                putExtra("selectedItemLocation", binding.placetext.text.toString())
                putExtra("selectedItemStart", getSelectedDateFromSpinners(binding.spnYear, binding.spnMonth, binding.spnDay))
                putExtra("selectedItemEnd", getSelectedDateFromSpinners(binding.spnYear2, binding.spnMonth2, binding.spnDay2))

                val day=setDay().toInt() //몇박몇일
                val res="${day}박 ${day+1}일"
                putExtra("selectedItemDay",res )       //몇박몇일



            }

            setResult(Activity.RESULT_OK, modifiedDataIntent)
            finish() // 액티비티 종료


        }



    }

    //출발 날짜 넘겨주기
    private fun updateDepartureDate(): String {
        val selectedYear = binding.spnYear.selectedItem.toString()
        val selectedMonth = binding.spnMonth.selectedItem.toString()
        val selectedDay = binding.spnDay.selectedItem.toString()
        departureDate = "$selectedYear$selectedMonth$selectedDay"
        Log.d("edit_dep","$departureDate")
        return departureDate
    }

    //도착 날짜 넘겨주기
    private fun updateArrivalDate() :String{
        val selectedYear = binding.spnYear2.selectedItem.toString()
        val selectedMonth = binding.spnMonth2.selectedItem.toString()
        val selectedDay = binding.spnDay2.selectedItem.toString()
        arrivalDate = "$selectedYear$selectedMonth$selectedDay"
        Log.d("edit_arr","$arrivalDate")

        return arrivalDate
    }

    //스피너 값 설정
    private fun initializeSpinners(selectedItemStart: String?) {
        val yearAdapter = ArrayAdapter.createFromResource(
            this,
            com.example.traveler.R.array.year2,
            android.R.layout.simple_spinner_item
        )
        val monthAdapter = ArrayAdapter.createFromResource(
            this,
            com.example.traveler.R.array.month2,
            android.R.layout.simple_spinner_item
        )
        val dayAdapter = ArrayAdapter.createFromResource(
            this,
            com.example.traveler.R.array.day2,
            android.R.layout.simple_spinner_item
        )

        binding.spnYear.adapter= yearAdapter
        binding.spnMonth.adapter= monthAdapter
        binding.spnDay.adapter= dayAdapter

        if (!selectedItemStart.isNullOrEmpty()) {
            setSpinnerToSelectedDate(selectedItemStart)
        }
    }
    private fun initializeSpinners2(selectedItemEnd: String?) {
        val yearAdapter = ArrayAdapter.createFromResource(
            this,
            com.example.traveler.R.array.year2,
            android.R.layout.simple_spinner_item
        )
        val monthAdapter = ArrayAdapter.createFromResource(
            this,
            com.example.traveler.R.array.month2,
            android.R.layout.simple_spinner_item
        )
        val dayAdapter = ArrayAdapter.createFromResource(
            this,
            com.example.traveler.R.array.day2,
            android.R.layout.simple_spinner_item
        )

        binding.spnYear2.adapter= yearAdapter
        binding.spnMonth2.adapter= monthAdapter
        binding.spnDay2.adapter= dayAdapter

        if (!selectedItemEnd.isNullOrEmpty()) {
            setSpinnerToSelectedDate2(selectedItemEnd)
        }
    }


    private fun setSpinnerToSelectedDate(selectedItemStart: String) {
        val year = selectedItemStart.substring(0, 4).toInt()
        val month = selectedItemStart.substring(4, 6).toInt() - 1
        val day = selectedItemStart.substring(6, 8).toInt()

        Log.d("spinday","$day")

        // 년, 월, 일 값을 스피너에 설정
        binding.spnYear.setSelection(getYearIndex(year))
        binding.spnMonth.setSelection(month)
        binding.spnDay.setSelection(day -1) //일은 1부터 시작해서 -1
    }
    private fun setSpinnerToSelectedDate2(selectedItemEnd: String) {
        val year = selectedItemEnd.substring(0, 4).toInt()
        val month = selectedItemEnd.substring(4, 6).toInt() - 1
        val day = selectedItemEnd.substring(6, 8).toInt()


        // 년, 월, 일 값을 스피너에 설정
        binding.spnYear2.setSelection(getYearIndex(year))
        binding.spnMonth2.setSelection(month)
        binding.spnDay2.setSelection(day -1) //일은 1부터 시작해서 -1
    }

    private fun getYearIndex(year: Int): Int {
        // Spinner에서 해당 년도의 인덱스를 계산하는 로직
        // 이 예시에서는 R.array.year2의 값이 2000년부터 시작되는 것으로 가정했습니다.
        // 만약 R.array.year2에 2000년부터 연속된 년도가 없다면 다른 방식을 사용해야 합니다.
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        return year - currentYear
    }
    private fun getSelectedDateFromSpinners(yearSpinner: Spinner, monthSpinner: Spinner, daySpinner: Spinner): String {
        val year = yearSpinner.selectedItem.toString()
        val month = (monthSpinner.selectedItemPosition+ 1).toString().padStart(2, '0') // 월은 0부터 시작하므로 +1, 1자리 월 앞에 0 추가
        val day = daySpinner.selectedItem.toString().padStart(2, '0') // 1자리 일 앞에 0 추가
        return "$year$month$day"
    }


    //몇박몇일 계산
    fun setDay():String{
        val start=updateDepartureDate()
        val end=updateArrivalDate()


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

        Log.d("edit test: 날짜!!", "$calcuDate 일 차이남!!")


        return calcuDate.toString()

    }

}