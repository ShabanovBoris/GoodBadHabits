package com.practice.goodbadhabits.ui.dashboard.filter

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.FragmentFilterBottomSheetBinding
import java.util.*


/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    FilterBottomSheet.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class FilterBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterBottomSheetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.design_bottom_sheet)

    }





    companion object {
        const val ARG_FILTER_DIALOG = "argument_for_bottomsheet"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}