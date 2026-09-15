package com.example.myweather.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Singleton object that provides the Retrofit instance for network calls.
 */
object RetrofitInstance {

    // Base URL for the Weather API
    private const val BASE_URL = "https://api.weatherapi.com/v1/"
    
    // Interceptor to log network requests and responses for debugging
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttpClient configured with timeouts and logging
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // The Retrofit builder that connects to the server and handles JSON conversion
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // The public service instance to perform API calls
    val apiService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}