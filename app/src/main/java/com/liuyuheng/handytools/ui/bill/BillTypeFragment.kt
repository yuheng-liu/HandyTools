package com.liuyuheng.handytools.ui.bill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.liuyuheng.common.extensions.visible
import com.liuyuheng.handytools.R
import kotlinx.android.synthetic.main.fragment_bill_type.*

class BillTypeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bill_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setupListeners()
    }

    private fun setupUi() {
        spinner_number_of_people.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, (1..20).toList())
    }

    private fun setupListeners() {
        toggleGroup_bill_type.addOnButtonCheckedListener { _, _, _ ->
            toggleGroup_bill_type.isSelectionRequired = true
            group_split_type.visible()
        }
        toggleGroup_split_type.addOnButtonCheckedListener { _, _, _ ->
            toggleGroup_split_type.isSelectionRequired = true
            textView_number_of_people.visible()
        }
    }
}