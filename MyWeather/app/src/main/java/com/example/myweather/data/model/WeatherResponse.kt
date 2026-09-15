package com.example.myweather.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data classes representing the structure of the API JSON response.
 */

// Root response object
data class WeatherResponse(
    val location: Location,
    val current: Current,
)

// Geographical location details
data class Location(
    val name: String,
    val country: String,
)

// Current weather status and measurements
data class Current(
    val temp_c: Double,
    val condition: Condition,
    val humidity: Int,
    val wind_kph: Double,
    @SerializedName("feelslike_c")
    val feelsLike_c: Double,
)

// Weather condition description and icon
data class Condition(
    val text: String,
    val icon: String,
)