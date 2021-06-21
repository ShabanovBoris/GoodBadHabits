package com.practice.goodbadhabits.data.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HabitApiResponse(
	val date: Int,
	@field:SerializedName("uid")
	val id: String,
	@field:SerializedName("color")
	val colorId: Int,
	@field:SerializedName("done_dates")
	val doneDates: List<Int>,
	val count: Int,
	val description: String,
	val priority: Int,
	val title: String,
	val type: Int,
	val frequency: Int
) : Parcelable

@Parcelize
data class HabitUid(
	@field:SerializedName("uid")
	val uid: String? = null
) : Parcelable

@Parcelize
data class HabitDoneRequest(
	val date: Int,
	@field:SerializedName("habit_uid")
	val uid: String,

) : Parcelable

