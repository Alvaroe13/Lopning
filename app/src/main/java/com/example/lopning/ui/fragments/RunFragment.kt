package com.example.lopning.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lopning.R
import com.example.lopning.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run)  {

    //by viewModels makes possible to inject viewModel class using Hilt
    private val viewModel: MainViewModel by viewModels()
}