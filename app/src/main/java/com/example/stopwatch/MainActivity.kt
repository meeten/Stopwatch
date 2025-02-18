package com.example.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var offset = 0L
    private var isRunning = false

    private val BASE_KEY = "base"
    private val OFFSET_KEY = "offset"
    private val RUNNING_KEY = "running"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            offset = savedInstanceState.getLong(OFFSET_KEY)
            isRunning = savedInstanceState.getBoolean(RUNNING_KEY)

            if (isRunning) {
                binding.stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                binding.stopwatch.start()
            } else setBaseTime()
        }

        binding.startButton.setOnClickListener {
            if (!isRunning) {
                setBaseTime()
                binding.stopwatch.start()
                isRunning = true
            }
        }

        binding.stopButton.setOnClickListener {
            if (isRunning) {
                setOffset()
                binding.stopwatch.stop()
                isRunning = false
            }
        }

        binding.resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

    private fun setBaseTime() {
        binding.stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    private fun setOffset() {
        offset = SystemClock.elapsedRealtime() - binding.stopwatch.base
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(BASE_KEY, binding.stopwatch.base)
        outState.putLong(OFFSET_KEY, offset)
        outState.putBoolean(RUNNING_KEY, isRunning)
        super.onSaveInstanceState(outState)
    }
}