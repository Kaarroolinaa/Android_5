package com.example.android_5.Weather

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.android_5.R

class WeatherActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var pressureSensor: Sensor? = null
    private lateinit var pressureTextView: TextView
    private lateinit var weatherButton: Button
    private var lastPressure = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

        if (pressureSensor == null) {
            Log.e("WeatherActivity", "Барометричний сенсор не підтримується на цьому пристрої.")
        } else {
            Log.d("WeatherActivity", "Барометричний сенсор підтримується.")
        }

        pressureTextView = findViewById(R.id.pressureTextView)
        weatherButton = findViewById(R.id.weatherButton)

        weatherButton.setOnClickListener {
            val intent = Intent(this, WeatherForecastActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        pressureSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_PRESSURE) {
                val currentPressure = it.values[0]
                if (currentPressure != lastPressure) {
                    pressureTextView.text = "Атмосферний тиск: $currentPressure мбар"
                    Log.d("WeatherActivity", "Тиск: $currentPressure мбар")
                }
                lastPressure = currentPressure
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}
