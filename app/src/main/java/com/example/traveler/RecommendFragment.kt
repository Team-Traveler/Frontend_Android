package com.example.traveler

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.traveler.databinding.FragmentRecommendBinding

class RecommendFragment : Fragment() {
    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        val rootView = binding.root

        // 날짜 - 년도 옵션들
        val spnYear = binding.spTheme
        spnYear.adapter = ArrayAdapter.createFromResource(
            rootView.context,
            com.example.traveler.R.array.year,
            android.R.layout.simple_spinner_item
        )

        //날짜 - 월 옵션들
        var spn = binding.spMonth
        spn.adapter = ArrayAdapter.createFromResource(
            requireContext(),  // Use requireContext() here
            com.example.traveler.R.array.month,
            android.R.layout.simple_spinner_item
        )

        //날짜 - 일 옵션들
        spn = binding.spDate
        spn.adapter = ArrayAdapter.createFromResource(
            requireContext(),  // Use requireContext() here
            com.example.traveler.R.array.day,
            android.R.layout.simple_spinner_item
        )
        //누구와 부분 고르기 옵션들
        spn = binding.spMember
        spn.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            com.example.traveler.R.array.withWhom,
            android.R.layout.simple_spinner_item
        )

        //몇명
        spn = binding.spHowmany
        spn.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            com.example.traveler.R.array.howMany,
            android.R.layout.simple_spinner_item
        )

        //무엇을 부분 고르기 옵션들
        spn = binding.spWhat
        spn.adapter = ArrayAdapter.createFromResource(
            requireContext(),  // Use requireContext() here
            com.example.traveler.R.array.what,
            android.R.layout.simple_spinner_item
        )

        //여행강도 부분 고르기 옵션들
        spn = binding.spIntensityPlan
        spn.adapter = ArrayAdapter.createFromResource(
            requireContext(),  // Use requireContext() here
            com.example.traveler.R.array.intensity,
            android.R.layout.simple_spinner_item
        )

//        val toolbar = binding.toolbar
//        setSupportActionBar(toolbar)

        // Set up the toolbar
//        val toolbar = binding.toolbar
//        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
//        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnDone.setOnClickListener {
            val intent = Intent(requireContext(), MakeplanActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
