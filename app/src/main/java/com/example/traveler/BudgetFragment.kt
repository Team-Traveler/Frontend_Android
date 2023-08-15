package com.example.traveler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.traveler.databinding.FragmentBudgetBinding

class BudgetFragment : Fragment() {
    private lateinit var binding: FragmentBudgetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBudgetBinding.inflate(inflater, container, false)

        binding.budgetBtnOk.setOnClickListener {
            val budgetAmount = binding.etBudget.text.toString()
            val sumFragment = parentFragment as SumFragment
            sumFragment.setBudget(budgetAmount)

//            parentFragmentManager.popBackStack()
        }
        return binding.root
    }
}