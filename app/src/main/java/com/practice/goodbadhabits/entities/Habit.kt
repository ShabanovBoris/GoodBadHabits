package com.practice.goodbadhabits.entities

import android.os.Parcelable
import android.widget.RadioButton
import androidx.annotation.ColorRes
import androidx.core.view.ViewCompat
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.FragmentAdditionBinding
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Habit(
    val title: String,
    @ColorRes val colorId: Int,
    val repeat: Int,
    val isCompleted: Boolean? = false,
    val date: Int?,
    val id: String,
    val doneDates: List<Long>?,
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
