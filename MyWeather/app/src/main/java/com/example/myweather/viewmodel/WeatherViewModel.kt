package com.example.myweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.data.model.WeatherResponse
import com.example.myweather.data.repository.WeatherRepository
import kotlinx.coroutines.launch
import com.example.myweather.utils.Result

/**
 * ViewModel that manages the UI state and handles logic for fetching weather data.
 */
class WeatherViewModel(private val repository: WeatherRepository): ViewModel() {
    
    // Internal mutable data for weather information
    private val _weatherData = MutableLiveData<WeatherResponse>()
    // Public immutable LiveData for the UI to observe
    val weatherData: LiveData<WeatherResponse> =  _weatherData

    // Internal mutable data for loading status
    private val _isLoading = MutableLiveData<Boolean>()
    // Public immutable LiveData for the UI to observe
    val isLoading: LiveData<Boolean> = _isLoading

    // Internal mutable data for error messages
    private val _errorMessage = MutableLiveData<String>()
    // Public immutable LiveData for the UI to observe
    val errorMessage: LiveData<String> = _errorMessage

    /**
     * Triggers the data fetching process from the repository within a coroutine.
     */
    fun fetchWeatherData(location: String){
        // Start loading
        _isLoading.value = true

        viewModelScope.launch {
            // Call the repository to get data
            when(val result = repository.getWeatherData(location)){
                is Result.Success -> {
                    // Update weather data on success
                    _weatherData.value = result.data
                    _errorMessage.value = ""
                }
                is Result.Error -> {
                    // Update error message on failure
                    _errorMessage.value = result.exception.message ?: "Unknown error"
                }
            }
            // Stop loading
            _isLoading.value = false
        }
    }
}