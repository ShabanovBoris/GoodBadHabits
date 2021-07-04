package com.practice.goodbadhabits.di

import com.practice.domain.common.DISPATCHER_DEFAULT
import com.practice.domain.common.DISPATCHER_IO
import com.practice.domain.common.DISPATCHER_MAIN
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
class DispatcherModule {

    @Provides
    @Named(DISPATCHER_IO)
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Named(DISPATCHER_MAIN)
    fun provideDispatcherMain(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Named(DISPATCHER_DEFAULT)
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default
}