package com.example.traveler.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.traveler.databinding.FragmentAddBinding

class AddFragment : Fragment() {
    private var binding : FragmentAddBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater,container,false)

//        var sData = resources.getStringArray(R.array.spinner_day)
//        var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, sData)
//        binding!!.spinner1.adapter = adapter

//        var sData2 = resources.getStringArray(R.array.spinner_cost)
//        var adapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, sData2)
//        binding!!.spinner2.adapter = adapter2

//        binding!!.addCostBtn.setOnClickListener {
//            val transaction = (activity as MainActivity).supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.frameLayout, DayFragment())
//            transaction.disallowAddToBackStack()
//            transaction.commit()
//        }

        return binding!!.root
    }
}