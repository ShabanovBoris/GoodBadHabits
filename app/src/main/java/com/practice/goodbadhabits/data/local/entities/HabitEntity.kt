package com.practice.goodbadhabits.data.local.entities

import androidx.annotation.ColorRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practice.goodbadhabits.data.local.DbContract

@Entity(tableName = DbContract.TABLE_NAME)
data class HabitEntity (
    @PrimaryKey
    @ColumnInfo(name = DbContract.COLUMN_ID)
    val id: String,
    @ColumnInfo(name = DbContract.TITLE)
    val title: String,
    @ColumnInfo(name = DbContract.COLOR_RES)
    val color: Int,
    @ColumnInfo(name = DbContract.REPEAT)
    val repeat: Int,
    @ColumnInfo(name = DbContract.IS_COMPLETED)
    val isCompleted: Boolean = false,
    @ColumnInfo(name = DbContract.DATE)
    val date: Int,
    @ColumnInfo(name = DbContract.DONE_DATES)
    val doneDates: String,
    @ColumnInfo(name = DbContract.COUNT)
    val count: Int,
    @ColumnInfo(name = DbContract.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = DbContract.PRIORITY)
    val priority: Int,
    @ColumnInfo(name = DbContract.TYPE)
    val type: Int,

    )





