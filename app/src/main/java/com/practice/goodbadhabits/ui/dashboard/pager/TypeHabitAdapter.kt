package com.practice.goodbadhabits.ui.dashboard.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.practice.domain.entities.Habit

class TypeHabitAdapter(fragment: Fragment)
    : FragmentStateAdapter(fragment.childFragmentManager, fragment.viewLifecycleOwner.lifecycle) {


    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = PagerFragment()
        when (position){
            0 -> {
                fragment.arguments =
                    Bundle(1).apply {
                        putSerializable(PagerFragment.TYPE, Habit.Type.GOOD)
                    }
            }
            1 -> {
                fragment.arguments =
                    Bundle(1).apply {
                        putSerializable(PagerFragment.TYPE, Habit.Type.BAD)
                    }
            }

        }
        return fragment
    }
}