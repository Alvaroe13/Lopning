package com.example.lopning.cache

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity( tableName = "run_table")
data class Run(
      var screenshot: Bitmap? = null,
      var timeStamp: Long = 0L,
      var speedAvg: Float = 0f,
      var distanceInMeters: Int = 0,
      var timeInMillis : Long = 0L,
      var caloriesLost: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}