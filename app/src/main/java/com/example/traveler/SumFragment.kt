package com.example.traveler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.traveler.databinding.FragmentSumBinding

class SumFragment : Fragment() {
    private lateinit var binding : FragmentSumBinding
    private var budgetValue: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSumBinding.inflate(inflater,container,false)

//        val transaction = childFragmentManager.beginTransaction()
//            transaction.replace(R.id.framelayout_sum, SumGraphFragment())
//            transaction.disallowAddToBackStack()
//            transaction.commit()

        binding.putBudget.setOnClickListener {
            val transaction = (activity as MainActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, BudgetFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
            binding.putBudget.text = budgetValue  // 업데이트 추가
        }
        return binding.root
    }
    fun setBudget(budget: String) {
        budgetValue = budget
        binding.putBudget.text = budgetValue
    }
}