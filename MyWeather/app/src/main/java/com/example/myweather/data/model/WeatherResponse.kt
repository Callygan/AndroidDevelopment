package com.example.myweather.data.model


data class WeatherResponse(
    val location: Location,
    val current: Current,
)

data class Location(
    val name: String,
    val country: String,
)

data class Current(
    val temp_c: Double,
    val condition: Condition,
    val humidity: Int,
    val wind_kph: Double,
    val feelsLike_c: Double,
)

data class Condition(
    val text: String,
    val icon: String,
)