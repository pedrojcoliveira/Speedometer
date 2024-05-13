package com.example.velocimetro

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var speedTextView: TextView
    private lateinit var controlButton: Button
    private var speed = 0
    private var isIncremeting = false
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speedTextView = findViewById(R.id.speedTextView)
        controlButton = findViewById(R.id.controlButton)
        handler = Handler(Looper.getMainLooper())

        controlButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isIncremeting = true
                    startIncrementingSpeed()
                }
                MotionEvent.ACTION_UP -> {
                    isIncremeting = false
                }
            }
            true
        }
    }

    private fun startIncrementingSpeed() {
        handler.post(object : Runnable {
            override fun run() {
                if (isIncremeting) {
                    speed++
                    speedTextView.text = "$speed Km/h"
                    handler.postDelayed(this, 100) // Incrementa a cada 100ms
                } else {
                    stopDecrementingSpeed()
                }
            }
        })
    }

    private fun stopDecrementingSpeed() {
        handler.post(object : Runnable {
            override fun run() {
                if (speed > 0) {
                    speed--
                    speedTextView.text = "$speed Km/h"
                    handler.postDelayed(this, 100) // Decrementa a cada 100ms
                }
            }
        })
    }
}

