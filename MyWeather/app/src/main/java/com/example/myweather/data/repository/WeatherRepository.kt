package com.example.myweather.data.repository

import com.example.myweather.data.api.WeatherApiService
import com.example.myweather.data.model.WeatherResponse
import com.example.myweather.utils.Result
import retrofit2.Response

class WeatherRepository(private val apiService: WeatherApiService) {
    suspend fun getWeatherData(location: String): Result<WeatherResponse>{
        return try {
         val response: Response<WeatherResponse> = apiService.getCurrentWeather(
             apiKey = 'YOUR_API_KEY', // TODO
             location = location
         )

            if(response.isSuccessful){
                val weatherResponse = response.body()
                if(weatherResponse != null){
                    Result.Success(weatherResponse)
                }else{
                    Result.Error(Exception("Response body is null"))
                }
            }
            else{
                Result.Error(Exception("Request failed with code ${response.code()}"))
            }
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}
