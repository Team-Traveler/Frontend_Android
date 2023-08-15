package com.example.traveler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.traveler.databinding.FragmentSumGraphBinding
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class SumGraphFragment : Fragment() {
    private lateinit var binding: FragmentSumGraphBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSumGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 총 막대 그래프 설정
        setupHorizontalBarChart(binding.horizontalBarChart, 66700f)

        // 식비 막대 그래프 설정
        setupHorizontalBarChart(binding.horizontalBarChart2, 13600f)

        // 교통비 막대 그래프 설정
        setupHorizontalBarChart(binding.horizontalBarChart3, 12500f)

        // 관광 막대 그래프 설정
        setupHorizontalBarChart(binding.horizontalBarChart4, 6300f)

        // 쇼핑 막대 그래프 설정
        setupHorizontalBarChart(binding.horizontalBarChart5, 2800f)

        // 기타 막대 그래프 설정
        setupHorizontalBarChart(binding.horizontalBarChart6, 2900f)
    }

    private fun setupHorizontalBarChart(chart: HorizontalBarChart, value: Float) {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, value)) // x-index 0에 value 추가

        val dataSet = BarDataSet(entries, "")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList() // 컬러 리스트 설정

        val barData = BarData(dataSet)
        barData.barWidth = 0.5f // 바 너비 설정

        chart.data = barData
        chart.description.isEnabled = false // 설명 비활성화
        chart.legend.isEnabled = false // 범례 비활성화
        chart.axisLeft.isEnabled = false // 왼쪽 축 비활성화
        chart.axisRight.isEnabled = false // 오른쪽 축 비활성화
        chart.xAxis.isEnabled = false // x축 비활성화
        chart.setFitBars(true) // 바 차트의 너비를 맞춤
        chart.invalidate()
    }
}