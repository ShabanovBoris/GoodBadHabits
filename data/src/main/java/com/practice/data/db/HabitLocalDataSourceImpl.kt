package com.practice.data.db

import com.practice.data.mappers.HabitEntityMapper
import com.practice.data.utils.logError
import com.practice.domain.entities.Habit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class HabitLocalDataSourceImpl(
    private val dao: HabitDao,
    private val dispatcher: CoroutineDispatcher,
    private val habitEntityMapper: HabitEntityMapper
) : HabitLocalDataSource {

    override suspend fun getHabits(): Flow<List<Habit>> =
        flow { emitAll(dao.getHabits()) }
            .catch { logError(currentCoroutineContext(), it, this::class) }
            .map { habitEntityMapper.toHabitList(it) }
            .flowOn(dispatcher)


    override suspend fun insertHabits(list: List<Habit>) = withContext(dispatcher) {
        dao.insertHabits(habitEntityMapper.toHabitEntityList(list))
    }

    override suspend fun clear() = dao.clear()

    override suspend fun deleteHabit(habitId: String) = withContext(dispatcher) {
       dao.delete(habitId)
    }

}