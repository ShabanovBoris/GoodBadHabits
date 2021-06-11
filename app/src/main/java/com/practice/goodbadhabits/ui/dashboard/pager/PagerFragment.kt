package com.practice.goodbadhabits.ui.dashboard.pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.goodbadhabits.HabitApplication
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.ui.MainViewModel


class PagerFragment : Fragment(R.layout.fragment_pager) {

    companion object{
        enum class HabitType{
            GOOD,
            BAD
        }
        const val TYPE = "type"
    }

    private val viewModel: MainViewModel by activityViewModels {
        (requireActivity().application as HabitApplication).component.viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterHabit = HabitRecyclerAdapter(requireActivity().applicationContext)

        view.findViewById<RecyclerView>(R.id.rv_habit_list).apply {
            adapter = adapterHabit
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        adapterHabit.submitList(viewModel.getList())

        val arg =
            arguments?.getSerializable(TYPE)

//        Toast.makeText(requireContext(), (arg as HabitType).name, Toast.LENGTH_SHORT).show()
    }


}