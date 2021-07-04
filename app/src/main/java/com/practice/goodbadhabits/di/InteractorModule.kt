package com.practice.goodbadhabits.di

import com.practice.domain.interactors.AddEditHabitInteractor
import com.practice.domain.interactors.DeleteHabitInteractor
import com.practice.domain.interactors.GetHabitInteractor
import com.practice.domain.repositories.HabitRepository
import com.practice.goodbadhabits.di.scopes.PerScreen
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @PerScreen
    @Provides
    fun provideAddEditHabitInteractor(repository: HabitRepository): AddEditHabitInteractor =
        AddEditHabitInteractor(repository)

    @PerScreen
    @Provides
    fun provideDeleteHabitInteractor(repository: HabitRepository): DeleteHabitInteractor =
        DeleteHabitInteractor(repository)

    @PerScreen
    @Provides
    fun provideGetHabitInteractor(repository: HabitRepository): GetHabitInteractor =
        GetHabitInteractor(repository)
}