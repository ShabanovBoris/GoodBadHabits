package com.practice.goodbadhabits.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.practice.goodbadhabits.data.local.entities.HabitEntity

@Database(entities = [HabitEntity::class], version = 1)
abstract class HabitDataBase: RoomDatabase() {

    abstract fun habitDao(): HabitDao

    companion object{
        @Volatile
        private var INSTANCE: HabitDataBase? = null

        fun getDatabase(appContext: Context): HabitDataBase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext, HabitDataBase::class.java,
                    DbContract.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}