package com.liuyuheng.handytools.ui.billcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.databinding.FragmentBillCalculatorBinding
import com.liuyuheng.handytools.internal.navigate
import com.liuyuheng.handytools.ui.BillDetailsFragmentState
import org.koin.android.viewmodel.ext.android.viewModel

class BillCalculatorFragment : Fragment() {

    private lateinit var binding: FragmentBillCalculatorBinding
    private val billCalculatorViewModel: BillCalculatorViewModel by viewModel()

    private lateinit var billsListAdapter: BillsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBillCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        billsListAdapter = BillsAdapter { bill -> onBillsAdapterItemPressed(bill) }
        binding.recyclerViewBills.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewBills.adapter = billsListAdapter
    }

    private fun setupObservers() {
        billCalculatorViewModel.getPersonsStringLiveData().observe(viewLifecycleOwner) { personsString ->
            binding.textViewCurrentPersonsValue.text = personsString
            binding.buttonAddBill.isEnabled = personsString.isNotBlank()
        }
        billCalculatorViewModel.getAllBillListLiveData().observe(viewLifecycleOwner) { billsList ->
            billsListAdapter.submitList(billsList)
        }
    }

    private fun setupListeners() {
        binding.buttonAddPerson.setOnClickListener { navigate(R.id.action_billCalculatorFragment_to_addEditPersonDialogFragment) }
        binding.buttonAddBill.setOnClickListener {
            billCalculatorViewModel.billDetailsFragmentState = BillDetailsFragmentState.AddBill
            navigate(R.id.action_billCalculatorFragment_to_billDetailsDialogFragment)
        }
    }

    private fun onBillsAdapterItemPressed(index: Int) {
        billCalculatorViewModel.currentBillIndex = index
        billCalculatorViewModel.billDetailsFragmentState = BillDetailsFragmentState.EditBill
        navigate(R.id.action_billCalculatorFragment_to_billDetailsDialogFragment)
    }
}