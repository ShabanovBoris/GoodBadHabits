package com.practice.goodbadhabits.ui.dashboard.pager

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.goodbadhabits.HabitApplication
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.entities.Habit
import com.practice.goodbadhabits.entities.HabitResult
import com.practice.goodbadhabits.ui.MainViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onSubscription


class PagerFragment : Fragment(R.layout.fragment_pager) {

    companion object {
        const val TYPE = "type"
    }

    private val viewModel: MainViewModel by activityViewModels {
        (requireActivity().application as HabitApplication).component.viewModelFactory
    }
    private val adapterHabit by lazy(LazyThreadSafetyMode.NONE) {
        HabitRecyclerAdapter(requireActivity().applicationContext)
    }

    private val arg by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getSerializable(TYPE) ?: Habit.Type.GOOD
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        view.findViewById<RecyclerView>(R.id.rv_habit_list).apply {
            adapter = adapterHabit
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }


        Log.e("TAG", "onViewCreated", )
        viewModel.habitList
            .onEach(::handleResult)
            .launchIn(lifecycleScope)


//        val arg = arguments?.getSerializable(TYPE)
//        if (arg == Habit.Type.BAD) {
//            adapterHabit.submitList(viewModel.habitList.replayCache[0])
//        }

    }


    private fun handleResult(result: HabitResult){
        when(result){
            HabitResult.EmptyResult -> {}
            is HabitResult.ValidResult -> {
                if (arg == Habit.Type.GOOD) adapterHabit.submitList(result.good)
                if (arg == Habit.Type.BAD) adapterHabit.submitList(result.bad)
            }
        }
    }



}