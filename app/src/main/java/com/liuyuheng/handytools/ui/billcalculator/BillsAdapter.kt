package com.liuyuheng.handytools.ui.billcalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.repository.Bill

class BillsAdapter(
    private val onClickListener: ()
): ListAdapter<Bill, BillsAdapter.BillItemViewHolder>(BillsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillItemViewHolder {
        return BillItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_bill , parent, false))
    }

    override fun onBindViewHolder(holder: BillItemViewHolder, position: Int) {
        holder.bind()
    }

    class BillItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind() = with(itemView) {

        }
    }

    companion object {
        private class BillsDiffCallback: DiffUtil.ItemCallback<Bill>() {
            override fun areItemsTheSame(oldItem: Bill, newItem: Bill): Boolean { return oldItem.id == newItem.id }
            override fun areContentsTheSame(oldItem: Bill, newItem: Bill): Boolean { return oldItem == newItem }
        }
    }
}