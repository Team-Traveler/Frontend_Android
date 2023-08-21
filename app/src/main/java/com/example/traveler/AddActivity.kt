package com.example.traveler

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.traveler.adapter.DayAdapter
import com.example.traveler.adapter.InnerAdapter
import com.example.traveler.databinding.ActivityAddBinding
import com.example.traveler.databinding.ActivityEditBinding
import com.example.traveler.databinding.ActivityMakeBinding
import com.example.traveler.databinding.FragmentAddBinding

class AddActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
//    val binding by lazy{ ActivityAddBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner = binding.spinner1
        spinner.adapter= ArrayAdapter.createFromResource(
            this,
            R.array.spinner_day,
            android.R.layout.simple_spinner_item
        )

        // 비용 추가 버튼 누르면
        binding.addCostOk.setOnClickListener{
            val content: String = binding.editContent.text.toString()
            val cost: String = binding.editCost.text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra("content", content)
            resultIntent.putExtra("cost", cost)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }



        var sData = resources.getStringArray(R.array.spinner_day)
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sData)
        binding.spinner1.adapter = adapter

    }
}