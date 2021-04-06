package com.liuyuheng.handytools.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.liuyuheng.handytools.R
import com.liuyuheng.handytools.databinding.FragmentHomeBinding
import com.liuyuheng.handytools.internal.navigate

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonBillCalculator.setOnClickListener { navigate(R.id.action_homeFragment_to_billCalculatorFragment) }
        binding.buttonTrivia.setOnClickListener { navigate(R.id.action_homeFragment_to_triviaFragment) }
    }
}