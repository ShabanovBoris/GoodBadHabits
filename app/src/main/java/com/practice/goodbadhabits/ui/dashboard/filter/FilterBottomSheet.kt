package com.practice.goodbadhabits.ui.dashboard.filter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.FragmentFilterBottomSheetBinding
import com.practice.goodbadhabits.ui.MainActivity
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
        (requireActivity() as MainActivity).mainScreenComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initColorPicker()

        //shows habits with isComplete = false
        binding.switchFilterComplete.isChecked = viewModel.mOnlyNotCompleted
        binding.switchFilterComplete.setOnCheckedChangeListener { _, isChecked ->
            viewModel.search( binding.etSearch.editText?.text.toString(), isChecked)
        }

        binding.etSearch.editText?.text?.append(viewModel.mSearchByTitle)
        binding.etSearch.editText?.doAfterTextChanged {
            viewModel.search(it.toString())
        }

        binding.ibCancelFilter.setOnClickListener {
            viewModel.clearFilter()
            this.dismiss()
        }
    }

    private fun initColorPicker() {
        binding.bColorButton.setBackgroundColor(requireContext().getColor(viewModel.mColorSearchFilter
            ?:R.color.material_on_background_disabled ))

        binding.bColorButton.setOnClickListener {
            it.visibility = View.GONE
            binding.colorPicker.visibility = View.VISIBLE
        }
        binding.includeColorPicker.layoutColorHolder.forEach {
            it.setOnClickListener {
                binding.bColorButton.apply {
                    viewModel.search(
                        searchByTitle = binding.etSearch.editText?.text.toString(),
                        colorSearchFilter = ColorPickerMap().pickedColor(it)
                    )
                    setBackgroundColor(
                        requireContext().getColor(
                            viewModel.mColorSearchFilter ?: R.color.material_on_background_disabled
                        )
                    )
                    visibility = View.VISIBLE
                }
                binding.colorPicker.visibility = View.GONE
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}