package com.example.lopning.services

import android.content.Intent
import androidx.lifecycle.LifecycleService
import com.example.lopning.utils.Constants.PAUSE_SERVICE
import com.example.lopning.utils.Constants.START_OR_RESUME_SERVICE
import com.example.lopning.utils.Constants.STOP_SERVICE

// implemented "lifeCycleService" class since we're gonna use LiveData that's lifecycle aware instead
// of old plain service class.
class LocationService : LifecycleService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {

            when(it.action){
                START_OR_RESUME_SERVICE -> {
                    println("LocationService, service started")
                }
                PAUSE_SERVICE -> {
                    println("LocationService, service paused")
                }
                STOP_SERVICE -> {
                    println("LocationService, service stopped")
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)

    }
}