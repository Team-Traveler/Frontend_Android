package com.example.traveler.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveler.adapter.DayAdapter
import com.example.traveler.databinding.FragmentDayBinding
import com.example.traveler.model.CostDto
import com.example.traveler.model.DayDto
import com.example.traveler.viewmodel.CostViewModel

class DayFragment : Fragment() {
    private lateinit var binding: FragmentDayBinding
    private lateinit var dayAdapter: DayAdapter
    private val dayDataList = ArrayList<DayDto>()
    private val costViewModel: CostViewModel by viewModels()
    private val costDataList = ArrayList<CostDto>()
    private lateinit var content: TextView
    private lateinit var selectedValueTextView: TextView
    private lateinit var categoryBtn: ImageButton
    private lateinit var cost: TextView
    private lateinit var editBtnCost: TextView
    private lateinit var deleteImgBtn2: ImageButton // deleteImgBtn2 추가
    val REQUEST_CODE_ADD = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDayBinding.inflate(inflater, container, false)
//        binding.addCostBtn.setOnClickListener {
//            val myIntent = Intent(activity, AddActivity::class.java)
//            startActivityForResult(myIntent, REQUEST_CODE_ADD)
//        }

        dayAdapter = DayAdapter(dayDataList, costViewModel)
        binding.outerRvDay.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.outerRvDay.adapter = dayAdapter

//        val costDataList1 = listOf(
//            CostDto(0, "식비", "점심 야끼카레", "11,000"),
//            CostDto(1, "교통비", "지하철", "2,300"),
//            CostDto(2, "쇼핑", "휴족시간", "6,000")
//        )
        val dayData1 = DayDto("1일차", "8월 10일")
        dayDataList.add(dayData1)

//        val costDataList2 = listOf(
//            CostDto(0, "식비", "점심 야끼카레", "11,000"),
//            CostDto(1, "교통비", "지하철", "2,300"),
//            CostDto(2, "쇼핑", "휴족시간", "6,000")
//        )
        val dayData2 = DayDto("2일차", "8월 11일")
        dayDataList.add(dayData2)

        dayAdapter.notifyDataSetChanged()

        return binding.root
    }

    //    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.addCostBtn.setOnClickListener {
//            val intent = Intent(activity, AddActivity::class.java)
//            startActivityForResult(intent, REQUEST_CODE_ADD_ACTIVITY)
//        }
//    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_ADD && resultCode == Activity.RESULT_OK) {
//            val content = data?.getStringExtra("content")
//            val cost = data?.getStringExtra("cost")
//        }
//    }


}
