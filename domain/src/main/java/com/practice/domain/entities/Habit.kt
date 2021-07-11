package com.practice.domain.entities

import android.os.Parcelable
import androidx.annotation.ColorRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(
    val title: String,
    @ColorRes val colorId: Int,
    val repeatDays: Int,
    var isCompleted: Boolean = false,
    val createDate: Long,
    val id: String,
    val doneDates: List<Long>,
    val count: Int,
    val description: String,
    val priority: Int,
    val type: Int,
):Parcelable {
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
