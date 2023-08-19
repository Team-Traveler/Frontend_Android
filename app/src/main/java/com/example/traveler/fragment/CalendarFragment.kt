package com.example.traveler.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.MainActivity
import com.example.traveler.NaviActivity
import com.example.traveler.R
import com.example.traveler.model.DayDto
import com.example.traveler.adapter.CalendarAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarFragment(index: Int) : Fragment() {

    private val TAG = javaClass.simpleName
    lateinit var mContext: Context
    lateinit var mActivity: NaviActivity

    var pageIndex = index
    lateinit var currentDate: Date

    lateinit var calendar_year_month_text: TextView
    lateinit var calendar_layout: LinearLayout
    lateinit var calendar_view: RecyclerView
    lateinit var calendarAdapter: CalendarAdapter
    private val dayDataList = ArrayList<DayDto>()

    companion object {
        var instance: CalendarFragment? = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NaviActivity) {
            mContext = context
            mActivity = activity as NaviActivity
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
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        initView(view)
        initCalendar()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun initView(view: View) {
        pageIndex -= (Int.MAX_VALUE / 2)
        Log.e(TAG, "Calender Index: $pageIndex")
        calendar_year_month_text = view.findViewById(R.id.calendar_year_month_text)
        calendar_layout = view.findViewById(R.id.calendar_layout)
        calendar_view = view.findViewById(R.id.calendar_view)
        val date = Calendar.getInstance().run {
            add(Calendar.MONTH, pageIndex)
            time
        }
        currentDate = date
        Log.e(TAG, "$date")
        var datetime: String = SimpleDateFormat(
            mContext.getString(R.string.calendar_year_month_format),
            Locale.KOREA
        ).format(date.time)
        calendar_year_month_text.setText(datetime)
    }
    fun initCalendar() {
        // 각 월의 1일의 요일을 구해
        // 첫 주의 일요일~해당 요일 전까지는 ""으로
        // 말일까지 해당 날짜
        // 마지막 날짜 뒤로는 ""으로 처리하여
        // CalendarAdapter로 List를 넘김
        calendarAdapter = CalendarAdapter(mContext, calendar_layout, currentDate)
        calendar_view.adapter = calendarAdapter
        calendar_view.layoutManager = GridLayoutManager(mContext, 7, GridLayoutManager.VERTICAL, false)
        calendar_view.setHasFixedSize(true)
        calendarAdapter.itemClick = object :
        CalendarAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val selectedDate = calendarAdapter.dataList[position].toString()
                val selectedMonth = SimpleDateFormat("M", Locale.KOREA).format(currentDate)
                val selectedYear = SimpleDateFormat("yyyy", Locale.KOREA).format(currentDate)

                val firstDateIndex = calendarAdapter.dataList.indexOf(1)
                val lastDateIndex = calendarAdapter.dataList.lastIndexOf(calendarAdapter.furangCalendar.currentMaxDate)
                // 현재 월의 1일 이전, 현재 월의 마지막일 이후는 터치 disable
                if (position < firstDateIndex || position > lastDateIndex) {
                    return
                }
                val day = calendarAdapter.dataList[position].toString()
                val date = "${calendar_year_month_text.text}${day}일"
                Log.d(TAG, "$date")

                // 이 부분에서 해당 날짜의 지출 정보를 가져와서 표시합니다.
//                val matchingDayData = getDayDataForDate("$selectedYear/$selectedMonth/$selectedDate")
//                if (matchingDayData != null) {
//                    // 해당 날짜에 지출 정보가 있는 경우, 지출 내역을 표시하는 처리를 수행합니다.
//                    // 예시로 로그에 출력하는 부분입니다.
//                    for (costData in matchingDayData.costDataList) {
//                        Log.d(TAG, "Date: $selectedYear/$selectedMonth/$selectedDate, Category: ${costData.category}, Item: ${costData.content}, Amount: ${costData.cost}")
//                    }
//                }
            }
        }
        calendar_view.adapter = calendarAdapter
    }
    private fun getDayDataForDate(date: String): DayDto? {
        // 날짜 형식과 일치하는 DayDto를 찾아서 반환하는 함수를 작성합니다.
        return dayDataList.find { it.date == date }
    }
    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}