package com.liuyuheng.handytools.ui.billcalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.liuyuheng.handytools.databinding.ListBillItemPersonBinding
import com.liuyuheng.handytools.repository.BillItemPerson

class BillItemPersonAdapter(private val itemListener: (BillItemPerson) -> Unit): ListAdapter<BillItemPerson, BillItemPersonAdapter.PersonViewHolder>(PersonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(ListBillItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position), itemListener)
    }

    class PersonViewHolder(private val binding: ListBillItemPersonBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(billItemPerson: BillItemPerson, itemListener: (BillItemPerson) -> Unit) = with(binding){
            textViewPersonNameValue.text = billItemPerson.name
            textViewPaidAmountValue.text = billItemPerson.getPaidAmountString()
            root.setOnClickListener { itemListener(billItemPerson) }
        }
    }

    // submitList requires a new list in order to do comparison and effect change in UI
    override fun submitList(list: List<BillItemPerson>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }
}

private class PersonDiffCallback: DiffUtil.ItemCallback<BillItemPerson>() {
    override fun areItemsTheSame(oldItem: BillItemPerson, newItem: BillItemPerson): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: BillItemPerson, newItem: BillItemPerson): Boolean {
        return oldItem.name == newItem.name && oldItem.paidAmount == newItem.paidAmount
    }
}