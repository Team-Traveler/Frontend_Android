package com.example.traveler.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.example.traveler.ChoiceFragment
import com.example.traveler.R
import com.example.traveler.databinding.FragmentNoteBinding
import com.example.traveler.adapter.ViewPagerAdapter

class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 1. View Binding 설정
        binding = FragmentNoteBinding.inflate(inflater, container, false)

        // 2. View Pager의 FragmentStateAdapter 설정
        binding.vpCheck.adapter = activity?.let { ViewPagerAdapter(it) }

        // 3. View Pager의 Orientation 설정
        binding.vpCheck.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }
}
