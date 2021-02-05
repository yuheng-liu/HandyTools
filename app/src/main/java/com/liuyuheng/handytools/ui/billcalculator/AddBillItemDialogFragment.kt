package com.liuyuheng.handytools.ui.billcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.databinding.DialogFragmentAddBillItemBinding
import com.liuyuheng.handytools.internal.utilValidateItemName
import com.liuyuheng.handytools.internal.utilValidateItemCost
import org.koin.android.viewmodel.ext.android.viewModel

class AddBillItemDialogFragment: DialogFragment() {

    private lateinit var binding: DialogFragmentAddBillItemBinding
    private val billCalculatorViewModel: BillCalculatorViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_rounded_10dp)
        binding = DialogFragmentAddBillItemBinding.inflate(inflater, container, false)
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
            val billName = binding.textInputLayoutItemName.editText?.text.toString()
            val billCost = binding.textInputLayoutItemCost.editText?.text.toString()

            if (validateBillName(billName) and validateBillCost(billCost)) {
                billCalculatorViewModel.addBillItem(billName, billCost.toDouble())
            }
            dismiss()
        }
        binding.buttonCancel.setOnClickListener { dismiss() }
    }

    private fun validateBillName(billName: String) = utilValidateItemName(billName, binding.textInputLayoutItemName)
    private fun validateBillCost(billCost: String) = utilValidateItemCost(billCost, binding.textInputLayoutItemCost)
}