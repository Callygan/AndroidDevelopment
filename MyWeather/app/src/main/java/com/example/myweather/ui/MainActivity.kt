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

/**
 * Main screen of the application that displays weather information.
 */
class MainActivity : AppCompatActivity() {
    
    // Binding object to access UI elements directly
    private lateinit var binding: ActivityMainBinding

    // Initialize the ViewModel with its factory and repository
    private val viewModel: WeatherViewModel by viewModels{
        WeatherViewModelFactory(
            WeatherRepository(RetrofitInstance.apiService)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Setup View Binding to connect the layout file
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize UI components and listeners
        initView()
        
        // Setup observers to listen for data changes
        setupObservers()

        // Fetch initial weather data for a default city
        viewModel.fetchWeatherData("Sibiu")
    }

    /**
     * Sets up click listeners for the search button.
     */
    private fun initView() {
        binding.btnSearch.setOnClickListener {
            val location = binding.etSearch.text.toString().trim()

            if (location.isNotEmpty()) {
                hideKeyboard()
                // Request weather data for the user-entered location
                viewModel.fetchWeatherData(location)
            }
            else {
                // Show a message if the search field is empty
                Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Observes changes in the ViewModel's LiveData to update the screen.
     */
    private fun setupObservers() {
        // Update the screen when new weather data is received
        viewModel.weatherData.observe(this, { weatherData ->
            weatherData?.let {
                updateWeatherUI(it)
            }
        })

        // Show or hide the loading spinner based on request status
        viewModel.isLoading.observe(this, {isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        // Handle and display error messages if something goes wrong
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

    /**
     * Populates the UI components with the received weather details.
     */
    fun updateWeatherUI(weatherResponse: WeatherResponse) {
        binding.tvLocation.text = "${weatherResponse.location.name}, ${weatherResponse.location.country}"
        binding.tvTemperature.text = "${weatherResponse.current.temp_c}°C"
        binding.tvCondition.text = weatherResponse.current.condition.text
        binding.tvFeelsLike.text = "${weatherResponse.current.feelsLike_c}°C"
        binding.tvHumidity.text = "${weatherResponse.current.humidity}%"
        binding.tvWind.text = "${weatherResponse.current.wind_kph} km/h"

        // Make the weather card visible once the data is ready
        binding.weatherCard.visibility = View.VISIBLE
    }

    /**
     * Utility method to hide the soft keyboard after searching.
     */
    private fun hideKeyboard(){
        val view = this.currentFocus
        view?.let {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}