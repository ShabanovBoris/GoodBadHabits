package com.practice.goodbadhabits.ui.addition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.practice.goodbadhabits.HabitApplication
import com.practice.goodbadhabits.databinding.FragmentAdditionBinding

class AdditionFragment : Fragment() {
    private var _binding: FragmentAdditionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: AdditionViewModel by viewModels{
        (requireActivity().application as HabitApplication).component
            .viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdditionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}