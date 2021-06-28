package com.practice.goodbadhabits.data.local

import androidx.room.*
import com.practice.goodbadhabits.data.local.entities.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit_table ORDER BY date ASC")
    fun getHabits(): Flow<List<HabitEntity>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabits(list: List<HabitEntity>)

    @Query("DELETE FROM habit_table")
    suspend fun clear()

    @Query("DELETE FROM habit_table WHERE habit_table_id = :id")
    suspend fun delete(id: String)
}