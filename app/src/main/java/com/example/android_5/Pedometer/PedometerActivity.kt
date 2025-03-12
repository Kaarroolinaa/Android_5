package com.example.android_5.Pedometer

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.android_5.R

class PedometerActivity: AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private var stepDetectorSensor: Sensor? = null
    private var stepCount = 0
    private lateinit var stepCountTextView: TextView

    private val handler = Handler(Looper.getMainLooper())
    private val updateTask = object : Runnable {
        override fun run() {
            stepCountTextView.text = "Кроки: $stepCount"
            handler.postDelayed(this, 1000)
        }
    }

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null && event.sensor.type == Sensor.TYPE_STEP_DETECTOR) {
                stepCount++
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedometer)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        stepCountTextView = findViewById(R.id.stepCountTextView)

        if (stepDetectorSensor != null) {
            sensorManager.registerListener(sensorEventListener, stepDetectorSensor, SensorManager.SENSOR_DELAY_UI)
        } else {
            stepCountTextView.text = "Сенсор кроків не підтримується"
        }
    }

    override fun onResume() {
        super.onResume()
        handler.post(updateTask)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateTask)

        stepCount = 0
        sensorManager.unregisterListener(sensorEventListener)
    }
}
