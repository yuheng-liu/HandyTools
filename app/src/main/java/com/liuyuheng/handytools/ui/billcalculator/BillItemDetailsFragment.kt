package com.liuyuheng.handytools.ui.billcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.databinding.FragmentBillItemDetailsBinding
import com.liuyuheng.handytools.internal.navigate
import com.liuyuheng.handytools.internal.utilValidateItemName
import com.liuyuheng.handytools.internal.utilValidateItemCost
import com.liuyuheng.handytools.repository.BillItem
import com.liuyuheng.handytools.repository.BillItemPerson
import com.liuyuheng.handytools.ui.AddItemPaymentDialogFragmentState
import org.koin.android.viewmodel.ext.android.viewModel

class BillItemDetailsFragment: Fragment() {

    private lateinit var binding: FragmentBillItemDetailsBinding
    private val billCalculatorViewModel: BillCalculatorViewModel by viewModel()

    private lateinit var billItemPersonListAdapter: BillItemPersonAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBillItemDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        billItemPersonListAdapter = BillItemPersonAdapter { billItemPerson -> onPersonsAdapterItemPressed(billItemPerson) }
        binding.recyclerViewItemPersonsList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewItemPersonsList.adapter = billItemPersonListAdapter
    }

    private fun setupObservers() {
        billCalculatorViewModel.getCurrentBillItemLiveData().observe(viewLifecycleOwner) { item ->
            binding.textInputLayoutItemName.editText?.setText(item.name)
            if (item.cost != 0.0) binding.textInputLayoutItemCost.editText?.setText(item.getItemCostString(false))
            billItemPersonListAdapter.submitList(item.itemPersonList)
        }
    }

    private fun setupListeners() {
        binding.buttonAddItemPerson.setOnClickListener {
            billCalculatorViewModel.addItemPaymentDialogFragmentState = AddItemPaymentDialogFragmentState.AddItemPerson
            navigate(R.id.action_billItemDetailsFragment_to_addItemPaymentDialogFragment)
        }
        binding.buttonDone.setOnClickListener {
            val itemName = binding.textInputLayoutItemName.editText?.text.toString()
            val itemCost = binding.textInputLayoutItemCost.editText?.text.toString()
            val personsList = billItemPersonListAdapter.currentList

            if (validateItemName(itemName) and validateItemCost(itemCost)) {
                billCalculatorViewModel.updateBillItem(BillItem(itemName, itemCost.toDouble(), personsList))
                findNavController().navigateUp()
            }
        }
    }

    private fun onPersonsAdapterItemPressed(billItemPerson: BillItemPerson) {
        billCalculatorViewModel.addItemPaymentDialogFragmentState = AddItemPaymentDialogFragmentState.EditItemPerson(billItemPerson)
        navigate(R.id.action_billItemDetailsFragment_to_addItemPaymentDialogFragment)
    }

    private fun validateItemName(billName: String) = utilValidateItemName(billName, binding.textInputLayoutItemName)
    private fun validateItemCost(billCost: String) = utilValidateItemCost(billCost, binding.textInputLayoutItemCost)
}