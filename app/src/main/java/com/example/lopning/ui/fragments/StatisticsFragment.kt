package com.example.lopning.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lopning.R
import com.example.lopning.ui.viewModels.MainViewModel
import com.example.lopning.ui.viewModels.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics)  {

    //by viewModels makes possible to inject viewModel class using Hilt
    private val viewModel: StatisticsViewModel by viewModels()
}