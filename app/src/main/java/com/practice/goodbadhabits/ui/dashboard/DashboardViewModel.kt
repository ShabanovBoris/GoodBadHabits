package com.practice.goodbadhabits.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.data.utils.logError
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
        flow { emitAll(getHabitInteractor.getRawHabitCache()) }
            .distinctUntilChanged()
            //drop initial emit
            .drop(1)
            //new items adding in start of list
            .map { list ->
                return@map list.first().type
            }
            .catch { logError(currentCoroutineContext(), it, this@DashboardViewModel, 26) }
            .shareIn(viewModelScope, SharingStarted.Lazily, 1)
    }


}
