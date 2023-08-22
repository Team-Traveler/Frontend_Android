package com.example.traveler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveler.adapter.ChoiceAdapter
import com.example.traveler.databinding.FragmentChoiceBinding
import com.example.traveler.model.TravelDto

class ChoiceFragment : Fragment() {
    private lateinit var binding: FragmentChoiceBinding
    private val travelList = mutableListOf<TravelDto>() // 여행 데이터 리스트

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 여행 데이터 추가 (임의의 데이터 예시)
        travelList.add(TravelDto("부산여행", "2박 3일", "8/10 ~ 8/12"))
        travelList.add(TravelDto("부산여행", "2박 3일", "8/10 ~ 8/12"))
        // ... 더 많은 여행 데이터 추가

        val adapter = ChoiceAdapter(travelList)
        binding.rvChoice.adapter = adapter
        binding.rvChoice.layoutManager = LinearLayoutManager(requireContext())
    }
}
