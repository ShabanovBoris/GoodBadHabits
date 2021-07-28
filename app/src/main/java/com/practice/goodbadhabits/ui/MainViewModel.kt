package com.practice.goodbadhabits.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.data.utils.logError
import com.practice.domain.common.HabitResult
import com.practice.domain.entities.Habit
import com.practice.domain.interactors.AddEditHabitInteractor
import com.practice.domain.interactors.DeleteHabitInteractor
import com.practice.domain.interactors.GetHabitInteractor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val addEditHabitInteractor: AddEditHabitInteractor,
    private val deleteHabitInteractor: DeleteHabitInteractor,
    private val getHabitInteractor: GetHabitInteractor
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler(::logError)

    private val _habitList =
        MutableSharedFlow<HabitResult>(1, 0, BufferOverflow.DROP_OLDEST)
    val habitList get() = _habitList.asSharedFlow()

    // region search parameters
    var mOnlyNotCompleted = false
    var mColorSearchFilter: Int? = null
    var mSearchByTitle = ""
    // endregion

    init {
        initList()
    }

    /**
     * get data from db for fast loading elements and simultaneously fetch data from network
     */
    private fun initList() = viewModelScope.launch(exceptionHandler) {
        launch {
            getHabitCache()
        }
        launch {
            getHabitInteractor.getHabits()
                .collect {
                    deleteHabitInteractor.clearCache()
                    addEditHabitInteractor.insertHabitCache(it)
                }
        }
    }

    private suspend fun getHabitCache() = viewModelScope.launch(exceptionHandler) {
        getHabitInteractor.getHabitCache()
            .combine(filter(mSearchByTitle)) { origin, afterFilter ->
                if (afterFilter is HabitResult.ValidResult || afterFilter is HabitResult.EmptyResult) {
                    return@combine afterFilter
                } else origin
            }.collect {
                _habitList.emit(it)
            }
    }

    fun search(
        searchByTitle: String = mSearchByTitle,
        onlyNotCompleted: Boolean = mOnlyNotCompleted,
        colorSearchFilter: Int? = mColorSearchFilter
    ) = viewModelScope.launch(exceptionHandler) {
        mOnlyNotCompleted = onlyNotCompleted
        mColorSearchFilter = colorSearchFilter
        mSearchByTitle = searchByTitle
        getHabitCache()
    }

    fun clearFilter() =
        viewModelScope.launch(exceptionHandler) {
            mOnlyNotCompleted = false
            mColorSearchFilter = null
            mSearchByTitle = ""
            getHabitCache()
        }

    fun clearData() = viewModelScope.launch(exceptionHandler) {
        deleteHabitInteractor.clearData()
    }

    fun addDoneHabit(habit: Habit, date: Long) = viewModelScope.launch(exceptionHandler) {
        addEditHabitInteractor.setHabitDone(habit, date)
        initList()
    }

    private suspend fun filter(habitTitle: String): Flow<HabitResult> = getHabitInteractor.getHabitCache()
        .onEach { mSearchByTitle = habitTitle }
        .map { habitResult ->
            if (habitResult is HabitResult.ValidResult) {
                if (mSearchByTitle.isEmpty() && mColorSearchFilter == null && !mOnlyNotCompleted)
                    return@map HabitResult.EmptySearch

                var newHabitResult =
                    if (mSearchByTitle.isNotEmpty()) HabitResult.ValidResult(
                        good = habitResult.good?.filter { habit ->
                            habit.title.contains(mSearchByTitle, true)
                        },
                        bad = habitResult.bad?.filter { habit ->
                            habit.title.contains(mSearchByTitle, true)
                        }
                    ) else habitResult

                if (mOnlyNotCompleted) newHabitResult = newHabitResult.copy(
                    good = newHabitResult.good?.filter { habit -> !habit.isCompleted },
                    bad = newHabitResult.bad?.filter { habit -> !habit.isCompleted }
                )

                if (mColorSearchFilter != null) newHabitResult = newHabitResult.copy(
                    good = newHabitResult.good?.filter { habit -> habit.colorId == mColorSearchFilter },
                    bad = newHabitResult.bad?.filter { habit -> habit.colorId == mColorSearchFilter }
                )

                if (newHabitResult == HabitResult.ValidResult(emptyList(), emptyList()))
                    return@map HabitResult.EmptyResult
                else newHabitResult

            } else habitResult
        }
}





