package com.practice.goodbadhabits.di

import com.practice.data.api.HabitRemoteDataSource
import com.practice.data.api.impl.HabitRemoteDataSourceImpl
import com.practice.data.db.HabitLocalDataSource
import com.practice.data.db.impl.HabitLocalDataSourceImpl
import com.practice.data.repositories.habits.HabitRepositoryImpl
import com.practice.domain.repositories.HabitRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun provideRepository(repositoryImpl: HabitRepositoryImpl): HabitRepository

    @Binds
    fun provideHabitRemoteDataSource(
        habitRemoteDataSourceImpl: HabitRemoteDataSourceImpl
    ): HabitRemoteDataSource

    @Binds
    fun provideHabitLocalDataSource(
        habitLocalDataSourceImpl: HabitLocalDataSourceImpl
    ): HabitLocalDataSource
}