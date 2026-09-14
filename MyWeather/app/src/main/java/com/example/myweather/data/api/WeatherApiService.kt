package com.example.myweather.data.api

import com.example.myweather.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
//    http://api.weatherapi.com/v1/current.json?key=9fd51100b2fe4dab815181811261409&q=Sibiu&aqi=no

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") airQuality: String = "Yes"
    ): Response<WeatherResponse>
}