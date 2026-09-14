package com.example.myweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.data.model.WeatherResponse
import com.example.myweather.data.repository.WeatherRepository
import kotlinx.coroutines.launch
import com.example.myweather.utils.Result

class WeatherViewModel(private val repository: WeatherRepository): ViewModel() {
    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> =  _weatherData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchWeatherData(location: String){
        _isLoading.value = true

        viewModelScope.launch {
            when(val result = repository.getWeatherData(location)){
                is Result.Success -> {
                    _weatherData.value = result.data
                    _errorMessage.value = ""
                }
                is Result.Error -> {
                    _errorMessage.value = result.exception.message ?: "Unknown error"
                }
            }

            _isLoading.value = false
        }
    }
}