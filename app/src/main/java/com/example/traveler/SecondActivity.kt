package com.example.traveler

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.traveler.databinding.ActivitySecondBinding
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Calendar

class SecondActivity : AppCompatActivity() {
    private val REQUEST_CODE=100

    val binding by lazy {ActivitySecondBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //출발 날짜 클릭 시,
        val dep=binding.departday
        dep.setOnClickListener {

            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                val month="%02d".format(month)
                val day="%02d".format(day)

                binding.departday.text = "${year}${month}${day}"

            }

            DatePickerDialog(this,
                data,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()

        }

        //도착 날짜 클릭 시,
        val arr=binding.arriveday
        arr.setOnClickListener {
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                val month="%02d".format(month)
                val day="%02d".format(day)
                binding.arriveday.text = "${year}${month}${day}"

            }
            DatePickerDialog(this,
                data,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()

        }


        //[여행 계획 만들기] 버튼 클릭
        binding.button.setOnClickListener {

            //여행 상세 내용 -> mainactivity recyclerview에 전달
            sendMemo()

        }


    }


    fun sendMemo() {
        //일정명, 몇박, 기간 받아오기 ~> db 전달 ???? (메인엑티비티에서 처리)
        val course: String =binding.coursetext.getText().toString()
        val day=setDay().toInt() //몇박몇일
        val res="${day}박 ${day+1}일"




        val start=binding.departday.getText().toString()    //출발

        val startFormatDate =newFormat(start)
        val end=binding.arriveday.getText().toString()      //도착
        val endFormatDate=newFormat(end)
        val date="$startFormatDate~$endFormatDate"    // 2019/01/01~2019/01/02

        //이전 페이지에 전달
       // val intent = Intent(this,MainActivity::class.java)
        val intent=intent
        val resulIntent=Intent()
        resulIntent.putExtra("course", course) //일정명
        resulIntent.putExtra("day",res )       //몇박몇일
        resulIntent.putExtra("date",date )       //2019/01/01~2019/01/02

        Log.d("sendmemo","course 는 ${course}")
        Log.d("sendmemo","몇박몇일  ${res}")
        Log.d("sendmemo","일정기간은  ${date}")



//        val resultIntent=Intent()
        setResult(RESULT_OK,resulIntent)
        finish()
        //getResultText.launch(intent)
        //setResult(RESULT_OK,intent)
        //finish()
        //startActivity(intent)
    }



    //몇박몇일 계산
    fun setDay():String{
        val start=binding.departday.getText().toString()
        val end=binding.arriveday.getText().toString()

        Log.d("format","$start") //포맷 확인
        Log.d("format","$end") //포맷 확인

        val dateFormat = SimpleDateFormat("yyyyMMdd")

        val startDate=dateFormat.parse(start)
        Log.d("startDate","$startDate") //포맷 확인
        val endDate=dateFormat.parse(end)
        Log.d("endDate","$endDate") //포맷 확인

        val sec=(endDate.time - startDate.time)/1000
        val days=sec/(24*60*60)
        Log.d("days: 날짜!!", "$days 일 차이남!!")


        //날짜 차이
        var calcuDate = (endDate.time - startDate.time) / (60 * 60 * 24 * 1000)

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









