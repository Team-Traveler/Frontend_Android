package com.example.traveler.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.traveler.MainActivity
import com.example.traveler.NaviActivity
import com.example.traveler.R
import com.example.traveler.adapter.CalendarPagerFragmentStateAdapter
import com.example.traveler.dialog.CostDialog

class MonthFragment : Fragment() {

    private val TAG = javaClass.simpleName
    lateinit var mContext: Context

    lateinit var calendarViewPager: ViewPager2

    companion object {
        var instance: MonthFragment? = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NaviActivity) {
            mContext = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_month, container, false)
        calendarViewPager = view.findViewById(R.id.calendarViewPager)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView() {
        val calendarPagerFragmentStateAdapter = CalendarPagerFragmentStateAdapter(requireActivity())
        calendarViewPager.adapter = calendarPagerFragmentStateAdapter
        calendarViewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        calendarPagerFragmentStateAdapter.apply {
            calendarViewPager.setCurrentItem(this.monthFragmentPosition, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

    fun updateData(data: Bundle) {
        val category = data.getString("category")
        val cost = data.getString("cost")

        // 이 부분에서 해당 날짜에 대한 TextView를 찾아서 업데이트합니다.
        val textViewId = R.id.item_calendar_expense_text // 텍스트뷰 아이디를 적절하게 수정
        val textView = view?.findViewById<TextView>(textViewId)
        textView?.text = "$category - $cost" // 예시로 카테고리와 비용을 표시
    }
}
