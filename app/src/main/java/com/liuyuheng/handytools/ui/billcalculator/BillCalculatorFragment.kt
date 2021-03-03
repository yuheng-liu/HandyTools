package com.liuyuheng.handytools.ui.billcalculator

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.databinding.FragmentBillCalculatorBinding
import com.liuyuheng.handytools.internal.navigate
import org.koin.android.viewmodel.ext.android.viewModel

class BillCalculatorFragment : Fragment() {

    private lateinit var binding: FragmentBillCalculatorBinding
    private val billCalculatorViewModel: BillCalculatorViewModel by viewModel()

    private lateinit var billItemsAdapter: BillItemsAdapter

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
        billItemsAdapter = BillItemsAdapter ({ item -> onItemPressed(item) }, { view, selectedIndex -> onItemLongPressed(view, selectedIndex) })
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewItems.adapter = billItemsAdapter
    }

    private fun setupObservers() {
        billCalculatorViewModel.getNameListLiveData().observe(viewLifecycleOwner) { nameList ->
            binding.textViewCurrentPersonsValue.text = nameList.joinToString(", ")
            binding.buttonAddItem.isEnabled = nameList.isNotEmpty()
        }
        billCalculatorViewModel.getAllBillItemListLiveData().observe(viewLifecycleOwner) { itemList ->
            billItemsAdapter.submitList(itemList)
            binding.buttonCompute.isEnabled = itemList.isNotEmpty()
        }
    }

    private fun setupListeners() {
        binding.buttonAddPerson.setOnClickListener { navigate(R.id.action_billCalculatorFragment_to_addEditItemPersonDialogFragment) }
        binding.buttonAddItem.setOnClickListener { navigate(R.id.action_billCalculatorFragment_to_addBillItemDialogFragment) }
        binding.buttonCompute.setOnClickListener { navigate(R.id.action_billCalculatorFragment_to_billResultDialogFragment) }
    }

    private fun onItemPressed(index: Int) {
        billCalculatorViewModel.currentItemIndex = index
        navigate(R.id.action_billCalculatorFragment_to_billItemDetailsDialogFragment)
    }

    private fun onItemLongPressed(view: View, selectedIndex: Int) {
        PopupMenu(requireContext(), view).apply {
            setOnMenuItemClickListener {
                billCalculatorViewModel.deleteBillItem(selectedIndex)
                true
            }
            inflate(R.menu.bill_item_menu)
            show()
        }
    }
}