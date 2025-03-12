package com.example.android_5.Compass

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CompassView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 5f
        textSize = 50f
    }

    private val arrowPaint = Paint().apply {
        color = Color.RED
        strokeWidth = 10f
    }

    private var azimuth: Float = 0f

    fun updateDirection(azimuth: Float) {
        this.azimuth = azimuth
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        val centerX = width / 2
        val centerY = height / 2

        paint.color = Color.BLACK
        canvas.drawText("N", centerX - 10, 50f, paint)
        canvas.drawText("S", centerX - 10, height - 20f, paint)
        canvas.drawText("E", width - 100f, centerY + 10, paint)
        canvas.drawText("W", 20f, centerY + 10, paint)

        val arrowLength = 150f
        val arrowX = centerX + arrowLength * Math.sin(Math.toRadians(azimuth.toDouble())).toFloat()
        val arrowY = centerY - arrowLength * Math.cos(Math.toRadians(azimuth.toDouble())).toFloat()

        canvas.drawLine(centerX, centerY, arrowX, arrowY, arrowPaint)
    }
}