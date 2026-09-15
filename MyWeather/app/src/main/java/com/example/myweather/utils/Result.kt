package com.example.myweather.utils

/**
 * A generic class that holds a value with its loading status.
 * Used to handle Success and Error states for data operations.
 */
sealed class Result<out T> {
    
    // Represents a successful operation with data
    data class Success<out T>(val data: T) : Result<T>()
    
    // Represents a failed operation with an exception
    data class Error(val exception: Exception) : Result<Nothing>()
}