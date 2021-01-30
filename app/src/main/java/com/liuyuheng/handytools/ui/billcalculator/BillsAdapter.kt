package com.liuyuheng.handytools.ui.billcalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liuyuheng.handytools.databinding.ListItemBillBinding
import com.liuyuheng.handytools.repository.Bill

class BillsAdapter(private val itemListener: (Int) -> Unit): ListAdapter<Bill, BillsAdapter.BillItemViewHolder>(BillsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillItemViewHolder {
        return BillItemViewHolder(ListItemBillBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BillItemViewHolder, position: Int) {
        holder.bind(position, getItem(position), itemListener)
    }

    class BillItemViewHolder(private val binding: ListItemBillBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, bill: Bill, itemListener: (Int) -> Unit) = with(binding) {
            val billName = "Bill ${position+1} (${bill.name})"
            binding.textViewBillName.text = billName
            binding.textViewBillTotalCosts.text = bill.getTotalCostsString()
            binding.textViewPaymentsPersonName.text = bill.billPersonList.joinToString("\n") { it.name }
            binding.textViewPaymentsPersonPaidAmount.text = bill.billPersonList.joinToString("\n") { it.getPaidAmountString() }

            binding.root.setOnClickListener{ itemListener(position) }
        }
    }

    // submitList requires a new list in order to do comparison and effect change in UI
    override fun submitList(list: List<Bill>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }
}

private class BillsDiffCallback: DiffUtil.ItemCallback<Bill>() {
    override fun areItemsTheSame(oldItem: Bill, newItem: Bill): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Bill, newItem: Bill): Boolean {
        return oldItem.name == newItem.name
    }
}