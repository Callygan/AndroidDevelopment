package com.example.myweather.data.api

import com.example.myweather.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface defining the API endpoints for weather data.
 */
interface WeatherApiService {

    /**
     * Fetches current weather data for a specific location.
     * @param apiKey The secret key to access the API.
     * @param location City name or coordinates.
     * @param airQuality Option to include air quality data (default is "Yes").
     */
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") airQuality: String = "Yes"
    ): Response<WeatherResponse>
}