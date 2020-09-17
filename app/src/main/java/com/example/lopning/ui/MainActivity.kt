package com.example.lopning.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lopning.R
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

   // lateinit var bottomView : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

}