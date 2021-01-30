package com.liuyuheng.handytools.ui.billcalculator

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.databinding.FragmentBillCalculatorBinding
import com.liuyuheng.handytools.internal.navigate
import com.liuyuheng.handytools.repository.Bill
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
        billsListAdapter = BillsAdapter ({ bill -> onBillItemPressed(bill) }, { view, selectedIndex -> onBillItemLongPressed(view, selectedIndex) })
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
        binding.buttonAddBill.setOnClickListener { navigate(R.id.action_billCalculatorFragment_to_addBillDialogFragment) }
    }

    private fun onBillItemPressed(index: Int) {
        billCalculatorViewModel.currentBillIndex = index
        navigate(R.id.action_billCalculatorFragment_to_billDetailsDialogFragment)
    }

    private fun onBillItemLongPressed(view: View, selectedIndex: Int) {
        PopupMenu(requireContext(), view).apply {
            setOnMenuItemClickListener {
                Log.d("myDebug", "selected index: $selectedIndex")
                true
            }
            inflate(R.menu.bill_menu)
            show()
        }
    }
}