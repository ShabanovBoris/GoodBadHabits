package com.practice.goodbadhabits.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.FragmentDashboardBinding
import com.practice.goodbadhabits.ui.MainScreen
import com.practice.goodbadhabits.ui.ViewModelFactory
import com.practice.goodbadhabits.ui.dashboard.pager.TypeHabitAdapter
import com.practice.goodbadhabits.utils.NightModeHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = checkNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: DashboardViewModel by viewModels { viewModelFactory }

    private var job: Job? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainScreen).mainScreenComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        // set click listener for night mode switch
        NightModeHelper(requireActivity().applicationContext)
            .setUpNightSwitcher(binding.bottomToolBar.bap)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPager()

        // add new habit
        binding.bottomToolBar.fabPlus.setOnClickListener {
            findNavController().navigate(
                R.id.action_dashboardFragment_to_additionFragment
            )
        }

        // filter bottom sheet
        binding.bottomToolBar.bap.setNavigationOnClickListener {
            findNavController().navigate(
                R.id.action_dashboardFragment_to_filterDialog
            )
        }
    }

    private fun setUpViewPager() {
        val tableLayout = binding.tableLayout
        val viewPager = binding.viewPager

        /**
         * set tabs titles and icons fot them
         */
        val names = mapOf(
            0 to com.practice.domain.entities.Habit.Type.GOOD.name,
            1 to com.practice.domain.entities.Habit.Type.BAD.name
        )
        val icons = mapOf(
            0 to R.drawable.ic_good_mood,
            1 to R.drawable.ic_bad_mood
        )


        viewPager.adapter = TypeHabitAdapter(this)

        TabLayoutMediator(tableLayout, viewPager, true) { tab, pos ->
            tab.text = names[pos]
            tab.setIcon(icons[pos] ?: R.drawable.ic_check_no)
        }.attach()


        /**
         *  changes listener, simply shows the badge according to the type
         *  when we adding/editing the habit
         */
        job = viewModel.sharedFlow
            .onEach {
                tableLayout.getTabAt(it)?.apply {
                    orCreateBadge.hasNumber()
                    badge?.badgeGravity = BadgeDrawable.BOTTOM_START
                }
                Log.e("TAG", "get updated type: $it", )
            }
            .launchIn(lifecycleScope)
        //and remove badges
        tableLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.removeBadge()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.removeBadge()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                /***/
            }
        })
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}