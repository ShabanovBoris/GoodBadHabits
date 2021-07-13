package com.practice.goodbadhabits.di

import com.practice.data.api.HabitRemoteDataSource
import com.practice.data.api.impl.HabitRemoteDataSourceImpl
import com.practice.data.db.HabitLocalDataSource
import com.practice.data.db.impl.HabitLocalDataSourceImpl
import com.practice.data.repositories.habits.HabitRepositoryImpl
import com.practice.domain.repositories.HabitRepository
import com.practice.goodbadhabits.di.scopes.PerScreen
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @PerScreen
    @Binds
    fun provideRepository(
        repositoryImpl: HabitRepositoryImpl
    ): HabitRepository

    @PerScreen
    @Binds
    fun provideHabitRemoteDataSource(
        habitRemoteDataSourceImpl: HabitRemoteDataSourceImpl
    ): HabitRemoteDataSource

    @PerScreen
    @Binds
    fun provideHabitLocalDataSource(
        habitLocalDataSourceImpl: HabitLocalDataSourceImpl
    ): HabitLocalDataSource
}