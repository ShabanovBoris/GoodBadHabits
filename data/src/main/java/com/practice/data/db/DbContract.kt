package com.practice.data.db

import android.provider.BaseColumns

object DbContract {
    const val DATABASE_NAME = "habit.db"


    const val TABLE_NAME = "habit_table"
    const val TITLE = "title"
    const val REPEAT = "repeat_times"
    const val IS_COMPLETED = "is_completed"
    const val COLOR_RES = "color_res"
    const val COLUMN_ID = TABLE_NAME + BaseColumns._ID
    const val DATE = "date"
    const val DONE_DATES = "done_dates"
    const val COUNT = "count"
    const val DESCRIPTION = "description"
    const val PRIORITY = "priority"
    const val TYPE = "type"
}