package com.practice.goodbadhabits.ui.addition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.ViewCompat.requireViewById
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.practice.goodbadhabits.HabitApplication
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.FragmentAdditionBinding
import com.practice.goodbadhabits.entities.Habit
import com.practice.goodbadhabits.utils.CustomColorPicker
import com.practice.goodbadhabits.utils.validateFields
import java.util.*

class AdditionFragment : Fragment() {
    private var _binding: FragmentAdditionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdditionViewModel by viewModels {
        (requireActivity().application as HabitApplication).component
            .viewModelFactory
    }


     var checkedColor: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdditionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
       val colorButton = binding.bColorButton
       val colorPickerView = binding.frameColorPicker
       val title = binding.tvTitle
       val description = binding.etDescription
       val type = binding.rgType
       val frequency = binding.etEvery
       val countRepeat = binding.etRepeat
       val priority = binding.sPriorityLayout
        //init spinner
        val priorityList = listOf(
            Habit.Priority.LOW.name,
            Habit.Priority.MEDIUM.name,
            Habit.Priority.HIGH.name
        )
        binding.priorityDropdown.apply {
            setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.priority_dropdown_item,
                    priorityList
                )
            )
            setText(Habit.Priority.HIGH.name, false)
        }
        //color picker
        colorButton.setOnClickListener {
            it.visibility = View.GONE
            binding.frameColorPicker.visibility = View.VISIBLE
        }
        binding.includeColorPicker.layoutColorHolder.forEach {
            it.setOnClickListener {
                colorButton.apply {
                    checkedColor = CustomColorPicker(it).pickedColor()
                    setBackgroundColor(
                        requireContext().getColor(
                            checkedColor ?: R.color.secondaryColor_600
                        )
                    )
                    visibility = View.VISIBLE
                }
                colorPickerView.visibility = View.GONE
            }
        }

        //TODO - validate values
        binding.bSubmit.setOnClickListener {
            if(validateFields(binding)) {
                val habit = Habit(
                    title = title.editText?.text.toString(),
                    colorId = requireNotNull(checkedColor),
                    repeat = frequency.editText?.text.toString().toInt(),
                    isCompleted = false,
                    date = Date().time.toInt(),
                    id = "",
                    doneDates = listOf(Date().time.toInt()),
                    count = countRepeat.editText?.text.toString().toInt(),
                    description = description.editText?.text.toString(),
                    priority = Habit.Priority.valueOf(priority.editText?.text.toString()).ordinal,
                    type = Habit.Type.valueOf(
                        requireViewById<RadioButton>(
                            type,
                            type.checkedRadioButtonId
                        ).text.toString().uppercase()
                    ).ordinal
                )
                viewModel.addHabit(habit)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}