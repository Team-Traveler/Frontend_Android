package com.example.traveler.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.traveler.R
import com.example.traveler.adapter.OuterAdapter
import com.example.traveler.fragment.SumFragment

class BudgetDialog(context: Context, private val budgetDialogListener: BudgetDialogListener) : Dialog(context){
    interface BudgetDialogListener {
        fun onBudgetEntered(budget: String)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.budget_dialog)

        var okButton : Button = findViewById(R.id.budgetBtnOk)
        var budgetEditView : EditText = findViewById(R.id.etBudget)

        // 배경 투명하게 바꿔줌
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        okButton.setOnClickListener {
            val budget = budgetEditView.text.toString()

            // 입력하지 않았을 때
            if (TextUtils.isEmpty(budget)) {
                Toast.makeText(context, "예산을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            // 입력 창이 비어 있지 않을 때
            else {
                budgetDialogListener.onBudgetEntered(budget)
                dismiss()
            }
        }
    }
}