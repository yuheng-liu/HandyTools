package com.liuyuheng.handytools.ui.billcalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liuyuheng.handytools.databinding.ListBillItemBinding
import com.liuyuheng.handytools.repository.BillItem

class ItemsAdapter(
    private val itemListener: (Int) -> Unit,
    private val onLongClickListener: (View, Int) -> Unit
): ListAdapter<BillItem, ItemsAdapter.BillItemViewHolder>(BillsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillItemViewHolder {
        return BillItemViewHolder(ListBillItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BillItemViewHolder, position: Int) {
        holder.bind(position, getItem(position), itemListener, onLongClickListener)
    }

    class BillItemViewHolder(private val binding: ListBillItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, billItem: BillItem, itemListener: (Int) -> Unit, onLongClickListener: (View, Int) -> Unit) = with(binding) {
            val itemName = "Item ${position+1} (${billItem.name})"
            binding.textViewItemName.text = itemName
            val billCost = "Item Cost: ${billItem.getItemCostString()}"
            binding.textViewItemCost.text = billCost
            binding.textViewPaymentsPersonName.text = billItem.itemPersonList.joinToString("\n") { it.name }
            binding.textViewPaymentsPersonPaidAmount.text = billItem.itemPersonList.joinToString("\n") { it.getPaidAmountString() }

            binding.root.setOnClickListener { itemListener(position) }
            binding.root.setOnLongClickListener { view ->
                onLongClickListener(view, position)
                true
            }
        }
    }

    // submitList requires a new list in order to do comparison and effect change in UI
    override fun submitList(list: List<BillItem>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }
}

private class BillsDiffCallback: DiffUtil.ItemCallback<BillItem>() {
    override fun areItemsTheSame(oldItem: BillItem, newItem: BillItem): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: BillItem, newItem: BillItem): Boolean {
        return oldItem.name == newItem.name
    }
}