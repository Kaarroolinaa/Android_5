package com.example.android_5.Weather

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.android_5.R

class WeatherForecastActivity : AppCompatActivity() {

    private lateinit var weatherTextView: TextView
    private lateinit var cityEditText: EditText
    private lateinit var fetchWeatherButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        weatherTextView = findViewById(R.id.weatherTextView)
        cityEditText = findViewById(R.id.cityEditText)
        fetchWeatherButton = findViewById(R.id.fetchWeatherButton)

        fetchWeatherButton.setOnClickListener {
            val city = cityEditText.text.toString()
            fetchWeather(city)
        }
    }

    private fun fetchWeather(city: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherApiService::class.java)
        val apiKey = "a36be37755fc5fa36d18bdd9a3152833"

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = service.getWeather(city, apiKey)
                if (response.isSuccessful) {
                    val weather = response.body()
                    val tempCelsius = weather?.main?.temp?.minus(273.15f)
                    val pressure = weather?.main?.pressure
                    val humidity = weather?.main?.humidity
                    val description = weather?.weather?.get(0)?.description

                    val forecastText = """
                        Місто: ${weather?.name}
                        Температура: ${tempCelsius?.toInt()}°C
                        Тиск: $pressure мбар
                        Вологість: $humidity%
                        Опис: $description
                    """.trimIndent()

                    weatherTextView.text = forecastText
                } else {
                    weatherTextView.text = "Помилка при отриманні даних."
                }
            } catch (e: Exception) {
                weatherTextView.text = "Помилка: ${e.message}"
            }
        }
    }
}
