package com.practice.goodbadhabits.di

import android.content.Context
import androidx.room.Room
import com.practice.data.db.DbContract
import com.practice.data.db.HabitDao
import com.practice.data.db.HabitDataBase
import com.practice.goodbadhabits.di.scopes.PerScreen
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @PerScreen
    @Provides
    fun provideHabitDataBase(appContext: Context): HabitDataBase =
        Room.databaseBuilder(
            appContext,
            HabitDataBase::class.java,
            DbContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @PerScreen
    @Provides
    fun provideHabitDao(habitDataBase: HabitDataBase):HabitDao =
        habitDataBase.habitDao()
}