package com.example.traveler.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.example.traveler.R
import com.example.traveler.databinding.FragmentSumBinding
import com.example.traveler.dialog.BudgetDialog

class SumFragment : Fragment(), BudgetDialog.BudgetDialogListener {
    private lateinit var binding : FragmentSumBinding
    private var budgetValue: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSumBinding.inflate(inflater,container,false)

        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.framelayout_sum, SumGraphFragment())
        transaction.disallowAddToBackStack()
        transaction.commit()

        binding.putBudget.setOnClickListener {
            val budgetDialog = BudgetDialog(requireContext(), this)
            budgetDialog.show()
        }
        return binding.root
    }
    override fun onBudgetEntered(budget: String) {
        // 예산 값을 받아서 UI 업데이트
        budgetValue = budget
        binding.putBudget.visibility = GONE
        binding.textView28.visibility = VISIBLE
        binding.textView28.text = budgetValue  // 예산을 표시하는 TextView의 ID에 맞게 변경해야 합니다.
    }
}