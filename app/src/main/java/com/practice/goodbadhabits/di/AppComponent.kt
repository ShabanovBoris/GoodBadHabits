package com.practice.goodbadhabits.di

import android.content.Context
import com.practice.goodbadhabits.ui.MainActivity
import com.practice.goodbadhabits.ui.addition.AdditionFragment
import com.practice.goodbadhabits.ui.dashboard.DashboardFragment
import com.practice.goodbadhabits.ui.dashboard.filter.FilterBottomSheet
import com.practice.goodbadhabits.ui.dashboard.pager.PagerFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class, DataModule::class, DispatcherModule::class, RoomModule::class,
        InteractorModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: AdditionFragment)
    fun inject(fragment: DashboardFragment)
    fun inject(fragment: FilterBottomSheet)
    fun inject(fragment: PagerFragment)
}