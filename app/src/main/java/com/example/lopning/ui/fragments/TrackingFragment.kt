package com.example.lopning.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lopning.R
import com.example.lopning.services.LocationService
import com.example.lopning.ui.viewModels.MainViewModel
import com.example.lopning.utils.Constants.START_OR_RESUME_SERVICE
import com.google.android.gms.maps.GoogleMap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tracking.*

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking)  {

    //by viewModels makes possible to inject viewModel class using Hilt
    private val viewModel: MainViewModel by viewModels()

    private var map : GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMapView(savedInstanceState)
        btnInitRun()

    }

    private fun initMapView(savedInstanceState: Bundle?) {
        mapView?.onCreate(savedInstanceState)
        mapView.getMapAsync{
            map = it
        }
    }


    private fun btnInitRun() {
        btnToggleRun.setOnClickListener {
            sendCommandToService(START_OR_RESUME_SERVICE)
        }
    }

    private fun sendCommandToService(command :String ) {
        Intent(requireContext(), LocationService::class.java).also {
            it.action = command
            requireContext().startService(it)
        }
    }


    //using WebView, so we must handle map LifeCycle manually.

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }


}