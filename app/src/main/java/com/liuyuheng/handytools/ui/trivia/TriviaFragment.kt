package com.liuyuheng.handytools.ui.trivia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.liuyuheng.handytools.databinding.FragmentTriviaBinding
import com.liuyuheng.handytools.repository.TriviaDifficulty
import com.liuyuheng.handytools.repository.TriviaType
import org.koin.android.viewmodel.ext.android.viewModel

class TriviaFragment: Fragment() {

    private lateinit var binding: FragmentTriviaBinding
    private val triviaViewModel: TriviaViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTriviaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setupObservers()
        setupListeners()
    }

    private fun setupUi() {
        val amountAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listOf(1..50))
        val difficultyAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, TriviaDifficulty.values().map { it.text })
        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, TriviaType.values().map { it.text })
        (binding.textInputLayoutAmountDropdown.editText as? AutoCompleteTextView)?.setAdapter(amountAdapter)
        (binding.textInputLayoutDifficultyDropdown.editText as? AutoCompleteTextView)?.setAdapter(difficultyAdapter)
        (binding.textInputLayoutTypeDropdown.editText as? AutoCompleteTextView)?.setAdapter(typeAdapter)
    }

    private fun setupObservers() {
        triviaViewModel.getTriviaCategory().observe(viewLifecycleOwner) { triviaCategoryList ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, triviaCategoryList.map { it.name })
            (binding.textInputLayoutCategoryDropdown.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }
    }

    private fun setupListeners() {
        binding.buttonGetQuestions.setOnClickListener {
            val category = binding.textInputLayoutCategoryDropdown.editText?.text.toString()
            val amount = binding.textInputLayoutAmountDropdown.editText?.text.toString().toInt()
            val difficulty = TriviaDifficulty.valueOf(binding.textInputLayoutDifficultyDropdown.editText?.text.toString())
            val type = TriviaType.valueOf(binding.textInputLayoutTypeDropdown.editText?.text.toString())

            triviaViewModel.getCategoryQuestions(category, amount, difficulty, type).observe(viewLifecycleOwner) { triviaQuestions ->
                Log.d("myDebug", "$triviaQuestions")
            }
        }
    }
}