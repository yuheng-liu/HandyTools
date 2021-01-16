package com.liuyuheng.handytools.ui.billcalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.liuyuheng.handytools.databinding.ListItemBillDetailsBinding
import com.liuyuheng.handytools.repository.BillPerson

class PersonsAdapter(): ListAdapter<BillPerson, PersonsAdapter.PersonViewHolder>(PersonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(ListItemBillDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PersonViewHolder(private val binding: ListItemBillDetailsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(billPerson: BillPerson) = with(binding){
            textInputLayoutPersonName.hint = String.format("Person %s Name", billPerson.index.toString())
        }
    }
}

private class PersonDiffCallback: DiffUtil.ItemCallback<BillPerson>() {
    override fun areItemsTheSame(oldItem: BillPerson, newItem: BillPerson): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: BillPerson, newItem: BillPerson): Boolean {
        return oldItem.name == newItem.name
    }
}