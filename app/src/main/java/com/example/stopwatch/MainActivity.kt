package com.example.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity

class MainActivity:AppCompatActivity(){
    private lateinit var stopwatch:Chronometer

    private var offset = 0L
    private var isRunning = false

    private val BASE_KEY = "base"
    private val OFFSET_KEY = "offset"
    private val RUNNING_KEY = "running"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stopwatch = findViewById(R.id.stopwatch)

        if (savedInstanceState != null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            isRunning = savedInstanceState.getBoolean(RUNNING_KEY)

            if (isRunning) {
                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            } else setBaseTime()
        }

        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener{
            if(!isRunning){
                setBaseTime()
                stopwatch.start()
                isRunning = true
            }
        }

        val stopButton = findViewById<Button>(R.id.stop_button)
        stopButton.setOnClickListener{
            if (isRunning){
                setOffset()
                stopwatch.stop()
                isRunning = false
            }
        }

        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener{
            offset = 0
            setBaseTime()
        }
    }

    private fun setBaseTime(){
        stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    private fun setOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(BASE_KEY, stopwatch.base)
        outState.putLong(OFFSET_KEY, offset)
        outState.putBoolean(RUNNING_KEY, isRunning)
        super.onSaveInstanceState(outState)
    }
}