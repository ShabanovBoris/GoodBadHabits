package com.practice.data.entities

import androidx.room.*
import com.practice.data.db.DbContract

@Entity(tableName = DbContract.TABLE_NAME)
@TypeConverters(HabitEntity.DatesTypeConverter::class)
data class HabitEntity(
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
    val date: Int?,
    @ColumnInfo(name = DbContract.DONE_DATES)
    val doneDates: DoneDates,
    @ColumnInfo(name = DbContract.COUNT)
    val count: Int,
    @ColumnInfo(name = DbContract.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = DbContract.PRIORITY)
    val priority: Int,
    @ColumnInfo(name = DbContract.TYPE)
    val type: Int,
) {

    data class DoneDates(
        val dates: List<Long>
    )

    class DatesTypeConverter {

        @TypeConverter
        fun datesToString(doneDates: DoneDates): String =
            if (doneDates.dates.isEmpty()) {
                "0"
            } else {
                doneDates.dates.joinToString(",")
            }

        @TypeConverter
        fun datesFromString(doneDates: String): DoneDates =
            if (doneDates == "0") {
                DoneDates(emptyList())
            } else {
                DoneDates(
                    doneDates
                        .split(",")
                        .map { it.toLong() }
                )
            }
    }
}





