package com.practice.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.practice.data.dto.HabitEntity

@Database(entities = [HabitEntity::class], version = 1)
abstract class HabitDataBase: RoomDatabase() {

    abstract fun habitDao(): HabitDao

}