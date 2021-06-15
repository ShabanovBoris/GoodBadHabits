package com.practice.goodbadhabits.ui.dashboard.pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.goodbadhabits.HabitApplication
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.ui.MainViewModel
import kotlinx.coroutines.flow.*


class PagerFragment : Fragment(R.layout.fragment_pager) {

    companion object{
        const val TYPE = "type"
    }

    private val viewModel: MainViewModel by activityViewModels {
        (requireActivity().application as HabitApplication).component.viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init recycler
        val adapterHabit = HabitRecyclerAdapter(requireActivity().applicationContext)


        view.findViewById<RecyclerView>(R.id.rv_habit_list).apply {
            adapter = adapterHabit
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.habitList
            .onSubscription {
                //init list
                viewModel.getList()
            }
            .onEach { adapterHabit.submitList(it) }
            .launchIn(lifecycleScope)


        val arg =
            arguments?.getSerializable(TYPE)

//        Toast.makeText(requireContext(), (arg as HabitType).name, Toast.LENGTH_SHORT).show()
    }


}