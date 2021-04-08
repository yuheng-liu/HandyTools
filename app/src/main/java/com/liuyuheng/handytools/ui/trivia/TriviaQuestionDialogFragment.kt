package com.liuyuheng.handytools.ui.trivia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.liuyuheng.common.extensions.gone
import com.liuyuheng.common.extensions.showToast
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.databinding.DialogFragmentTriviaQuestionBinding
import org.koin.android.viewmodel.ext.android.viewModel

class TriviaQuestionDialogFragment: DialogFragment() {

    private lateinit var binding: DialogFragmentTriviaQuestionBinding
    private val triviaViewModel: TriviaViewModel by viewModel()

    private lateinit var triviaAnswerChoicesAdapter: TriviaAnswerChoicesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_rounded_10dp)
        binding = DialogFragmentTriviaQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setupObservers()
    }

    override fun onStart() {
        super.onStart()

        // need to set width & height manually as dialog fragment disregards values set in xml
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setupUi() {
        triviaAnswerChoicesAdapter = TriviaAnswerChoicesAdapter { view, isCorrect -> onItemPressed(view, isCorrect) }
        binding.recyclerViewAnswerChoices.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAnswerChoices.adapter = triviaAnswerChoicesAdapter
    }

    private fun setupObservers() {
        triviaViewModel.getTriviaQuestionListLiveData().observe(viewLifecycleOwner) { triviaQuestions ->
            binding.progressCircular.gone()

            // TODO add dynamic handling for multiple questions, now only works for 1 question
            binding.textViewQuestion.text = triviaQuestions[0].question

            val list = triviaQuestions[0].wrongChoices.map { TriviaAnswerChoices(it, false) }.toMutableList().apply {
                add(TriviaAnswerChoices(triviaQuestions[0].answer, true))
                shuffle()
            }

            triviaAnswerChoicesAdapter.submitList(list.toList())
        }
    }

    private fun onItemPressed(view: View, isCorrect: Boolean) {
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), if (isCorrect) R.color.green else R.color.red))
    }
}