package com.liuyuheng.handytools.ui.billcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.liuyuheng.handytools.databinding.FragmentBillDetailsBinding
import com.liuyuheng.handytools.internal.utilValidateBillName
import com.liuyuheng.handytools.internal.utilValidateNumberOfPeople
import com.liuyuheng.handytools.internal.utilValidateTotalCosts
import com.liuyuheng.handytools.repository.BillPerson

class BillDetailsFragment: Fragment() {

    private lateinit var binding: FragmentBillDetailsBinding

    private lateinit var personsListAdapter: PersonsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBillDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        personsListAdapter = PersonsAdapter()
        binding.recyclerViewPersonsList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPersonsList.adapter = personsListAdapter
    }

    private fun setupListeners() {
        binding.buttonNextStep.setOnClickListener {
            if (validateBillName() and validateTotalCosts() and validateNumberOfPeople()) {
                val numberOfPeople = binding.textInputLayoutNumberOfPeople.editText?.text.toString().toInt()
                personsListAdapter.submitList(1.rangeTo(numberOfPeople).map { number -> BillPerson(number, "", 0.00) })
            }
        }
    }

    private fun validateBillName() = utilValidateBillName(binding.textInputLayoutBillName.editText?.text.toString(), binding.textInputLayoutBillName)
    private fun validateTotalCosts() = utilValidateTotalCosts(binding.textInputLayoutBillTotalCost.editText?.text.toString(), binding.textInputLayoutBillTotalCost)
    private fun validateNumberOfPeople() = utilValidateNumberOfPeople(binding.textInputLayoutNumberOfPeople.editText?.text.toString(), binding.textInputLayoutNumberOfPeople)
}