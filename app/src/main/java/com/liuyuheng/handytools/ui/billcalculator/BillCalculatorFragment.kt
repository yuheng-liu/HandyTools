package com.liuyuheng.handytools.ui.billcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.liuyuheng.common.utils.dialogs.BillCalculatorDialogUtils
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.databinding.FragmentBillCalculatorBinding
import com.liuyuheng.handytools.internal.navigate
import org.koin.android.viewmodel.ext.android.viewModel

class BillCalculatorFragment : Fragment() {

    private lateinit var binding: FragmentBillCalculatorBinding
    private val billCalculatorViewModel: BillCalculatorViewModel by viewModel()

    private lateinit var billsListAdapter: BillsAdapter
    private lateinit var addEditPersonDialog: AlertDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBillCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDialogs()
        setupObservers()
        setupListeners()
    }

    private fun setupDialogs() {
        addEditPersonDialog = BillCalculatorDialogUtils.getAddEditPersonsDialog(requireContext(), View.inflate(requireContext(), R.layout.view_add_edit_person, null))
        addEditPersonDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener{

        }
    }

    private fun setupObservers() {

    }

    private fun setupListeners() {
        binding.buttonAddPerson.setOnClickListener { showAddEditPersonsDialog() }
        binding.buttonAddBill.setOnClickListener { navigate(R.id.action_billTypeFragment_to_billDetailsDialogFragment) }
    }

    private fun showAddEditPersonsDialog() {
        val editInfoDialog = MaterialAlertDialogBuilder(requireContext())
            .setView(View.inflate(requireContext(), R.layout.view_add_edit_person, null))
            .setPositiveButton("Done", null)
            .setNegativeButton("Cancel", null)
            .create()

        editInfoDialog.show()
    }
}