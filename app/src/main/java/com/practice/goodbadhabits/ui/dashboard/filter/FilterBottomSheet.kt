package com.practice.goodbadhabits.ui.dashboard.filter

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.practice.goodbadhabits.HabitApplication
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.FragmentFilterBottomSheetBinding
import com.practice.goodbadhabits.ui.MainViewModel
import com.practice.goodbadhabits.utils.CustomColorPickerMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.util.*

@FlowPreview
class FilterBottomSheet : BottomSheetDialogFragment() {

    private val viewModel: MainViewModel by activityViewModels {
        (requireActivity().application as HabitApplication).component.viewModelFactory
    }

    private var _binding: FragmentFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initColorPicker()
        //every filter appear, search resets
        if (savedInstanceState == null){
            viewModel.onSearchTextChanged("", viewLifecycleOwner)
        }
        //shows habits with isComplete = false
        binding.switchFilterComplete.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isOnlyCompleted = isChecked
            viewModel.onSearchTextChanged( binding.etSearch.editText?.text.toString(),viewLifecycleOwner)
        }

        binding.etSearch.editText?.doAfterTextChanged {
            viewModel.onSearchTextChanged(it.toString(), viewLifecycleOwner)
        }
    }

    private fun initColorPicker() {
        viewModel.colorSearchFilter = null

        binding.bColorButton.setOnClickListener {
            it.visibility = View.GONE
            binding.colorPicker.visibility = View.VISIBLE
        }
        binding.includeColorPicker.layoutColorHolder.forEach {
            it.setOnClickListener {
                binding.bColorButton.apply {
                    viewModel.colorSearchFilter = CustomColorPickerMap().pickedColor(it)
                    setBackgroundColor(
                        requireContext().getColor(
                            viewModel.colorSearchFilter ?: R.color.secondaryColor_600
                        )
                    )
                    visibility = View.VISIBLE
                }
                binding.colorPicker.visibility = View.GONE
                //start search after choosing the color
                viewModel.onSearchTextChanged( binding.etSearch.editText?.text.toString(),viewLifecycleOwner)
            }

        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}