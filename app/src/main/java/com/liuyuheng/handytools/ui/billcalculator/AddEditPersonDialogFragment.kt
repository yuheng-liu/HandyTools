package com.liuyuheng.handytools.ui.billcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.databinding.ViewAddEditPersonBinding
import org.koin.android.viewmodel.ext.android.viewModel

class AddEditPersonDialogFragment: DialogFragment() {

    private lateinit var binding: ViewAddEditPersonBinding
    private val billCalculatorViewModel: BillCalculatorViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // set dialog background here, dialogs have their own window
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_rounded_10dp)
        binding = ViewAddEditPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    override fun onStart() {
        super.onStart()

        // need to set width & height manually as dialog fragment disregards values set in xml
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setupObservers() {
        billCalculatorViewModel.getPersonsStringLiveData().observe(viewLifecycleOwner) { personsString ->
            if (personsString.isNotBlank()) binding.textInputLayoutPersonNames.editText?.setText(personsString)
        }
    }

    private fun setupListeners() {
        binding.buttonDone.setOnClickListener {
            billCalculatorViewModel.setPersonsString(binding.textInputLayoutPersonNames.editText?.text.toString())
            dismiss()
        }
        binding.buttonCancel.setOnClickListener { dismiss() }
    }
}