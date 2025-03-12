package com.example.android_5.Brightness

import android.content.ContentResolver
import android.provider.Settings

object ScreenBrightnessUtils {

    fun adjustScreenBrightness(contentResolver: ContentResolver, lightLevel: Float) {
        val brightness = (lightLevel / 100) * 255
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness.toInt())
    }

    fun setAutoBrightness(contentResolver: ContentResolver, enable: Boolean) {
        val mode = if (enable) {
            Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
        } else {
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        }
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, mode)
    }

    fun getCurrentBrightness(contentResolver: ContentResolver): Int {
        return Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)
    }
}