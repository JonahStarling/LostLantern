package com.jonahstarling.lostlantern

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * Created by jonah on 11/11/17.
 */
class PlayerView : View {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var centerX = 0f
    private var centerY = 0f
    private var radius = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    var angle: Double = Math.PI / 2

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        centerX = canvas.width/2f
        centerY = canvas.height/2f
        radius = canvas.width/4f

        paint.color = Color.parseColor("#7C4DFF")
        paint.strokeWidth = 0.1f
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.isAntiAlias = true

        path.reset()
        val topX = (centerX + (radius * 2.0) * Math.cos(angle)).toFloat()
        val topY = (centerY + (radius * 2.0) * Math.sin(angle)).toFloat()
        val leftX = (centerX + (radius * 2.0 - radius/2) * Math.cos(angle-0.25)).toFloat()
        val leftY = (centerY + (radius * 2.0 - radius/2) * Math.sin(angle-0.25)).toFloat()
        val rightX = (centerX + (radius * 2.0 - radius/2) * Math.cos(angle+0.25)).toFloat()
        val rightY = (centerY + (radius * 2.0 - radius/2) * Math.sin(angle+0.25)).toFloat()
        path.fillType = Path.FillType.EVEN_ODD
        path.moveTo(topX, topY)
        path.lineTo(leftX, leftY)
        path.lineTo(rightX, rightY)
        path.lineTo(topX, topY)
        path.close()

        canvas.drawPath(path, paint)
        canvas.drawCircle(centerX, centerY, radius, paint)
    }

}