package com.practice.goodbadhabits.ui.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.goodbadhabits.data.HabitRepository
import com.practice.goodbadhabits.entities.Habit
import kotlinx.coroutines.flow.*

class DashboardViewModel(
   private val repository: HabitRepository
) : ViewModel() {

    /**
     * get changes for show badges in tab layout when DB updates
     */
    val sharedFlow: SharedFlow<Int> by lazy(LazyThreadSafetyMode.NONE) {
            flow { emitAll(repository.getHabitsCache()) }
                .distinctUntilChanged()
                .drop(1)
                .map { list ->
                    Log.e("asd", "getCacheChanges: ${list.last().type} ", )
                    return@map list.last().type
                }
                .shareIn(viewModelScope, SharingStarted.Lazily, 1)
    }



}