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
    var createDate: Long,
    var id: String,
    val doneDates: List<Long>,
    val count: Int,
    val description: String,
    val priority: Int,
    val type: Int,
):Parcelable {
    enum class Priority{
        LOW ,
        MEDIUM,
        HIGH
    }

    enum class Type{
        GOOD,
        BAD
    }

}
