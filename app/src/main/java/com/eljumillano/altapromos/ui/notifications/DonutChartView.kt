package com.eljumillano.altapromos.ui.notifications

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.eljumillano.altapromos.R

class DonutChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 40f
        strokeCap = Paint.Cap.ROUND
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 40f
        color = 0xFFE0E0E0.toInt()
    }

    private val rectF = RectF()
    private var segments = listOf<ChartSegment>()

    data class ChartSegment(
        val percentage: Float,
        val color: Int
    )

    fun setData(segments: List<ChartSegment>) {
        this.segments = segments
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (segments.isEmpty()) {
            return
        }

        val size = minOf(width, height).toFloat()
        val padding = paint.strokeWidth / 2
        rectF.set(padding, padding, size - padding, size - padding)

        // Dibujar círculo de fondo
        canvas.drawCircle(
            size / 2,
            size / 2,
            (size - paint.strokeWidth) / 2,
            backgroundPaint
        )

        // Dibujar segmentos
        var startAngle = -90f // Empezar desde arriba

        segments.forEach { segment ->
            paint.color = segment.color
            val sweepAngle = 360f * segment.percentage / 100f

            canvas.drawArc(
                rectF,
                startAngle,
                sweepAngle,
                false,
                paint
            )

            startAngle += sweepAngle
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = minOf(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }
}

