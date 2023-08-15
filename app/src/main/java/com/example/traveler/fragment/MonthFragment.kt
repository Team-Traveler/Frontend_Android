package com.example.traveler.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.traveler.MainActivity
import com.example.traveler.R
import com.example.traveler.adapter.CalendarPagerFragmentStateAdapter

class MonthFragment : Fragment() {

    private val TAG = javaClass.simpleName
    lateinit var mContext: Context

    lateinit var calendarViewPager: ViewPager2

    companion object {
        var instance: MonthFragment? = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
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
}
