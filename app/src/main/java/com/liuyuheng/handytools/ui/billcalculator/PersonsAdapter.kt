package com.liuyuheng.handytools.ui.billcalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.liuyuheng.handytools.databinding.ListItemBillDetailsBinding
import com.liuyuheng.handytools.repository.BillPerson

class PersonsAdapter(private val itemListener: (BillPerson) -> Unit): ListAdapter<BillPerson, PersonsAdapter.PersonViewHolder>(PersonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(ListItemBillDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position), itemListener)
    }

    class PersonViewHolder(private val binding: ListItemBillDetailsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(billPerson: BillPerson, itemListener: (BillPerson) -> Unit) = with(binding){
            textViewPersonNameValue.text = billPerson.name
            textViewPaidAmountValue.text = billPerson.getPaidAmountString()
            root.setOnClickListener { itemListener(billPerson) }
        }
    }

    // submitList requires a new list in order to do comparison and effect change in UI
    override fun submitList(list: List<BillPerson>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }
}

private class PersonDiffCallback: DiffUtil.ItemCallback<BillPerson>() {
    override fun areItemsTheSame(oldItem: BillPerson, newItem: BillPerson): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: BillPerson, newItem: BillPerson): Boolean {
        return oldItem.name == newItem.name && oldItem.paidAmount == newItem.paidAmount
    }
}