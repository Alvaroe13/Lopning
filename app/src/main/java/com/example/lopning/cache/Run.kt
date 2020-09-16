package com.example.lopning.cache

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity( tableName = "run_table")
data class Run(
    private var screenshot: Bitmap? = null,
    private var timeStamp: Long = 0L,
    private var speedAvg: Float = 0f,
    private var distanceInMeters: Long = 0L,
    private var timeInMillis : Long = 0L,
    private var caloriesLost: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}