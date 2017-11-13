package com.jonahstarling.lostlantern

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by jonah on 11/11/17.
 */
class PlayerView : View {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var centerX = 0f
    var centerY = 0f
    var radius = 0f
    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        centerX = canvas.width/2f
        centerY = canvas.height/2f
        radius = centerX
        paint.color = Color.parseColor("#7C4DFF")
        canvas.drawCircle(centerX, centerY, radius, paint)
    }

}