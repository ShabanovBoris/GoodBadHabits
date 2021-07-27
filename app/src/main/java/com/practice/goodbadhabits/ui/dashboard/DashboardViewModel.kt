package com.practice.goodbadhabits.ui.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.data.utils.logError
import com.practice.domain.common.HabitResult
import com.practice.domain.entities.Habit
import com.practice.domain.interactors.GetHabitInteractor
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.*

class DashboardViewModel(
    private val getHabitInteractor: GetHabitInteractor
) : ViewModel() {

    /**
     * get type(Bad,Good) of habit that was changed
     */
    val sharedFlow: SharedFlow<Int> by lazy(LazyThreadSafetyMode.NONE) {
        flow { emitAll(getHabitInteractor.getHabitCache()) }
            .distinctUntilChanged()
            //drop initial emit
            .drop(1)
            .map {
               return@map when(it){
                    is HabitResult.ValidResult -> {
                        (it.good.orEmpty() + it.bad.orEmpty()).run {
                            maxByOrNull { listItem -> listItem.createDate }?.type ?: INVALID_VALUE
                        }
                    }
                    else -> { INVALID_VALUE }
                }
            }
            .catch { logError(currentCoroutineContext(), it, this@DashboardViewModel, 26) }
            .shareIn(viewModelScope, SharingStarted.Lazily, 0)
    }


   private companion object{
        const val INVALID_VALUE = -1
    }
}
