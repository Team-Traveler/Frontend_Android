package com.example.traveler.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveler.MainActivity
import com.example.traveler.R
import com.example.traveler.databinding.FragmentDayBinding


class DayFragment : Fragment() {
    private var binding : FragmentDayBinding? = null
    private lateinit var transaction: FragmentTransaction
//    private val dayViewModel: DayViewModel by viewModels()
//    private val adapter : DayAdapter by lazy { DayAdapter(dayViewModel) } // 어댑터 선언

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDayBinding.inflate(inflater,container,false)

        binding!!.rvDay.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL,false)
//        binding!!.rvDay.adapter = adapter

//        // 리스트 관찰하여 변경시 어댑터에 전달해줌
//        dayViewModel.readAllData.observe(viewLifecycleOwner, Observer {
//            adapter.setData(it)
//        })

        binding!!.costAddBtn.setOnClickListener {
            val transaction = (activity as MainActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, AddFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }

        return binding!!.root
    }
}