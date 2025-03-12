package com.example.android_5.Brightness

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.provider.Settings
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.android_5.R

class BrightnessActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var autoBrightnessButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brightness)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        autoBrightnessButton = findViewById(R.id.auto_brightness_button)

        autoBrightnessButton.setOnClickListener {
            toggleAutoBrightness()
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.sensor.type == Sensor.TYPE_LIGHT) {
            val ambientLight = event.values[0]
            adjustScreenBrightnessBasedOnLight(ambientLight)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    private fun adjustScreenBrightnessBasedOnLight(ambientLight: Float) {
        val brightness = when {
            ambientLight < 10 -> 0
            ambientLight in 10.0..100.0 -> 128
            else -> 255
        }

        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness)
    }

    private fun toggleAutoBrightness() {
        val currentMode = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        if (currentMode == Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) {
            Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC)
        } else {
            Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        }
    }
}
