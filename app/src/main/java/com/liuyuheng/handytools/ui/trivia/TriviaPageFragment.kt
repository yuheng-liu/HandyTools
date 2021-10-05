package com.liuyuheng.handytools.ui.trivia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.databinding.FragmentTriviaPageBinding
import org.koin.android.viewmodel.ext.android.viewModel

class TriviaPageFragment: Fragment() {

    private lateinit var binding: FragmentTriviaPageBinding
    private lateinit var triviaAnswerChoicesAdapter: TriviaAnswerChoicesAdapter
    private val triviaViewModel: TriviaViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTriviaPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
    }

    private fun setupUi() {
        triviaAnswerChoicesAdapter = TriviaAnswerChoicesAdapter { view, isCorrect -> onItemPressed(view, isCorrect) }
        binding.recyclerViewAnswerChoices.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAnswerChoices.adapter = triviaAnswerChoicesAdapter
    }

    private fun onItemPressed(view: View, isCorrect: Boolean) {
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), if (isCorrect) R.color.green else R.color.red))
    }
}