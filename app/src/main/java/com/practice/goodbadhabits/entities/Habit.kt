package com.practice.goodbadhabits.entities

import androidx.annotation.ColorRes

data class Habit(
    val title: String,
    @ColorRes val color: Int,
    val repeat: String,
    val isCompleted: Boolean = false
    )
