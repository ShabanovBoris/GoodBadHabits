package com.practice.goodbadhabits.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.data.repositories.habits.HabitRepositoryImpl
import com.practice.domain.interactors.GetHabitInteractor
import com.practice.domain.repositories.HabitRepository
import kotlinx.coroutines.flow.*

class DashboardViewModel(
   private val getHabitInteractor: GetHabitInteractor
) : ViewModel() {

    /**
     * get type(Bad,Good) of habit that was changed
     */
    val sharedFlow: SharedFlow<Int> by lazy(LazyThreadSafetyMode.NONE) {
            flow { emitAll(getHabitInteractor.getRawHabitCache()) }
                .distinctUntilChanged()
                    //drop initial emit
                .drop(1)
                .map { list ->
                    return@map list.last().type
                }
                .shareIn(viewModelScope, SharingStarted.Lazily, 1)
    }
}