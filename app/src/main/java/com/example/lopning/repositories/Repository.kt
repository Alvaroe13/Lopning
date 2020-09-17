package com.example.lopning.repositories

import com.example.lopning.cache.Run
import com.example.lopning.cache.RunDao
import javax.inject.Inject

class Repository @Inject constructor(
    val dao : RunDao
) {

    suspend fun insertRun(run : Run) = dao.insertRun(run)

    suspend fun deleteRun(run: Run) = dao.deleteRun(run)

    //These return LiveData and we don't need to create coroutines

    fun getAllRunsByDate() = dao.getAllRunsByDateDesc()

    fun getAllRunsByTime() = dao.getAllRunsByTimeDesc()

    fun getAllRunsByCaloriesBurned() = dao.getAllRunsByCaloriesDesc()

    fun getAllRunsByAvgSpeed() = dao.getAllRunsByAvgSpeed()

    fun getAllRunsByDistance() = dao.getAllRunsByDistanceDesc()

    fun getTotalCaoriesBurned() = dao.getTotalCaloriesBurned()

    fun getTotalTimeMillis() = dao.getTotalTimeMillis()

    fun getTotalDistance() = dao.getTotalDistance()

    fun getAveSpeed() = dao.getAvergaSpeed()

}