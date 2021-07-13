package com.practice.goodbadhabits.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.domain.interactors.AddEditHabitInteractor
import com.practice.domain.interactors.DeleteHabitInteractor
import com.practice.domain.interactors.GetHabitInteractor
import com.practice.goodbadhabits.di.scopes.PerScreen
import com.practice.goodbadhabits.ui.addition.AdditionViewModel
import com.practice.goodbadhabits.ui.dashboard.DashboardViewModel
import javax.inject.Inject

@PerScreen
class ViewModelFactory @Inject constructor(
    private val addEditHabitInteractor: AddEditHabitInteractor,
    private val deleteHabitInteractor: DeleteHabitInteractor,
    private val getHabitInteractor: GetHabitInteractor
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {

        MainViewModel::class.java -> MainViewModel(
            addEditHabitInteractor,
            deleteHabitInteractor,
            getHabitInteractor
        )

        AdditionViewModel::class.java -> AdditionViewModel(
            addEditHabitInteractor,
            deleteHabitInteractor,
            getHabitInteractor
        )

        DashboardViewModel::class.java -> DashboardViewModel(
            getHabitInteractor
        )


        else -> error("$modelClass is not registered ViewModel")
    } as T
}
