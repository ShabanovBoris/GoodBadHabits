package com.practice.goodbadhabits.entities

import androidx.annotation.ColorRes

data class Habit(
    val title: String,
    @ColorRes val colorId: Int,
    val repeat: Int,
    val isCompleted: Boolean? = false,
    val date: Int,
    val id: String,
    val doneDates: List<Int>,
    val count: Int,
    val description: String,
    val priority: Int,
    val type: Int,
) {
    enum class Priority(priority: Int) {
        LOW(0),
        MEDIUM(1),
        HIGH(2)
    }

    enum class Type(type: Int){
        GOOD(0),
        BAD(1)
    }
}
