package com.practice.goodbadhabits.ui

import com.practice.goodbadhabits.di.DataModule
import com.practice.goodbadhabits.di.HabitApiModule
import com.practice.goodbadhabits.di.InteractorModule
import com.practice.goodbadhabits.di.RoomModule
import com.practice.goodbadhabits.di.scopes.PerScreen
import com.practice.goodbadhabits.ui.addition.AdditionFragment
import com.practice.goodbadhabits.ui.dashboard.DashboardFragment
import com.practice.goodbadhabits.ui.dashboard.filter.FilterBottomSheet
import com.practice.goodbadhabits.ui.dashboard.pager.PagerFragment
import dagger.Subcomponent

@PerScreen
@Subcomponent(modules = [InteractorModule::class, HabitApiModule::class, DataModule::class, RoomModule::class])
interface MainScreenComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): MainScreenComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: AdditionFragment)
    fun inject(fragment: DashboardFragment)
    fun inject(fragment: FilterBottomSheet)
    fun inject(fragment: PagerFragment)
}