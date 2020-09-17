package com.example.lopning.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lopning.R
import com.example.lopning.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking)  {

    //by viewModels makes possible to inject viewModel class using Hilt
    private val viewModel: MainViewModel by viewModels()
}