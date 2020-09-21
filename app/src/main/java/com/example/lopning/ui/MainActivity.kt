package com.example.lopning.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lopning.R
import com.example.lopning.utils.Constants.NOTIFICATION_PENDING_INTENT_ACTION
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openTrackingFragment(intent)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        bottomView.setupWithNavController(fragmentHost.findNavController())
        showSomeViews()

    }

    private fun showSomeViews() {

        fragmentHost.findNavController().addOnDestinationChangedListener{ _ , destination, _ ->

            when(destination.id) {
                R.id.settingsFragment , R.id.runFragment, R.id.statisticsFragment ->
                    bottomView.visibility = View.VISIBLE
                else -> bottomView.visibility = View.GONE
            }

        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        openTrackingFragment(intent)
    }

    private fun openTrackingFragment(intent : Intent?){
        if (intent?.action == NOTIFICATION_PENDING_INTENT_ACTION){
            fragmentHost.findNavController().navigate(R.id.global_open_tracking_fragment)
        }
    }

}