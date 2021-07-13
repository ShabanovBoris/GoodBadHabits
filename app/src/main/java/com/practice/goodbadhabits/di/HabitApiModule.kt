package com.practice.goodbadhabits.di

import com.practice.data.api.HabitApi
import com.practice.goodbadhabits.di.scopes.PerScreen
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class HabitApiModule {

    @PerScreen
    @Provides
    fun provideHabitApi(retrofit: Retrofit): HabitApi =
        retrofit.create(HabitApi::class.java)
}