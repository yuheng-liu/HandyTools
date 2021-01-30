package com.liuyuheng.handytools.ui.billcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.databinding.FragmentBillDetailsBinding
import com.liuyuheng.handytools.internal.navigate
import com.liuyuheng.handytools.internal.utilValidateBillName
import com.liuyuheng.handytools.internal.utilValidateTotalCosts
import com.liuyuheng.handytools.repository.Bill
import com.liuyuheng.handytools.repository.BillPerson
import com.liuyuheng.handytools.ui.AddBillPaymentDialogFragmentState
import org.koin.android.viewmodel.ext.android.viewModel

class BillDetailsFragment: Fragment() {

    private lateinit var binding: FragmentBillDetailsBinding
    private val billCalculatorViewModel: BillCalculatorViewModel by viewModel()

    private lateinit var billPersonsListAdapter: BillPersonsAdapter

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
        billPersonsListAdapter = BillPersonsAdapter { billPerson -> onPersonsAdapterItemPressed(billPerson) }
        binding.recyclerViewPersonsList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPersonsList.adapter = billPersonsListAdapter
    }

    private fun setupObservers() {
        billCalculatorViewModel.getCurrentBillLiveData().observe(viewLifecycleOwner) { bill ->
            binding.textInputLayoutBillName.editText?.setText(bill.name)
            if (bill.totalCosts != 0.0) binding.textInputLayoutBillTotalCost.editText?.setText(bill.getTotalCostsString(false))
            billPersonsListAdapter.submitList(bill.billPersonList)
        }
    }

    private fun setupListeners() {
        binding.buttonAddPayment.setOnClickListener {
            billCalculatorViewModel.addBillPaymentDialogFragmentState = AddBillPaymentDialogFragmentState.AddBillPerson
            navigate(R.id.action_billDetailsFragment_to_addBillPaymentDialogFragment)
        }
        binding.buttonDone.setOnClickListener {
            val billName = binding.textInputLayoutBillName.editText?.text.toString()
            val totalCosts = binding.textInputLayoutBillTotalCost.editText?.text.toString()
            val personsList = billPersonsListAdapter.currentList

            if (validateBillName(billName) and validateTotalCosts(totalCosts)) {
                billCalculatorViewModel.updateBill(Bill(billName, totalCosts.toDouble(), personsList))
                findNavController().navigateUp()
            }
        }
    }

    private fun onPersonsAdapterItemPressed(billPerson: BillPerson) {
        billCalculatorViewModel.addBillPaymentDialogFragmentState = AddBillPaymentDialogFragmentState.EditBillPerson(billPerson)
        navigate(R.id.action_billDetailsFragment_to_addBillPaymentDialogFragment)
    }

    private fun validateBillName(billName: String) = utilValidateBillName(billName, binding.textInputLayoutBillName)
    private fun validateTotalCosts(totalCosts: String) = utilValidateTotalCosts(totalCosts, binding.textInputLayoutBillTotalCost)
}