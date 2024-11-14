package edu.temple.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var timerBinder: TimerService.TimerBinder
    var isConnection = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.startButton)

        val serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                timerBinder = service as TimerService.TimerBinder
                isConnection = true
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                isConnection = false

            }
        }

        bindService(
            Intent(this, TimerService::class.java),
            serviceConnection,
            BIND_AUTO_CREATE
        )

        findViewById<Button>(R.id.startButton).setOnClickListener {

            if (timerBinder.isRunning == false) {
                startButton.text = "Pause"
                if (isConnection) {
                    timerBinder.start(100)
                }
            } else{
                startButton.text = "Start"
                if (isConnection) {
                    timerBinder.pause()
                }
            }

        }
        
        findViewById<Button>(R.id.stopButton).setOnClickListener {
            if (isConnection) {
                timerBinder.stop()
                startButton.text = "Start"

            }
        }
    }
}