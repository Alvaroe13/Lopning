package com.example.lopning.cache

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RunDao {

    //-------------------- Basic Queries------------------------------//

    @Insert
    suspend fun insertRun(run :Run )

    @Delete
    suspend fun deleteRun(un: Run)


    //----------------- Custom Queries -----------------------------//

    @Query("SELECT * FROM run_table ORDER BY timeStamp DESC")
    fun getAllRunsByDateDesc(): LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY timeInMillis DESC")
    fun getAllRunsByTimeDesc() : LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY caloriesLost DESC")
    fun getAllRunsByCaloriesDesc(): LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY speedAvg DESC")
    fun getAllRunsByAvgSpeed(): LiveData<List<Run>>

    @Query("SELECT * FROM  run_table ORDER BY distanceInMeters DESC ")
    fun getAllRunsByDistanceDesc(): LiveData<List<Run>>

    @Query("SELECT SUM(caloriesLost) FROM run_table")
    fun getTotalCaloriesBurned(): LiveData<Long>

    @Query("SELECT SUM(timeInMillis) FROM run_table")
    fun getTotalTimeMillis(): LiveData<Int>

    @Query("SELECT SUM(distanceInMeters) FROM run_table")
    fun getTotalDistance(): LiveData<Int>

    @Query("SELECT AVG(speedAvg) FROM run_table")
    fun getAvergaSpeed(): LiveData<Float>


}