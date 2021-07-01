package com.practice.goodbadhabits.ui.dashboard.pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.goodbadhabits.HabitApplication
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.FragmentPagerBinding
import com.practice.goodbadhabits.databinding.HabitCardItemBinding
import com.practice.goodbadhabits.ui.MainViewModel
import com.practice.goodbadhabits.ui.addition.AdditionFragment
import com.practice.data.utils.LinearSpacingDecoration
import com.practice.goodbadhabits.utils.launchInWhenStarted
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onEach


class PagerFragment : Fragment(R.layout.fragment_pager) {

    companion object {
        const val TYPE = "type"
    }

    private var _binding: FragmentPagerBinding? = null
    private val binding get() = _binding!!

    private var job: Job? = null

    private var cardItemBinding: HabitCardItemBinding? = null

    private val viewModel: MainViewModel by activityViewModels {
        (requireActivity().application as HabitApplication).component.viewModelFactory
    }
    private val adapterHabit by lazy(LazyThreadSafetyMode.NONE) {
        HabitRecyclerAdapter(
            cardItemBinding,
            requireActivity().applicationContext
        )
    }

    private val argHabitType by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getSerializable(TYPE) ?: com.practice.domain.entities.Habit.Type.GOOD
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPagerBinding.bind(view)

        adapterHabit.setOnDoneCheckListener { habitId, button ->
            viewModel.addDoneHabit(habitId, System.currentTimeMillis())
        }

        adapterHabit.setOnEditListener {
            findNavController().navigate(
                R.id.action_dashboardFragment_to_additionFragment,
                Bundle(2).apply {
                    putBoolean(AdditionFragment.IS_EDIT, true)
                    putParcelable(AdditionFragment.HABIT_ARG, it)
                }
            )
        }


        binding.rvHabitList.apply {
            addItemDecoration(LinearSpacingDecoration(0,150))
            adapter = adapterHabit
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }


        job = viewModel.habitList
            .onEach(::handleResult)
            .launchInWhenStarted(lifecycleScope)
    }


    private fun handleResult(result: com.practice.domain.entities.HabitResult) {
        when (result) {
            com.practice.domain.entities.HabitResult.EmptyResult -> {
                adapterHabit.submitList(emptyList())
            }
            is com.practice.domain.entities.HabitResult.ValidResult -> {
                if (argHabitType == com.practice.domain.entities.Habit.Type.GOOD) adapterHabit.submitList(result.good)
                if (argHabitType == com.practice.domain.entities.Habit.Type.BAD) adapterHabit.submitList(result.bad)
            }
            com.practice.domain.entities.HabitResult.EmptySearch -> {}
        }
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        cardItemBinding = null
    }
}
