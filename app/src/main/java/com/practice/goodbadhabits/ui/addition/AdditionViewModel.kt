package com.practice.goodbadhabits.ui.addition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.data.utils.logError
import com.practice.domain.entities.Habit
import com.practice.domain.repositories.HabitRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AdditionViewModel(private val repositoryImpl: HabitRepository) : ViewModel() {


    private val handler = CoroutineExceptionHandler(::logError)

    private val _actionStateFlow: MutableStateFlow<ActionState> = MutableStateFlow(ActionState.Empty)
    val actionStateFlow get() = _actionStateFlow.asStateFlow()

    fun addHabit(habit: Habit) =
        viewModelScope.launch(handler) {
            _actionStateFlow.value = ActionState.Loading
            repositoryImpl.uploadHabit(habit)
            repositoryImpl.fetchHabits()
                .onEach {
                    repositoryImpl.insertHabitsCache(it)
                }.launchIn(viewModelScope)
            _actionStateFlow.value = ActionState.Complete
        }

    fun delete(habitId: String) = viewModelScope.launch(handler) {
        _actionStateFlow.value = ActionState.Loading
        repositoryImpl.deleteHabit(habitId)
        repositoryImpl.deleteFromCache(habitId)
        _actionStateFlow.value = ActionState.Complete
    }

    sealed class ActionState{
        object Loading : ActionState()
        object Complete : ActionState()
        object Empty : ActionState()
    }
}