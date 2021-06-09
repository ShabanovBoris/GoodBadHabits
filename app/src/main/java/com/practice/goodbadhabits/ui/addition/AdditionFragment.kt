package com.practice.goodbadhabits.ui.addition

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.practice.goodbadhabits.databinding.FragmentAdditionBinding

class AdditionFragment : Fragment() {
    private var _binding: FragmentAdditionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: AdditionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdditionBinding.inflate(layoutInflater)

//        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
//            title = "Add habit"
//            setHomeAsUpIndicator(android.R.drawable.ic_media_rew)
//        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdditionViewModel::class.java)
        // TODO: Use the ViewModel
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}