package com.example.myweather.data.repository

import com.example.myweather.data.api.WeatherApiService
import com.example.myweather.data.model.WeatherResponse
import com.example.myweather.utils.Result
import retrofit2.Response

/**
 * Repository class that handles data operations and acts as a bridge between the API and ViewModel.
 */
class WeatherRepository(private val apiService: WeatherApiService) {
    
    /**
     * Executes the API call and processes the response into a Result object.
     */
    suspend fun getWeatherData(location: String): Result<WeatherResponse>{
        return try {
            // Call the network service
            val response: Response<WeatherResponse> = apiService.getCurrentWeather(
                apiKey = "9fd51100b2fe4dab815181811261409", // Hardcoded API Key (Should be secured in production)
                location = location
            )

            // Check if the response is successful and has a body
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
        } catch (e: Exception){
            // Catch any network or parsing errors
            Result.Error(e)
        }
    }
}