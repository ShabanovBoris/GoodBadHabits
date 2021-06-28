package com.practice.goodbadhabits.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

 fun <T> Flow<T>.launchInWhenStarted(lifecycleCoroutineScope: LifecycleCoroutineScope): Job =
    lifecycleCoroutineScope.launchWhenStarted {
    collect() // tail-call
}