package com.example.hapticslayout

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

object VibratorCompat {
    private fun getVibrator(context: Context): Vibrator {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    fun vibrateWheel(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        getVibrator(context).vibrate(
            VibrationEffect.createOneShot(
                25,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }

    fun vibrateTickLegacy(context: Context) {
        val vibrator = getVibrator(context)
        @Suppress("DEPRECATION")
        vibrator.vibrate(EffectType.TICK.compatPattern, -1)
    }


    fun vibrate(context: Context, effectType: EffectType) {
        val vibrator = getVibrator(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && vibrator.areAllEffectsSupported(effectType.effectId) == Vibrator.VIBRATION_EFFECT_SUPPORT_YES) {
            vibrator.vibrate(VibrationEffect.createPredefined(effectType.effectId))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(effectType.compatPattern, -1)
        }
    }
}

@SuppressLint("InlinedApi")
//Values from here https://cs.android.com/android/platform/superproject/+/master:frameworks/base/services/core/java/com/android/server/vibrator/VibrationSettings.java;l=121;
enum class EffectType(val compatPattern: LongArray, val effectId: Int) {
    CLICK(longArrayOf(0, 10, 20, 30), VibrationEffect.EFFECT_CLICK),
    TICK(longArrayOf(125, 30), VibrationEffect.EFFECT_TICK)
}