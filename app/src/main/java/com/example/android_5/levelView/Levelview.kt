package com.example.android_5.levelView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class LevelView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var pitch = 0f
    private var roll = 0f
    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 10f
        textSize = 50f
    }

    fun updateAngles(x: Float, y: Float) {
        roll = -x * 5
        pitch = y * 5
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val lineX = centerX + roll * 10
        val lineY = centerY - pitch * 10

        canvas.drawLine(centerX - 200, lineY, centerX + 200, lineY, paint)

        canvas.drawText("Кут: %.2f°".format(roll), centerX - 100, centerY - 100, paint)
    }
}
