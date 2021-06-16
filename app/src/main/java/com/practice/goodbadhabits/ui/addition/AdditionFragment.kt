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

class AdditionFragment : Fragment() {
    private var _binding: FragmentAdditionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdditionViewModel by viewModels {
        (requireActivity().application as HabitApplication).component
            .viewModelFactory
    }

    private lateinit var title: TextInputLayout
    private lateinit var description: TextInputLayout
    private lateinit var type: RadioGroup
    private lateinit var countRepeat: TextInputLayout
    private lateinit var frequency: TextInputLayout
    private lateinit var priority: TextInputLayout
    private lateinit var colorPickerView: FrameLayout
    private lateinit var colorButton: MaterialButton

    private var checkedColor: Int? = null

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
        colorButton = binding.bColorButton
        colorPickerView = binding.frameColorPicker
        title = binding.tvTitle
        description = binding.etDescription
        type = binding.rgType
        frequency = binding.etEvery
        countRepeat = binding.etRepeat
        priority = binding.sPriorityLayout
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
        }



        binding.bColorButton.setOnClickListener {
            it.visibility = View.GONE
            binding.frameColorPicker.visibility = View.VISIBLE
        }


        requireViewById<LinearLayout>(view, R.id.layout_color_holder).forEach {
            it.setOnClickListener {
                binding.bColorButton.apply {
                    checkedColor = CustomColorPicker(it).pickedColor()
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

        binding.bSubmit.setOnClickListener {
            val habit = Habit(
                title.editText?.text.toString(),
                requireNotNull(checkedColor),
                countRepeat.editText?.text.toString().toInt(),
                false,
                260897,//TODO
                "",
                emptyList(),
                countRepeat.editText?.text.toString().toInt(),
                description.editText?.text.toString(),
                Habit.Priority.valueOf(priority.editText?.text.toString()).ordinal,
                Habit.Type.valueOf(requireViewById<RadioButton>(type,type.checkedRadioButtonId).text.toString().uppercase()).ordinal
            )
            viewModel.addHabit(habit)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}