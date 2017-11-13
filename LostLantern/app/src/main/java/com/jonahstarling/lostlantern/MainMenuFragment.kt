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


/**
 * Created by jonah on 11/11/17.
 */

class MainMenuFragment : Fragment(), SensorEventListener {

    private lateinit var rootView: View
    private lateinit var sensorManager: SensorManager
    private var phoneLeveled: Boolean = false
    private var movingUp: Boolean = false
    private var movingBack: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.fragment_main_menu, container, false)
        sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val animator = TimeAnimator()
        animator.setTimeListener { _, _, _ ->
            if (movingUp) {
                rootView.player.y -= 5
            } else if (movingBack) {
                rootView.player.y += 5
            }
        }
        animator.start()
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
            if (sensorEvent.values[1] > 1.5f) {
                movingBack = true
                movingUp = false
            } else if (sensorEvent.values[1] < -1.5f) {
                movingUp = true
                movingBack = false
            } else {
                movingUp = false
                movingBack = false
            }
        } else {
            phoneLeveled = checkPhoneLeveled(sensorEvent)
            Log.d("phoneLeveled", phoneLeveled.toString())
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
        return checkX && checkY
    }
}