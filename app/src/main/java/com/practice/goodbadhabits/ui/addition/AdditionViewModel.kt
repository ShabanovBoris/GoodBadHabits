package com.practice.goodbadhabits.ui.addition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.data.utils.logError
import com.practice.domain.entities.Habit
import com.practice.domain.interactors.AddEditHabitInteractor
import com.practice.domain.interactors.DeleteHabitInteractor
import com.practice.domain.interactors.GetHabitInteractor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AdditionViewModel(
    private val addEditHabitInteractor: AddEditHabitInteractor,
    private val deleteHabitInteractor: DeleteHabitInteractor,
    private val getHabitInteractor: GetHabitInteractor
) : ViewModel() {


    private val handler = CoroutineExceptionHandler(::logError)

    private val _actionStateFlow: MutableStateFlow<ActionState> =
        MutableStateFlow(ActionState.EMPTY)
    val actionStateFlow get() = _actionStateFlow.asStateFlow()

    fun addHabit(habit: Habit) =
        viewModelScope.launch(handler) {
            _actionStateFlow.value = ActionState.LOADING

            addEditHabitInteractor.uploadHabit(habit)

            getHabitInteractor.getHabits()
                .onEach {
                    addEditHabitInteractor.insertHabitCache(it)
                }.launchIn(viewModelScope)

            _actionStateFlow.value = ActionState.COMPLETE
        }

    fun delete(habitId: String) = viewModelScope.launch(handler) {
        _actionStateFlow.value = ActionState.LOADING
        deleteHabitInteractor.deleteHabit(habitId)
        _actionStateFlow.value = ActionState.COMPLETE
    }

    enum class ActionState {
        LOADING,
        COMPLETE,
        EMPTY
    }
}