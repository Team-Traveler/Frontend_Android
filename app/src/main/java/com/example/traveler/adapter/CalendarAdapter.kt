package com.example.traveler.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.FurangCalendar
import com.example.traveler.R
import com.example.traveler.model.DayDto
import java.text.SimpleDateFormat
import java.util.*

// 높이를 구하는데 필요한 LinearLayout과 FurangCalender를 사용할 때 필요한 date를 받는다.
class CalendarAdapter(val context: Context, val calendarLayout: LinearLayout, val date: Date) :
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
        var itemCalendarExpenseText: TextView = itemView!!.findViewById(R.id.item_calendar_expense_text)

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
            if (position < firstDateIndex || position > lastDateIndex) {
                itemCalendarDateText.setTextAppearance(R.style.LightColorTextViewStyle)
                itemCalendarDotView.background = null
            }

            // 날짜의 연월일 형식을 생성하여 지출 정보를 가져올 key로 사용합니다.
            val selectedDate = "${SimpleDateFormat("yyyy-MM", Locale.KOREA).format(date)}-${data.toString().padStart(2, '0')}"

//            // 이 부분에서 해당 날짜의 지출 정보를 가져와서 표시합니다.
//            val matchingDayData = getDayDataForDate(selectedDate)
//            if (matchingDayData != null) {
//                // 해당 날짜에 지출 정보가 있는 경우, 지출 내역을 표시하는 처리를 수행합니다.
//                // 예시로 TextView에 표시하는 부분입니다.
//                var expenseText = ""
//                for (costData in matchingDayData.costDataList) {
//                    expenseText += "${costData.category} - ${costData.content} (${costData.cost})\n"
//                }
//                itemCalendarDotView.visibility = View.VISIBLE
//                itemCalendarDateText.text = data.toString()
//                itemCalendarExpenseText.text = expenseText
//            } else {
//                // 해당 날짜에 지출 정보가 없는 경우, dot만 표시합니다.
//                itemCalendarDotView.visibility = View.GONE
//                itemCalendarDateText.text = data.toString()
//                itemCalendarExpenseText.text = ""
//            }
        }

        private fun getDayDataForDate(date: String): DayDto? {
            // 날짜 형식과 일치하는 DayDto를 찾아서 반환하는 함수를 작성합니다.
            return dayDataList.find { it.date == date }
        }

    }
}