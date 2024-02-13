package com.liuyuheng.handytools.ui.billcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.DialogFragment
import com.liuyuheng.common.extensions.visible
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.databinding.DialogFragmentAddBillItemPersonBinding
import com.liuyuheng.handytools.internal.utilValidatePaidAmount
import com.liuyuheng.handytools.internal.utilValidatePerson
import com.liuyuheng.handytools.repository.BillItemPerson
import com.liuyuheng.handytools.ui.AddItemPaymentDialogFragmentState
import org.koin.android.viewmodel.ext.android.viewModel

class AddBillItemPersonDialogFragment: DialogFragment() {

    private lateinit var binding: DialogFragmentAddBillItemPersonBinding
    private val billCalculatorViewModel: BillCalculatorViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_rounded_10dp)
        binding = DialogFragmentAddBillItemPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
        setupListeners()
    }

    override fun onStart() {
        super.onStart()

        // need to set width & height manually as dialog fragment disregards values set in xml
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setupUI() {
        when (val fragmentState = billCalculatorViewModel.addItemPaymentDialogFragmentState) {
            is AddItemPaymentDialogFragmentState.AddItemPerson -> { /* default state for adding of new bill person */ }
            is AddItemPaymentDialogFragmentState.EditItemPerson -> { // fragment can also be used for editing a current bill person
                val currentBillItemPerson = fragmentState.billItemPerson
                binding.buttonDelete.visible()
                binding.textInputLayoutPersonSpinner.editText?.setText(currentBillItemPerson.name)
                binding.textInputLayoutPaidAmount.editText?.setText(currentBillItemPerson.paidAmount.toString())
                binding.textInputLayoutPersonSpinner.isEnabled = false
            }
        }
    }

    private fun setupObservers() {
        billCalculatorViewModel.getNameListLiveData().observe(viewLifecycleOwner) { nameList ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, billCalculatorViewModel.filterExistingBillPersons(nameList))
            (binding.textInputLayoutPersonSpinner.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }
    }

    private fun setupListeners() {
        binding.buttonDone.setOnClickListener {
            val personName = binding.textInputLayoutPersonSpinner.editText?.text.toString()
            val paidAmount = binding.textInputLayoutPaidAmount.editText?.text.toString()
            val billItemPerson = BillItemPerson(personName, if (paidAmount.isNotBlank()) paidAmount.toDouble() else 0.0)

            if (validateItemPerson(personName) and validatePaidAmount(paidAmount)) {
                billCalculatorViewModel.addEditItemPerson(billItemPerson)
                dismiss()
            }
        }
        binding.buttonDelete.setOnClickListener {
            when (val fragmentState = billCalculatorViewModel.addItemPaymentDialogFragmentState) {
                is AddItemPaymentDialogFragmentState.EditItemPerson -> {
                    billCalculatorViewModel.deleteCurrentItemPerson(fragmentState.billItemPerson)
                    dismiss()
                }
                else -> { }
            }
        }
        binding.buttonCancel.setOnClickListener { dismiss() }
    }

    private fun validateItemPerson(personName: String) = utilValidatePerson(personName, binding.textInputLayoutPersonSpinner)
    private fun validatePaidAmount(paidAmount: String) = utilValidatePaidAmount(paidAmount, binding.textInputLayoutPaidAmount)
}

