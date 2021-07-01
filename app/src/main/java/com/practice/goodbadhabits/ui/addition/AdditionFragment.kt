package com.practice.goodbadhabits.ui.addition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.requireViewById
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.practice.goodbadhabits.HabitApplication
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.FragmentAdditionBinding
import com.practice.goodbadhabits.utils.ColorPickerMap
import com.practice.goodbadhabits.utils.validateFields
import java.util.*

class AdditionFragment : Fragment() {
    private var _binding: FragmentAdditionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdditionViewModel by viewModels {
        (requireActivity().application as HabitApplication).component
            .viewModelFactory
    }
    private val isEdit by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getBoolean(IS_EDIT, false) == true
    }
    private val habitArgument by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable<com.practice.domain.entities.Habit>(HABIT_ARG)
    }
    var checkedColor: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (isEdit){
            (requireActivity() as AppCompatActivity).supportActionBar?.setTitle(R.string.editHabit)
        }
        _binding = FragmentAdditionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        val colorButton = binding.bColorButton
        val title = binding.tvTitle
        val description = binding.etDescription
        val type = binding.rgType
        val frequency = binding.etEvery
        val countRepeat = binding.etRepeat
        val priority = binding.sPriorityLayout
        //spinner
        initSpinner()
        //color picker
        initColorPicker()
        //if edit mode enabled, fill in the fields with habit data
        if (isEdit) {
            val habit = requireNotNull(habitArgument)

            title.editText?.text?.append(habit.title)
            description.editText?.text?.append(habit.description)
            type.check(type[habit.type].id)
            frequency.editText?.text?.append(habit.repeat.toString())
            countRepeat.editText?.text?.append(habit.count.toString())
            binding.priorityDropdown.setText(com.practice.domain.entities.Habit.Priority.values()[habit.priority].toString(), false)
            checkedColor = habit.colorId
            colorButton.setBackgroundColor(requireContext().getColor(requireNotNull(checkedColor)))
            //and set listener to the delete button
            binding.bDelete.setOnClickListener {
                viewModel.delete(habit.id)
                Toast.makeText(requireContext(), "Delete complete", Toast.LENGTH_SHORT).show()
            }
        }
        //submit handler
        binding.bSubmit.setOnClickListener {
            if (validateFields(binding)) {
                val habit = com.practice.domain.entities.Habit(
                    title = title.editText?.text.toString(),
                    colorId = requireNotNull(checkedColor),
                    repeat = frequency.editText?.text.toString().toInt(),
                    isCompleted = false,
                    date = Date().time.toInt(),
                    id = if (isEdit) habitArgument?.id.toString() else "",
                    doneDates = emptyList(),
                    count = countRepeat.editText?.text.toString().toInt(),
                    description = description.editText?.text.toString(),
                    priority = com.practice.domain.entities.Habit.Priority.valueOf(priority.editText?.text.toString()).ordinal,
                    type = com.practice.domain.entities.Habit.Type.valueOf(
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

    private fun initColorPicker() {
        binding.bColorButton.setOnClickListener {
            it.visibility = View.GONE
            binding.frameColorPicker.visibility = View.VISIBLE
        }
        binding.includeColorPicker.layoutColorHolder.forEach {
            it.setOnClickListener {
                binding.bColorButton.apply {
                    checkedColor = ColorPickerMap().pickedColor(it)
                    setBackgroundColor(
                        requireContext().getColor(
                            checkedColor ?: R.color.secondaryColor_600
                        )
                    )
                    visibility = View.VISIBLE
                }
                binding.frameColorPicker.visibility = View.GONE
            }
        }
    }

    private fun initSpinner() {
        val priorityList = listOf(
            com.practice.domain.entities.Habit.Priority.LOW.name,
            com.practice.domain.entities.Habit.Priority.MEDIUM.name,
            com.practice.domain.entities.Habit.Priority.HIGH.name
        )
        binding.priorityDropdown.apply {
            setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.priority_dropdown_item,
                    priorityList
                )
            )
            //Priority.HIGH by default
            if(!isEdit) setText(com.practice.domain.entities.Habit.Priority.HIGH.name, false)
        }
    }


    override fun onStart() {
        super.onStart()
        if (isEdit) {
            binding.bDelete.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val IS_EDIT = "is_edit"
        const val HABIT_ARG = "habit_argument"
    }


}