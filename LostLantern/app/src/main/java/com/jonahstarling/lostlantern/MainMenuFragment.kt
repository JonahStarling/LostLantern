package com.jonahstarling.lostlantern

import android.app.Fragment
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.hardware.SensorManager
import kotlinx.android.synthetic.main.fragment_main_menu.view.*
import android.animation.TimeAnimator
import android.animation.ValueAnimator
import android.os.Handler
import kotlinx.android.synthetic.main.fragment_main_menu.*
import java.util.*


/**
 * Created by Jonah Starling on 11/11/17.
 *
 * Main Menu Fragment:
 *     -
 *
 * Relevant Files:
 *     -
 */

class MainMenuFragment : Fragment(), SensorEventListener {

    private lateinit var rootView: View
    private lateinit var sensorManager: SensorManager
    private var phoneLeveled: Boolean = false
    private var xSpeed: Float = 0.0f
    private var ySpeed: Float = 0.0f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.fragment_main_menu, container, false)
        sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val animator = TimeAnimator()
        animator.setTimeListener { _, _, _ ->
            rootView.player.x += xSpeed
            rootView.player.y += ySpeed
        }
        animator.start()

        val handler = Handler()
        handler.postDelayed({
            if (!phoneLeveled) {
                val hintTextAnimator = ValueAnimator.ofInt(0, 1)
                hintTextAnimator.duration = 1000
                hintTextAnimator.addUpdateListener { rootView.hintText.alpha = hintTextAnimator.animatedFraction }
                hintTextAnimator.start()
            }
        }, 2000)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        // unregister listener
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        if (phoneLeveled) {
            calculateXSpeed(sensorEvent)
            calculateYSpeed(sensorEvent)
        } else {
            phoneLeveled = checkPhoneLeveled(sensorEvent)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //TODO: Maybe do something here
    }

    private fun checkPhoneLeveled(sensorEvent: SensorEvent): Boolean {
        var checkX = false
        var checkY = false
        if (sensorEvent.values[0] < 1.0f && sensorEvent.values[0] > -1.0f) {
            checkX = true
        }
        if (sensorEvent.values[1] < 1.0f && sensorEvent.values[1] > -1.0f) {
            checkY = true
        }
        if (checkX && checkY) {
            if (rootView.hintText.alpha != 0f) {
                val hintTextAnimator = ValueAnimator.ofFloat(rootView.hintText.alpha, 0f)
                hintTextAnimator.duration = 1000
                hintTextAnimator.addUpdateListener { rootView.hintText.alpha = hintTextAnimator.animatedValue as Float }
                hintTextAnimator.start()
            }
        }
        return checkX && checkY
    }

    private fun calculateXSpeed(sensorEvent: SensorEvent) {
        xSpeed = when {
            sensorEvent.values[0] > 1.5f -> {
                -sensorEvent.values[0]
            }
            sensorEvent.values[0] < -1.5f -> {
                -sensorEvent.values[0]
            }
            else -> {
                0.0f
            }
        }
    }

    private fun calculateYSpeed(sensorEvent: SensorEvent) {
        ySpeed = when {
            sensorEvent.values[1] > 1.5f -> {
                sensorEvent.values[1]
            }
            sensorEvent.values[1] < -1.5f -> {
                sensorEvent.values[1]
            }
            else -> {
                0.0f
            }
        }
    }
}