package com.practice.goodbadhabits.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TypeHabitAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int =2

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