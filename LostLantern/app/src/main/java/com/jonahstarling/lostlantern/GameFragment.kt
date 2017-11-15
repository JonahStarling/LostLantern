package com.jonahstarling.lostlantern

import android.animation.TimeAnimator
import android.app.Fragment
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main_menu.view.*

/**
 * Created by jonah on 11/15/17.
 */
class GameFragment : Fragment(), SensorEventListener {

    private lateinit var rootView: View
    private lateinit var sensorManager: SensorManager
    private var xSpeed: Float = 0.0f
    private var ySpeed: Float = 0.0f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.fragment_game, container, false)
        sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Update player
        val animator = TimeAnimator()
        animator.setTimeListener { _, _, _ ->
            rootView.player.x += xSpeed
            rootView.player.y += ySpeed
            if (xSpeed > 0.0f) {
                rootView.player.angle = Math.atan(ySpeed.toDouble() / xSpeed.toDouble())
            } else if (xSpeed < 0.0f) {
                rootView.player.angle = Math.atan(ySpeed.toDouble() / xSpeed.toDouble()) + Math.PI
            } else if (xSpeed == 0.0f) {
                if (ySpeed >= 0.0f) {
                    rootView.player.angle = Math.PI / 2
                } else {
                    rootView.player.angle = 3 * Math.PI / 2
                }
            }
            rootView.player.invalidate()
        }
        animator.start()

        return rootView
    }

    override fun onResume() {
        super.onResume()
        // Register this class as a listener for the orientation and accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        xSpeed = PlayerMovement.calculateXSpeed(sensorEvent, 0.1f, PlayerMovement.SPEED_MULTIPLIER_NORMAL)
        ySpeed = PlayerMovement.calculateYSpeed(sensorEvent, 0.1f, PlayerMovement.SPEED_MULTIPLIER_NORMAL)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //TODO: Maybe do something here
    }

}