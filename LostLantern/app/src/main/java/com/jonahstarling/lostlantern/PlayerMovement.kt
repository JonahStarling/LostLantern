package com.jonahstarling.lostlantern

import android.hardware.SensorEvent

/**
 * Created by jonah on 11/14/17.
 */
class PlayerMovement {

    companion object {

        val SPEED_MULTIPLIER_VERY_SLOW: Float = 0.5f
        val SPEED_MULTIPLIER_SLOW: Float = 1.0f
        val SPEED_MULTIPLIER_NORMAL: Float = 2.0f
        val SPEED_MULTIPLIER_FAST: Float = 3.0f
        val SPEED_MULTIPLIER_VERY_FAST: Float = 4.0f

        fun calculateXSpeed(sensorEvent: SensorEvent, lowPass: Float, speedMultiplier: Float): Float {
            return when {
                sensorEvent.values[0] > lowPass -> (-sensorEvent.values[0] + lowPass) * speedMultiplier
                sensorEvent.values[0] < -lowPass -> (-sensorEvent.values[0] - lowPass) * speedMultiplier
                else -> 0.0f
            }
        }

        fun calculateYSpeed(sensorEvent: SensorEvent, lowPass: Float, speedMultiplier: Float): Float {
            return when {
                sensorEvent.values[1] > lowPass -> (sensorEvent.values[1] - lowPass) * speedMultiplier
                sensorEvent.values[1] < -lowPass -> (sensorEvent.values[1] + lowPass) * speedMultiplier
                else -> 0.0f
            }
        }

    }

}