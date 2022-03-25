package com.example.hapticslayout

import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import androidx.appcompat.app.AppCompatActivity
import com.example.hapticslayout.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.wheelTickButton.setOnClickListener {
            VibratorCompat.vibrateWheel(this)
        }
        binding.tickLegacyButton.setOnClickListener {
            VibratorCompat.vibrateTickLegacy(this)
        }
        binding.tickCompatButton.setOnClickListener {
            VibratorCompat.vibrate(this, EffectType.TICK)
        }
        binding.confirmButton.setOnClickListener {
            it.isHapticFeedbackEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                it.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
            }
        }
    }
}