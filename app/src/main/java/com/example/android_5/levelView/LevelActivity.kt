package com.example.android_5.levelView

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.android_5.R

class LevelActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var levelView: LevelView
    private lateinit var textOutput: TextView
    private lateinit var btnOk: Button

    private var roll = 0f
    private var pitch = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)

        levelView = findViewById(R.id.levelView)
        textOutput = findViewById(R.id.text_output)
        btnOk = findViewById(R.id.btn_ok)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        btnOk.setOnClickListener {
            val outputText =
                "Кут нахилу: ${"%.2f".format(roll)}°\nЛінія горизонту: ${"%.2f".format(pitch)}°"
            textOutput.text = outputText
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            roll = it.values[0]
            pitch = it.values[1]
            levelView.updateAngles(roll, pitch)
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
