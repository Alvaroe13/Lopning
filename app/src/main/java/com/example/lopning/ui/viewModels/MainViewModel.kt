package com.example.lopning.ui.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.lopning.repositories.Repository

/**
 * @ViewModelInject is the one to user when injecting dependency in VM instead of @Inject
 */
class MainViewModel @ViewModelInject constructor(
    val repo : Repository
): ViewModel() {
}