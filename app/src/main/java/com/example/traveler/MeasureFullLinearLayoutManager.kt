package com.example.traveler

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MeasureFullLinearLayoutManager(context: Context, private val recyclerView: RecyclerView) : LinearLayoutManager(context) {

    private var itemSize: Int = RecyclerView.LayoutParams.WRAP_CONTENT
    private var screenWidth: Int = 0
    private var isEditMode: Boolean = false

    fun setItemSize(itemSize: Int) {
        this.itemSize = itemSize
    }


    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        super.onMeasure(recycler, state, widthSpec, heightSpec)

        if (screenWidth == 0) {
            screenWidth = recyclerView?.width ?: 0
        }

        if (isEditMode) {
            // 편집 모드가 on일 때 아이템 크기와 위치 조정
            val itemSize = screenWidth * 3 / 4
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (child != null) {
                    val params = child.layoutParams as RecyclerView.LayoutParams
                    params.width = itemSize
                    child.layoutParams = params
                }
            }

            // 아이템을 오른쪽으로 치우침
            val horizontalPadding = (screenWidth - itemSize) / 2
            recyclerView?.setPadding(horizontalPadding, 0, horizontalPadding, 0)
            recyclerView?.clipToPadding = false
        } else {
            // 편집 모드가 off일 때 기본 상태 그대로 유지
            recyclerView?.setPadding(0, 0, 0, 0)
            recyclerView?.clipToPadding = true

            val defaultItemSize = screenWidth // 기본 크기를 화면 가로 사이즈로 설정
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (child != null) {
                    val params = child.layoutParams as RecyclerView.LayoutParams
                    params.width = defaultItemSize
                    child.layoutParams = params
                }
            }
        }
    }
}