package com.example.traveler.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveler.adapter.CostAdapter
import com.example.traveler.adapter.DayAdapter
import com.example.traveler.databinding.FragmentDayBinding
import com.example.traveler.model.CostDto
import com.example.traveler.model.DayDto

class DayFragment : Fragment() {
    private lateinit var binding : FragmentDayBinding
    private lateinit var dayAdapter: DayAdapter
    private val dayDataList = ArrayList<DayDto>()
    private val costDataList = ArrayList<CostDto>()
    private var costAdapter: CostAdapter = CostAdapter(costDataList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDayBinding.inflate(inflater,container,false)

        binding.addCostBtn.setOnClickListener {
//            val transaction = (activity as MainActivity).supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.frameLayout, AddFragment())
//            transaction.disallowAddToBackStack()
//            transaction.commit()
            // 데이터 전달하기

            val fragment = AddFragment()

            val bundle = Bundle().apply {
                putString("selectedItemContent", "YourContent")
                putString("selectedItemDate", "YourDate")
                putString("selectedItemCost", "YourCost")
            }
            fragment.arguments = bundle
        }
        dayAdapter = DayAdapter(dayDataList)
        binding.outerRvDay.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.outerRvDay.adapter = dayAdapter

        val costDataList1 = listOf(
            CostDto(0, "식비", "점심 야끼카레", "11,000"),
            CostDto(1, "교통비", "지하철", "2,300"),
            CostDto(2, "쇼핑", "휴족시간", "6,000")
        )
        val dayData1 = DayDto("1일차", "3월 10일", costDataList1)
        dayDataList.add(dayData1)

        val costDataList2 = listOf(
            CostDto(0, "식비", "점심 야끼카레", "11,000"),
            CostDto(1, "교통비", "지하철", "2,300"),
            CostDto(2, "쇼핑", "휴족시간", "6,000")
        )
        val dayData2 = DayDto("2일차", "3월 11일", costDataList2)
        dayDataList.add(dayData2)

        dayAdapter.notifyDataSetChanged()

        binding.editBtnCost.setOnClickListener {
            if (binding.editBtnCost.text == "편집") {
                binding.editBtnCost.text = "편집 완료"
            }
            else {
                binding.editBtnCost.text = "편집"
            }
            costAdapter.updateButtonState()
        }

        return binding.root
    }
}
