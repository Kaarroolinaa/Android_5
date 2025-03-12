package com.example.android_5.Weather

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val name: String
)

data class Main(
    val temp: Float,
    val pressure: Float,
    val humidity: Int
)

data class Weather(
    val description: String
)