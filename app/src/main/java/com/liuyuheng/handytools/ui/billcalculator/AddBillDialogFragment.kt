package com.liuyuheng.handytools.ui.billcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.databinding.DialogFragmentAddBillBinding
import com.liuyuheng.handytools.internal.utilValidateBillName
import com.liuyuheng.handytools.internal.utilValidateTotalCosts
import org.koin.android.viewmodel.ext.android.viewModel

class AddBillDialogFragment: DialogFragment() {

    private lateinit var binding: DialogFragmentAddBillBinding
    private val billCalculatorViewModel: BillCalculatorViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_rounded_10dp)
        binding = DialogFragmentAddBillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    override fun onStart() {
        super.onStart()

        // need to set width & height manually as dialog fragment disregards values set in xml
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setupListeners() {
        binding.buttonDone.setOnClickListener {
            val billName = binding.textInputLayoutBillName.editText?.text.toString()
            val totalCosts = binding.textInputLayoutBillTotalCost.editText?.text.toString()

            if (validateBillName(billName) and validateTotalCosts(totalCosts)) {
                billCalculatorViewModel.addBill(billName, totalCosts.toDouble())
            }
            dismiss()
        }
        binding.buttonCancel.setOnClickListener { dismiss() }
    }

    private fun validateBillName(billName: String) = utilValidateBillName(billName, binding.textInputLayoutBillName)
    private fun validateTotalCosts(totalCosts: String) = utilValidateTotalCosts(totalCosts, binding.textInputLayoutBillTotalCost)
}