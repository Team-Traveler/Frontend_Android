package com.example.traveler.fragment

import android.graphics.Color
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

    private fun setupTotalBarChart(chart: HorizontalBarChart, value: Float) {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, value)) // x-index 0에 value 추가

        val dataSet = BarDataSet(entries, "")
        dataSet.colors = listOf(ColorTemplate.MATERIAL_COLORS[0]) // 첫 번째 컬러만 사용

        val barData = BarData(dataSet)
        barData.barWidth = 1f // 바 너비를 1로 설정하여 꽉 채움

        chart.data = barData
        chart.description.isEnabled = false // 설명 비활성화
        chart.legend.isEnabled = false // 범례 비활성화
        chart.axisLeft.isEnabled = false // 왼쪽 축 비활성화
        chart.axisRight.isEnabled = false // 오른쪽 축 비활성화
        chart.xAxis.isEnabled = false // x축 비활성화
        chart.setFitBars(true) // 바 차트의 너비를 맞춤
        chart.invalidate()
    }

    private fun setupEmptyBarChart(chart: HorizontalBarChart) {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, 0f)) // x-index 0에 값 없음

        val dataSet = BarDataSet(entries, "")
        dataSet.colors = listOf(Color.TRANSPARENT) // 투명한 컬러 설정

        val barData = BarData(dataSet)
        barData.barWidth = 1f // 바 너비를 1로 설정하여 비워줌

        chart.data = barData
        chart.description.isEnabled = false // 설명 비활성화
        chart.legend.isEnabled = false // 범례 비활성화
        chart.axisLeft.isEnabled = false // 왼쪽 축 비활성화
        chart.axisRight.isEnabled = false // 오른쪽 축 비활성화
        chart.xAxis.isEnabled = false // x축 비활성화
        chart.setFitBars(true) // 바 차트의 너비를 맞춤
        chart.invalidate()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 총 막대 그래프 설정
        setupTotalBarChart(binding.horizontalBarChart, 66700f)

        // 식비 막대 그래프 설정
        setupEmptyBarChart(binding.horizontalBarChart2)

        // 교통비 막대 그래프 설정
        setupEmptyBarChart(binding.horizontalBarChart3)

        // 관광 막대 그래프 설정
        setupEmptyBarChart(binding.horizontalBarChart4)

        // 쇼핑 막대 그래프 설정
        setupEmptyBarChart(binding.horizontalBarChart5)

        // 기타 막대 그래프 설정
        setupEmptyBarChart(binding.horizontalBarChart6)
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