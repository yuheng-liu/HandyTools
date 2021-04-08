package com.liuyuheng.handytools.ui.trivia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.liuyuheng.handytools.databinding.ListTriviaQuestionItemBinding

class TriviaAnswerChoicesAdapter(
    private val itemListener: (View, Boolean) -> Unit,
): ListAdapter<TriviaAnswerChoices, TriviaAnswerChoicesAdapter.TriviaQuestionViewHolder>(TriviaQuestionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TriviaQuestionViewHolder {
        return TriviaQuestionViewHolder(ListTriviaQuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TriviaQuestionViewHolder, position: Int) {
        holder.bind(getItem(position), itemListener)
    }

    class TriviaQuestionViewHolder(private val binding: ListTriviaQuestionItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(triviaQuestion: TriviaAnswerChoices, itemClickListener: (View, Boolean) -> Unit) = with(binding) {
            textViewQuestionChoice.text = triviaQuestion.choiceText
            binding.root.setOnClickListener { itemClickListener(itemView, triviaQuestion.isCorrect) }
        }
    }

    // submitList requires a new list in order to do comparison and effect change in UI
    override fun submitList(list: List<TriviaAnswerChoices>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }
}

private class TriviaQuestionDiffCallback: DiffUtil.ItemCallback<TriviaAnswerChoices>() {
    override fun areItemsTheSame(oldItem: TriviaAnswerChoices, newItem: TriviaAnswerChoices): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: TriviaAnswerChoices, newItem: TriviaAnswerChoices): Boolean {
        return oldItem.choiceText == newItem.choiceText
    }
}

data class TriviaAnswerChoices(
    val choiceText: String,
    val isCorrect: Boolean
)