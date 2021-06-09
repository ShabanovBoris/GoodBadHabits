package com.practice.goodbadhabits.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.FragmentDashboardBinding

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
        binding.toolBar.fabPlus.setOnClickListener {
            findNavController().navigate(
                R.id.action_dashboardFragment_to_additionFragment
            )
        }
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val tableLayout = binding.tableLayout
        val viewPager = binding.viewPager
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
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}