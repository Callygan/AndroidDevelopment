package com.example.myweather.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myweather.R
import com.example.myweather.data.api.RetrofitInstance
import com.example.myweather.data.model.WeatherResponse
import com.example.myweather.data.repository.WeatherRepository
import com.example.myweather.databinding.ActivityMainBinding
import com.example.myweather.viewmodel.WeatherViewModel
import com.example.myweather.viewmodel.WeatherViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: WeatherViewModel by viewModels{
        WeatherViewModelFactory(
            WeatherRepository(RetrofitInstance.apiService)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        initView()
        setupObservers()

        viewModel.fetchWeatherData("Sibiu")
    }

    private fun initView() {
        binding.btnSearch.setOnClickListener {
            val location = binding.etSearch.text.toString().trim()

            if (location.isNotEmpty()) {
                hideKeyboard()
                viewModel.fetchWeatherData(location)
            }
            else {
                Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setupObservers() {
        viewModel.weatherData.observe(this, { weatherData ->
            weatherData?.let {
                updateWeatherUI(it)
            }
        })

        viewModel.isLoading.observe(this, {isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.errorMessage.observe(this, {errorMessage ->
            if(errorMessage.isNotEmpty()){
                binding.tvError.text = errorMessage
                binding.tvError.visibility = View.VISIBLE
                binding.weatherCard.visibility = View.GONE
            }
            else {
                binding.tvError.visibility = View.GONE
            }
        })
    }

    fun updateWeatherUI(weatherResponse: WeatherResponse) {
        binding.tvLocation.text = "${weatherResponse.location.name}, ${weatherResponse.location.country}"
        binding.tvTemperature.text = "${weatherResponse.current.temp_c}°C"
        binding.tvCondition.text = weatherResponse.current.condition.text
        binding.tvFeelsLike.text = "${weatherResponse.current.feelsLike_c}°C"
        binding.tvHumidity.text = "${weatherResponse.current.humidity}%"
        binding.tvWind.text = "${weatherResponse.current.wind_kph} km/h"

        binding.weatherCard.visibility = View.VISIBLE
    }

    private fun hideKeyboard(){
        val view = this.currentFocus
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}