package com.example.traveler.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.traveler.SumFragment
import com.example.traveler.fragment.CheckFragment
import com.example.traveler.fragment.DayFragment
import com.example.traveler.fragment.MonthFragment

class ViewPagerAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private var fragments: ArrayList<Fragment> = arrayListOf(CheckFragment(), DayFragment(), MonthFragment(), SumFragment())

    // FragmentStateAdapter 상속 시 무조건 override 해야하는 fun
    override fun getItemCount(): Int {
        return fragments.size
    }

    // FragmentStateAdapter 상속 시 무조건 override 해야하는 fun (View Pager의 position에 해당하는 fragment return)
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}