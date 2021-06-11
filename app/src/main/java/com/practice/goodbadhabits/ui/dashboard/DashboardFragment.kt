package com.practice.goodbadhabits.ui.dashboard

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.FragmentDashboardBinding
import com.practice.goodbadhabits.ui.dashboard.filter.FilterBottomSheet
import com.practice.goodbadhabits.ui.dashboard.pager.PagerFragment
import com.practice.goodbadhabits.ui.dashboard.pager.TypeHabitAdapter
import com.practice.goodbadhabits.utils.NightDayModeHelper

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // night mode switcher
        NightDayModeHelper(requireActivity(), binding.toolBar.bap)
            .nightModePreference()


        setUpViewPager()

        // add new habit
        binding.toolBar.fabPlus.setOnClickListener {
//            FragmentNavigatorExtras() shared element with navigate
            findNavController().navigate(
                R.id.action_dashboardFragment_to_additionFragment
            )
        }

        // filter bottom sheet
        binding.toolBar.bap.setNavigationOnClickListener {
            findNavController().navigate(
                R.id.action_dashboardFragment_to_filterDialog,
                Bundle(1).apply {
                    putInt(FilterBottomSheet.ARG_FILTER_DIALOG,30)
                })
        }
    }



    private fun setUpViewPager() {
        val tableLayout = binding.tableLayout
        val viewPager = binding.viewPager

        /**
         * set tabs titles and icons fot them
         */
        val names = mapOf(
            0 to PagerFragment.Companion.HabitType.GOOD.name,
            1 to PagerFragment.Companion.HabitType.BAD.name
        )
        val icons = mapOf(
            0 to R.drawable.ic_good_mood,
            1 to R.drawable.ic_bad_mood
        )


        viewPager.adapter = TypeHabitAdapter(this)
        TabLayoutMediator(tableLayout, viewPager) { tab, pos ->
            tab.text = names[pos]
            tab.setIcon(icons[pos] ?: R.drawable.ic_check_no)
            //check this
            tab.orCreateBadge.number = 1
            tab.badge?.badgeGravity = BadgeDrawable.BOTTOM_START
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}