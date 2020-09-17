package com.example.lopning.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lopning.R
import com.example.lopning.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_setup.*

@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup)  {

    //by viewModels makes possible to inject viewModel class using Hilt
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivContinue.setOnClickListener {
            findNavController().navigate(R.id.action_setupFragment_to_runFragment)
        }
    }
}