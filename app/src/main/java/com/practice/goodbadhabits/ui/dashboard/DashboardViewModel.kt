package com.practice.goodbadhabits.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.domain.interactors.GetHabitInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import org.jetbrains.annotations.TestOnly

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
                .shareIn(viewModelScope, SharingStarted.Lazily, 1)
    }


    fun getSharedFlow(coroutineScope: CoroutineScope = viewModelScope) =
        flow { emitAll(getHabitInteractor.getRawHabitCache()) }
            .distinctUntilChanged()
            //drop initial emit
            .drop(1)
            //new items adding in start of list
            .map { list ->
                return@map list.first().type
            }
            .shareIn(coroutineScope, SharingStarted.Lazily, 1)
    }
