package com.liuyuheng.handytools.ui.billcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.liuyuheng.common.extensions.showToast
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.databinding.FragmentBillDetailsBinding
import com.liuyuheng.handytools.internal.navigate
import com.liuyuheng.handytools.internal.utilValidateBillName
import com.liuyuheng.handytools.internal.utilValidateTotalCosts
import com.liuyuheng.handytools.repository.Bill
import com.liuyuheng.handytools.repository.BillPerson
import com.liuyuheng.handytools.ui.AddBillPaymentDialogFragmentState
import com.liuyuheng.handytools.ui.BillDetailsFragmentState
import org.koin.android.viewmodel.ext.android.viewModel

class BillDetailsFragment: Fragment() {

    private lateinit var binding: FragmentBillDetailsBinding
    private val billCalculatorViewModel: BillCalculatorViewModel by viewModel()

    private lateinit var personsListAdapter: PersonsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBillDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        personsListAdapter = PersonsAdapter { billPerson -> onPersonsAdapterItemPressed(billPerson) }
        binding.recyclerViewPersonsList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPersonsList.adapter = personsListAdapter
    }

    private fun setupObservers() {
        when (billCalculatorViewModel.billDetailsFragmentState) {
            is BillDetailsFragmentState.AddBill -> billCalculatorViewModel.getNewBillLiveData().observe(viewLifecycleOwner) { newBill -> updateUi(newBill) }
            is BillDetailsFragmentState.EditBill -> billCalculatorViewModel.getCurrentBillLiveData().observe(viewLifecycleOwner) { currentBill -> updateUi(currentBill) }
        }
    }

    private fun setupListeners() {
        binding.buttonAddPayment.setOnClickListener {
            val billName = binding.textInputLayoutBillName.editText?.text.toString()
            val totalCosts = binding.textInputLayoutBillTotalCost.editText?.text.toString()

            billCalculatorViewModel.updateNewBillInfo(billName, totalCosts.toDouble())
            billCalculatorViewModel.addBillPaymentDialogFragmentState = AddBillPaymentDialogFragmentState.AddBillPerson
            navigate(R.id.action_billDetailsFragment_to_addBillPaymentDialogFragment)
        }
        binding.buttonDone.setOnClickListener {
            val billName = binding.textInputLayoutBillName.editText?.text.toString()
            val totalCosts = binding.textInputLayoutBillTotalCost.editText?.text.toString()
            val personsList = personsListAdapter.currentList

            if (validateBillName(billName) and validateTotalCosts(totalCosts) and validateBillPayments(personsList) && validateIsNotDuplicate(billName)) {
                billCalculatorViewModel.addEditBill(Bill(billName, totalCosts.toDouble(), personsList))
                findNavController().navigateUp()
            }
        }
    }

    private fun onPersonsAdapterItemPressed(billPerson: BillPerson) {
        billCalculatorViewModel.addBillPaymentDialogFragmentState = AddBillPaymentDialogFragmentState.EditBillPerson(billPerson)
        navigate(R.id.action_billDetailsFragment_to_addBillPaymentDialogFragment)
    }

    private fun updateUi(bill: Bill) {
        binding.textInputLayoutBillName.editText?.setText(bill.name)
        if (bill.totalCosts != 0.0) binding.textInputLayoutBillTotalCost.editText?.setText(bill.totalCosts.toString())
        personsListAdapter.submitList(bill.billPersonList)
    }

    private fun validateBillName(billName: String) = utilValidateBillName(billName, binding.textInputLayoutBillName)
    private fun validateTotalCosts(totalCosts: String) = utilValidateTotalCosts(totalCosts, binding.textInputLayoutBillTotalCost)
    private fun validateBillPayments(personsList: List<BillPerson>): Boolean {
        return if (personsList.isEmpty()) {
            showToast("Please add at least 1 person payment")
            false
        } else {
            true
        }
    }
    private fun validateIsNotDuplicate(billName: String): Boolean {
        return if (billCalculatorViewModel.isBillAlreadyExists(billName)) {
            binding.textInputLayoutBillName.error = "Bill already exists, please click on the bill card to edit instead"
            false
        } else {
            binding.textInputLayoutBillName.error = null
            true
        }
    }
}