package com.example.traveler.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import com.example.traveler.R
import com.example.traveler.adapter.DayAdapter
import com.example.traveler.model.DayDto
import java.util.Date

class CostDialog(context: Context, myInterface: DayAdapter.DayViewHolder) : Dialog(context){
    // 액티비티에서 인터페이스를 받아옴
    private var costDialogInterface: DayAdapter.DayViewHolder = myInterface

    private var selectedCategory: String? = null

    private val dayDataList = ArrayList<DayDto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dialog2)

        var okButton2 : Button = findViewById(R.id.addCostOk)
//        var cancelButton2 : Button = findViewById(R.id.cancelButton2)
        var outerEditView2 : EditText = findViewById(R.id.editContent)
//        var outerEditView3 : EditText = findViewById(R.id.outerEditView3)
        var outerEditView4 : EditText = findViewById(R.id.editCost)
        val spinner: Spinner = findViewById(R.id.spinner1)

        spinner.adapter = ArrayAdapter.createFromResource(context, R.array.spinner_day, android.R.layout.simple_spinner_item)

        val foodImgBtn: ImageButton = findViewById(R.id.foodImgBtn)
        val trafficImgBtn: ImageButton = findViewById(R.id.trafficImgBtn)
        val sightseeingImgBtn: ImageButton = findViewById(R.id.sightseeingImgBtn)
        val stayImgBtn: ImageButton = findViewById(R.id.stayImgBtn)
        val shoppingImgBtn: ImageButton = findViewById(R.id.shoppingImgBtn)
        val flightImgBtn: ImageButton = findViewById(R.id.flightImgBtn)
        val etcImgBtn: ImageButton = findViewById(R.id.etcImgBtn)

        foodImgBtn.setOnClickListener {
            setSelectedCategory("식비")
            Toast.makeText(context, "식비 버튼 선택", Toast.LENGTH_SHORT).show()
        }
        trafficImgBtn.setOnClickListener {
            setSelectedCategory("교통")
            Toast.makeText(context, "교통 버튼 선택", Toast.LENGTH_SHORT).show()
        }
        sightseeingImgBtn.setOnClickListener {
            setSelectedCategory("관광")
            Toast.makeText(context, "관광 버튼 선택", Toast.LENGTH_SHORT).show()
        }
        stayImgBtn.setOnClickListener {
            setSelectedCategory("숙소")
            Toast.makeText(context, "숙소 버튼 선택", Toast.LENGTH_SHORT).show()
        }
        shoppingImgBtn.setOnClickListener {
            setSelectedCategory("쇼핑")
            Toast.makeText(context, "쇼핑 버튼 선택", Toast.LENGTH_SHORT).show()
        }
        flightImgBtn.setOnClickListener {
            setSelectedCategory("항공")
            Toast.makeText(context, "항공 버튼 선택", Toast.LENGTH_SHORT).show()
        }
        etcImgBtn.setOnClickListener {
            setSelectedCategory("기타")
            Toast.makeText(context, "기타 버튼 선택", Toast.LENGTH_SHORT).show()
        }


        okButton2.setOnClickListener {
            val content = outerEditView2.text.toString()
//            val category = outerEditView3.text.toString()
            val cost = outerEditView4.text.toString()

            if (content.isEmpty()) {
                Toast.makeText(context, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (selectedCategory == null) {
                Toast.makeText(context, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if (cost.isEmpty()) {
                Toast.makeText(context, "비용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                costDialogInterface.onOkButtonClicked(content, selectedCategory!!, cost)
                dismiss()
            }
        }
    }
    private fun setSelectedCategory(category: String) {
        selectedCategory = category

    }
}