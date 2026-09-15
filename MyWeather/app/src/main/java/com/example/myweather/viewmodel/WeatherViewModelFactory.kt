package com.example.myweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myweather.data.repository.WeatherRepository

/**
 * Factory class to create instances of WeatherViewModel with custom dependencies.
 */
class WeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {

    /**
     * Creates the ViewModel if the requested class is WeatherViewModel.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)){
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}