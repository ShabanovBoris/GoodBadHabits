package com.practice.goodbadhabits.ui.addition

import android.content.Context
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.practice.data.utils.toISOFormat
import com.practice.domain.entities.Habit
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.FragmentAdditionBinding
import com.practice.goodbadhabits.ui.MainScreen
import com.practice.goodbadhabits.ui.ViewModelFactory
import com.practice.goodbadhabits.utils.ColorPickerMap
import com.practice.goodbadhabits.utils.validateFields
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

class AdditionFragment : Fragment() {
    private var _binding: FragmentAdditionBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: AdditionViewModel by viewModels { viewModelFactory }

    private val isEdit by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getBoolean(IS_EDIT, false) == true
    }
    private val habitArgument by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable<Habit>(HABIT_ARG)
    }

    //color that picked after click on colorPicker
    var checkedColor: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainScreen).mainScreenComponent.inject(this)
    }

    override fun onStart() {
        super.onStart()
        if (isEdit) {
            binding.bDelete.visibility = View.VISIBLE
            binding.tvCreateDate.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (isEdit) {
            (requireActivity() as AppCompatActivity).supportActionBar?.setTitle(R.string.editHabit)
        }
        _binding = FragmentAdditionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        initColorPicker()
        //if edit mode enabled, fill in the fields with habit data
        if (isEdit) initFields()
        //submit handler
        setSubmitListener()
        //handle add/edit/delete state as ActonState
        viewModel.actionStateFlow
            .onEach(::actionStateHandle)
            .launchIn(lifecycleScope)
    }

    private fun setSubmitListener() {
        binding.bSubmit.setOnClickListener {
            if (validateFields(binding)) {
                binding.apply {
                    val habit = Habit(
                        title = tvTitle.editText?.text.toString(),
                        colorId = requireNotNull(checkedColor),
                        repeatDays = etEvery.editText?.text.toString().toInt(),
                        isCompleted = false,
                        //set date of creating habit
                        createDate = Date().time,
                        id = if (isEdit) habitArgument?.id.toString() else "",
                        doneDates = emptyList(),
                        count = etRepeat.editText?.text.toString().toInt(),
                        description = etDescription.editText?.text.toString(),
                        priority = Habit.Priority.valueOf(sPriorityLayout.editText?.text.toString()).ordinal,
                        type = Habit.Type.valueOf(
                            requireViewById<RadioButton>(
                                rgType,
                                rgType.checkedRadioButtonId
                            ).text.toString().uppercase()
                        ).ordinal
                    )
                    viewModel.addHabit(habit)
                }
            }
        }
    }

    private fun initFields() {
        val habit = requireNotNull(habitArgument)
        binding.apply {
            tvTitle.editText?.text?.append(habit.title)
            etDescription.editText?.text?.append(habit.description)
            rgType.check(rgType[habit.type].id)
            etEvery.editText?.text?.append(habit.repeatDays.toString())
            etRepeat.editText?.text?.append(habit.count.toString())
            binding.priorityDropdown.setText(
                Habit.Priority.values()[habit.priority].toString(),
                false
            )
            checkedColor = habit.colorId
            tvCreateDate.append(" ${Date(habit.createDate).toISOFormat()}")
            bColorButton.setBackgroundColor(requireContext().getColor(requireNotNull(checkedColor)))
            //and set listener to the delete button
            binding.bDelete.setOnClickListener {
                viewModel.delete(habit.id)
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
            //Priority.HIGH by default
            if (!isEdit) setText(Habit.Priority.HIGH.name, false)
        }
    }

    private fun actionStateHandle(actionState: AdditionViewModel.ActionState) {
        when (actionState) {
            AdditionViewModel.ActionState.COMPLETE -> findNavController().navigateUp()

            AdditionViewModel.ActionState.EMPTY -> {
            }

            AdditionViewModel.ActionState.LOADING ->
                Toast.makeText(requireContext(), "In process...", Toast.LENGTH_SHORT)
                    .show()
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