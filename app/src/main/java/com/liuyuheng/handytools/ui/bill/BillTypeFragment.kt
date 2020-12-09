package com.liuyuheng.handytools.ui.bill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.liuyuheng.common.extensions.visible
import com.liuyuheng.handytools.databinding.FragmentBillTypeBinding

class BillTypeFragment : Fragment() {

    private lateinit var binding: FragmentBillTypeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBillTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setupListeners()
    }

    private fun setupUi() {
        binding.spinnerNumberOfPeople.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, (1..20).toList())
    }

    private fun setupListeners() {
        binding.toggleGroupBillType.addOnButtonCheckedListener { _, _, _ ->
            binding.toggleGroupBillType.isSelectionRequired = true
            binding.groupSplitType.visible()
        }
        binding.toggleGroupSplitType.addOnButtonCheckedListener { _, _, _ ->
            binding.toggleGroupSplitType.isSelectionRequired = true
            binding.groupNumberOfPeople.visible()
        }
    }
}