package com.practice.goodbadhabits.ui.dashboard.pager

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.data.utils.LinearSpacingDecoration
import com.practice.domain.common.HabitResult
import com.practice.domain.entities.Habit
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.FragmentPagerBinding
import com.practice.goodbadhabits.databinding.HabitCardItemBinding
import com.practice.goodbadhabits.ui.MainScreen
import com.practice.goodbadhabits.ui.MainViewModel
import com.practice.goodbadhabits.ui.ViewModelFactory
import com.practice.goodbadhabits.ui.addition.AdditionFragment
import com.practice.goodbadhabits.utils.launchInWhenStarted
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.produceIn
import javax.inject.Inject


class PagerFragment : Fragment(R.layout.fragment_pager) {

    companion object {
        const val TYPE = "type"
    }

    private var _binding: FragmentPagerBinding? = null
    private val binding get() = checkNotNull(_binding)

    private var job: Job? = null

    private var cardItemBinding: HabitCardItemBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MainViewModel by activityViewModels { viewModelFactory }

    private var adapterHabit: HabitRecyclerAdapter? = null

    private val argHabitType by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getSerializable(TYPE) ?: Habit.Type.GOOD
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainScreen).mainScreenComponent
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPagerBinding.bind(view)

        adapterHabit = HabitRecyclerAdapter(
            cardItemBinding,
            requireActivity().applicationContext
        ).apply {
            setOnDoneCheckListener { habit, _ ->
                viewModel.addDoneHabit(habit, System.currentTimeMillis())
            }
            setOnEditListener {
                findNavController().navigate(
                    R.id.action_dashboardFragment_to_additionFragment,
                    Bundle(2).apply {
                        putBoolean(AdditionFragment.IS_EDIT, true)
                        putParcelable(AdditionFragment.HABIT_ARG, it)
                    }
                )
            }
        }

        binding.rvHabitList.apply {
            addItemDecoration(LinearSpacingDecoration(0, 150))
            adapter = adapterHabit
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        job = viewModel.habitList
            .onEach(::handleResult)
            .launchInWhenStarted(lifecycleScope)
    }


    private fun handleResult(result: HabitResult) {
        val adapter = requireNotNull(adapterHabit)
        when (result) {
            HabitResult.EmptyResult -> {
                adapter.submitList(emptyList())
            }
            is HabitResult.ValidResult -> {

                val oldListSize = adapter.itemCount

                if (argHabitType == Habit.Type.GOOD) adapter.submitList(result.good) {
                    //scroll up when list have new item
                    result.good
                        ?.let {
                            if (it.size > oldListSize) binding.rvHabitList.scrollToPosition(0)
                        }
                }
                if (argHabitType == Habit.Type.BAD) adapter.submitList(result.bad) {
                    result.bad
                        ?.let {
                            if (it.size > oldListSize) binding.rvHabitList.scrollToPosition(0)
                        }
                }
            }
            HabitResult.EmptySearch -> {
            }
        }
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapterHabit = null
        binding.rvHabitList.adapter = null
        cardItemBinding = null
    }
}
