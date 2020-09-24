package com.example.lopning.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.lopning.R
import com.example.lopning.ui.MainActivity
import com.example.lopning.utils.Constants.LOCATION_UPDATE_FASTEST_INTERVAL
import com.example.lopning.utils.Constants.LOCATION_UPDATE_INTERVAL
import com.example.lopning.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.example.lopning.utils.Constants.NOTIFICATION_CHANNEL_NAME
import com.example.lopning.utils.Constants.NOTIFICATION_ID
import com.example.lopning.utils.Constants.NOTIFICATION_PENDING_INTENT_ACTION
import com.example.lopning.utils.Constants.PAUSE_SERVICE
import com.example.lopning.utils.Constants.START_OR_RESUME_SERVICE
import com.example.lopning.utils.Constants.STOP_SERVICE
import com.example.lopning.utils.PermissionHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng

/** implemented "lifeCycleService" class since we're gonna use LiveData that's lifecycle aware instead
of old plain service class.*/


typealias Polyline = MutableList<LatLng>
typealias Polylines = MutableList<Polyline>


class LocationService : LifecycleService() {

    private var isFirstRun = true
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {
        val isTracking = MutableLiveData<Boolean>()
        val roadPoints = MutableLiveData<Polylines>()
    }

    override fun onCreate() {
        super.onCreate()
        postInitValues()
        fusedLocationProviderClient = FusedLocationProviderClient(this)

        isTracking.observe(this, Observer {
            trackLocationOrNot(it)
        })
    }

    private fun postInitValues() {
        isTracking.postValue(false)
        roadPoints.postValue(mutableListOf())

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {

            when (it.action) {
                START_OR_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        println("LocationService, service started")
                        startForegroundService()
                        isFirstRun = false
                    } else {
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

    //----------------------------Location retrieval section -------------------------------------//

    @SuppressLint("MissingPermission")
    private fun fetchUserLocation(request: LocationRequest){
        fusedLocationProviderClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
    }

    private fun stopFetchingUserLocation() = fusedLocationProviderClient.removeLocationUpdates(locationCallback)

    private fun trackLocationOrNot( trackingSignal : Boolean){
        if(trackingSignal){
            if (PermissionHelper.locationPermissions(this)){
                val request = LocationRequest().apply {
                    interval = LOCATION_UPDATE_INTERVAL
                    fastestInterval = LOCATION_UPDATE_FASTEST_INTERVAL
                    priority = PRIORITY_HIGH_ACCURACY
                }
                fetchUserLocation(request)
            }
        } else{
           stopFetchingUserLocation()
        }

    }

    private val locationCallback = object : LocationCallback(){

        override fun onLocationResult(resultFetched: LocationResult?) { // resultFetched is were the users location lies
            super.onLocationResult(resultFetched)
            if (isTracking.value!!){ //isTracking == true
                //resultFetched?.locations is to access the location inside the resultFetched variable
                resultFetched?.locations?.let {locations ->
                    for (location in locations) {
                        addPathPoint(location)
                        println("LocationService, user location = ${location.latitude} , ${location.longitude}")
                    }

                }
            }
        }


    }


    private fun addPathPoint(location : Location?){
        location?.let {
            val coordinates = LatLng(location.altitude, location.longitude)
            roadPoints.value?.apply {
                last().add(coordinates) //roadPoint is a list of lists so we add the coordinates fetched in the last position of this list
                roadPoints.postValue(this) //and we notify this change since this is a MutableLiveData
            }
        }
    }
    private fun addEmptyPolyline() = roadPoints.value?.apply {
        add(mutableListOf())
        roadPoints.postValue(this)
    } ?: roadPoints.postValue(mutableListOf(mutableListOf()))


    //--------------------------- Notification section----------------------------------//

    //this one puts in motion the rest
    private fun startForegroundService() {
        addEmptyPolyline()
        isTracking.postValue(true)
        createNotificationManager()
    }


    private fun createNotificationManager(){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createChannel(notificationManager)
        createNotification()
    }


    private fun createChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification() {
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_run)
            .setContentTitle(getString(R.string.app_name))
            .setContentTitle("00:00:00")
            .setContentIntent(getPendingIntent())

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun getPendingIntent() =
        PendingIntent.getActivity(this, 2, pendingIntent(), FLAG_UPDATE_CURRENT)

    private fun pendingIntent() = Intent(this, MainActivity::class.java).also {
        it.action = NOTIFICATION_PENDING_INTENT_ACTION
    }


}