package com.practice.goodbadhabits.di

import android.content.Context
import com.practice.goodbadhabits.ui.MainScreenComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [Subcomponents::class, NetworkModule::class, DispatcherModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }

    fun plusMainScreenComponent(): MainScreenComponent.Factory
}