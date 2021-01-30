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
import com.liuyuheng.handytools.databinding.DialogFragmentAddBillPersonBinding
import com.liuyuheng.handytools.internal.utilValidatePaidAmount
import com.liuyuheng.handytools.internal.utilValidatePerson
import com.liuyuheng.handytools.repository.BillPerson
import com.liuyuheng.handytools.ui.AddBillPaymentDialogFragmentState
import org.koin.android.viewmodel.ext.android.viewModel

class AddBillPersonDialogFragment: DialogFragment() {

    private lateinit var binding: DialogFragmentAddBillPersonBinding
    private val billCalculatorViewModel: BillCalculatorViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_rounded_10dp)
        binding = DialogFragmentAddBillPersonBinding.inflate(inflater, container, false)
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
        when (val fragmentState = billCalculatorViewModel.addBillPaymentDialogFragmentState) {
            is AddBillPaymentDialogFragmentState.AddBillPerson -> { /* default state for adding of new bill person */ }
            is AddBillPaymentDialogFragmentState.EditBillPerson -> { // fragment can also be used for editing a current bill person
                val currentBillPerson = fragmentState.billPerson
                binding.buttonDelete.visible()
                binding.textInputLayoutPersonSpinner.editText?.setText(currentBillPerson.name)
                binding.textInputLayoutPaidAmount.editText?.setText(currentBillPerson.paidAmount.toString())
                binding.textInputLayoutPersonSpinner.isEnabled = false
            }
        }
    }

    private fun setupObservers() {
        billCalculatorViewModel.getPersonsStringLiveData().observe(viewLifecycleOwner) { personsString ->
            val list = personsString.split(",").map { it.trim() }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
            (binding.textInputLayoutPersonSpinner.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }
    }

    private fun setupListeners() {
        binding.buttonDone.setOnClickListener {
            val personName = binding.textInputLayoutPersonSpinner.editText?.text.toString()
            val paidAmount = binding.textInputLayoutPaidAmount.editText?.text.toString()
            val billPerson = BillPerson(personName, if (paidAmount.isNotBlank()) paidAmount.toDouble() else 0.0)

            if (validatePerson(personName) and validatePaidAmount(paidAmount)) {
                billCalculatorViewModel.addEditBillPerson(billPerson)
                dismiss()
            }
        }
        binding.buttonDelete.setOnClickListener {
            when (val fragmentState = billCalculatorViewModel.addBillPaymentDialogFragmentState) {
                is AddBillPaymentDialogFragmentState.EditBillPerson -> {
                    billCalculatorViewModel.deleteCurrentBillPerson(fragmentState.billPerson)
                    dismiss()
                }
            }
        }
        binding.buttonCancel.setOnClickListener { dismiss() }
    }

    private fun validatePerson(personName: String) = utilValidatePerson(personName, binding.textInputLayoutPersonSpinner)
    private fun validatePaidAmount(paidAmount: String) = utilValidatePaidAmount(paidAmount, binding.textInputLayoutPaidAmount)
}

