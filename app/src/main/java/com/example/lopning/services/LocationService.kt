package com.example.lopning.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.lopning.R
import com.example.lopning.ui.MainActivity
import com.example.lopning.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.example.lopning.utils.Constants.NOTIFICATION_CHANNEL_NAME
import com.example.lopning.utils.Constants.NOTIFICATION_ID
import com.example.lopning.utils.Constants.NOTIFICATION_PENDING_INTENT_ACTION
import com.example.lopning.utils.Constants.PAUSE_SERVICE
import com.example.lopning.utils.Constants.START_OR_RESUME_SERVICE
import com.example.lopning.utils.Constants.STOP_SERVICE

// implemented "lifeCycleService" class since we're gonna use LiveData that's lifecycle aware instead
// of old plain service class.
class LocationService : LifecycleService() {

    private var isFirstRun = true

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {

            when(it.action){
                START_OR_RESUME_SERVICE -> {
                    if (isFirstRun){
                        println("LocationService, service started")
                        startForegroundService()
                        isFirstRun = false
                    }else{
                        println("LocationService, service resumed!")
                    }

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

    private fun startForegroundService(){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        createNotification()

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager : NotificationManager){
        val channel =  NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(){
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_run)
            .setContentTitle(getString(R.string.app_name))
            .setContentTitle("00:00:00")
            .setContentIntent(getPendingIntent())

        startForeground(NOTIFICATION_ID, notificationBuilder.build())

    }

    private fun getPendingIntent() = PendingIntent.getActivity( this, 2, pendingIntent(), FLAG_UPDATE_CURRENT )

    private fun pendingIntent() = Intent(this,  MainActivity::class.java).also {
        it.action = NOTIFICATION_PENDING_INTENT_ACTION }


}