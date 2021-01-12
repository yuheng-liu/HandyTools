package com.liuyuheng.handytools.ui.billcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.liuyuheng.handytools.databinding.FragmentBillCalculatorBinding

class BillCalculatorFragment : Fragment() {

    private lateinit var binding: FragmentBillCalculatorBinding

    private lateinit var billsListAdapter: BillsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBillCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.imageButtonAddBill.setOnClickListener {

        }
    }
}