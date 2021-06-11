package com.practice.goodbadhabits.ui.dashboard.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TypeHabitAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int =2

    /**
     * ViewPager on fragment based for reuse in future
     */
    override fun createFragment(position: Int): Fragment {
        val fragment = PagerFragment()
        when (position){
            0 -> {
                fragment.arguments =
                    Bundle(1).apply {
                        putSerializable(PagerFragment.TYPE, PagerFragment.Companion.HabitType.GOOD)
                    }
            }
            1 -> {
                fragment.arguments =
                    Bundle(1).apply {
                        putSerializable(PagerFragment.TYPE, PagerFragment.Companion.HabitType.BAD)
                    }
            }

        }
        return fragment
    }
}