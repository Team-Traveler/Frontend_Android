package com.example.traveler.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import com.example.traveler.AddViewModel
import com.example.traveler.R
import com.example.traveler.databinding.FragmentAddBinding

class MonthFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var viewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity()).get(AddViewModel::class.java)

        // 데이터 전달받기
//        viewModel.selectedItemContent = arguments?.getString("selectedItemContent")
//        viewModel.selectedItemDate = arguments?.getString("selectedItemDate")
//        viewModel.selectedItemCost = arguments?.getString("selectedItemCost")
        //detail페이지 에서 정보 전달받아서, 입력된 상태로
        val data = arguments // 데이터는 arguments에서 받아옴
        val selectedItemContent = data?.getString("selectedItemContent")
        val selectedItemDate = data?.getString("selectedItemDate")
        val selectedItemCategory = data?.getString("selectedItemCategory")
        val selectedItemCost = data?.getString("selectedItemCost")

        //받아온 값들을 넣어주기
        binding.editContent.setText(selectedItemContent)
        binding.editCost.setText(selectedItemCost)

        // 스피너 초기화 및 값 설정
        initializeSpinners(selectedItemDate)  //출발

        // 다시 dayfragment로 이동
        binding.addCostOk.setOnClickListener {
            // 수정된 데이터를 인텐트에 담아서 결과로 전달
            val modifiedDataIntent = Intent().apply{
                putExtra("selectedItemContent", binding.editContent.text.toString())
                putExtra("selectedItemDate",  getSelectedDateFromSpinners(binding.spinner1))
//                putExtra("selectedItemCategory", getSelectedDateFromButtons(binding.foodImgBtn))
                putExtra("selectedItemCost", binding.editCost.text.toString())
            }
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }
    private fun initializeSpinners(selectedItemStart: String?) {
        // 날짜 선택
//        var sData = resources.getStringArray(R.array.spinner_day)
//        var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, sData)
//        binding.spinner1.adapter = adapter
        val dayAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_day,
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.spinner1.adapter= dayAdapter
    }
    private fun getSelectedDateFromSpinners(daySpinner: Spinner): String {
        val day = daySpinner.selectedItem.toString()
        return "$day"
    }
}