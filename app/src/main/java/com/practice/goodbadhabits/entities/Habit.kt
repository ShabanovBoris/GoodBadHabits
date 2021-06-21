package com.practice.goodbadhabits.entities

import android.widget.RadioButton
import androidx.annotation.ColorRes
import androidx.core.view.ViewCompat
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.FragmentAdditionBinding
import java.util.*

data class Habit(
    val title: String,
    @ColorRes val colorId: Int,
    val repeat: Int,
    val isCompleted: Boolean? = false,
    val date: Int?,
    val id: String,
    val doneDates: List<Int>?,
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
