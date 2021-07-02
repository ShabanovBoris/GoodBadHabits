package com.practice.goodbadhabits.di

import android.content.Context
import androidx.room.Room
import com.practice.data.db.DbContract
import com.practice.data.db.HabitDao
import com.practice.data.db.HabitDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideHabitDataBase(appContext: Context): HabitDataBase =
        Room.databaseBuilder(
            appContext,
            HabitDataBase::class.java,
            DbContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideHabitDao(habitDataBase: HabitDataBase):HabitDao =
        habitDataBase.habitDao()
}