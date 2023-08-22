package com.example.traveler.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.FurangCalendar
import com.example.traveler.R
import com.example.traveler.model.DayDto
import com.example.traveler.viewmodel.CostViewModel
import java.text.SimpleDateFormat
import java.util.*

// 높이를 구하는데 필요한 LinearLayout과 FurangCalender를 사용할 때 필요한 date를 받는다.
class CalendarAdapter(val context: Context, val calendarLayout: LinearLayout, val date: Date
) :
    RecyclerView.Adapter<CalendarAdapter.CalendarItemHolder>() {

    private val TAG = javaClass.simpleName
    var dataList: ArrayList<Int> = arrayListOf()
    private val dayDataList = ArrayList<DayDto>()

    // FurangCalendar을 이용하여 날짜 리스트 세팅
    var furangCalendar: FurangCalendar = FurangCalendar(date)
    init {
        furangCalendar.initBaseCalendar()
        dataList = furangCalendar.dateList
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onBindViewHolder(holder: CalendarItemHolder, position: Int) {

        // list_item_calendar 높이 지정
        val h = calendarLayout.height / 6
        holder.itemView.layoutParams.height = h

        holder?.bind(dataList[position], position, context)
        if (itemClick != null) {
            holder?.itemView?.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarItemHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.list_item_calendar, parent, false)
        return CalendarItemHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    inner class CalendarItemHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var itemCalendarDateText: TextView = itemView!!.findViewById(R.id.item_calendar_date_text)
        var itemCalendarDotView: View = itemView!!.findViewById(R.id.item_calendar_dot_view)

        fun bind(data: Int, position: Int, context: Context) {
//            Log.d(TAG, "${furangCalendar.prevTail}, ${furangCalendar.nextHead}")
            val firstDateIndex = furangCalendar.prevTail
            val lastDateIndex = dataList.size - furangCalendar.nextHead - 1
            itemCalendarDateText.setText(data.toString())

            // 오늘 날짜 처리
            var dateString: String = SimpleDateFormat("dd", Locale.KOREA).format(date)
            var dateInt = dateString.toInt()
            if (dataList[position] == dateInt) {
                itemCalendarDateText.setTypeface(itemCalendarDateText.typeface, Typeface.BOLD)
            }

            // 현재 월의 1일 이전, 현재 월의 마지막일 이후 값의 텍스트를 회색처리
//            if (position < firstDateIndex || position > lastDateIndex) {
//                itemCalendarDateText.setTextAppearance(R.style.LightColorTextViewStyle)
//                itemCalendarDotView.background = null
//            }
            if (position < firstDateIndex || position > lastDateIndex) {
                itemCalendarDateText.setTextColor(ContextCompat.getColor(context, R.color.light_background))
                itemCalendarDotView.background = null
            } else {
                itemCalendarDateText.setTextColor(ContextCompat.getColor(context, R.color.night_background))
            }
        }
    }
}