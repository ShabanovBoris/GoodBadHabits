package com.practice.goodbadhabits.ui.dashboard.filter

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.practice.goodbadhabits.HabitApplication
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.FragmentFilterBottomSheetBinding
import com.practice.goodbadhabits.ui.ViewModelFactory
import com.practice.goodbadhabits.ui.MainViewModel
import com.practice.goodbadhabits.utils.ColorPickerMap
import javax.inject.Inject


class FilterBottomSheet : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MainViewModel by activityViewModels { viewModelFactory }

    private var _binding: FragmentFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as HabitApplication).appComponent.inject(this)
    }

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
            viewModel.onSearchTextChanged("")
        }
        //shows habits with isComplete = false
        binding.switchFilterComplete.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onlyNotCompleted = isChecked
            viewModel.onSearchTextChanged( binding.etSearch.editText?.text.toString())
        }

        binding.etSearch.editText?.doAfterTextChanged {
            viewModel.onSearchTextChanged(it.toString())
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
                    viewModel.colorSearchFilter = ColorPickerMap().pickedColor(it)
                    setBackgroundColor(
                        requireContext().getColor(
                            viewModel.colorSearchFilter ?: R.color.secondaryColor_600
                        )
                    )
                    visibility = View.VISIBLE
                }
                binding.colorPicker.visibility = View.GONE
                //start searching after choosing the color
                viewModel.onSearchTextChanged( binding.etSearch.editText?.text.toString())
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}