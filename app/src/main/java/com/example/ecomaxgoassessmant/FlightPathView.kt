package com.example.ecomaxgoassessmant


import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class FlightPathView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paintPath = Paint().apply {
        color = Color.RED
        strokeWidth = 5f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val paintMarker = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val path = Path()
    private var markerX = 0f
    private var markerY = 0f

    private var animator: ValueAnimator? = null

    private var worldMapBitmap: Bitmap? = null

    init {
        // Load your world map image
        worldMapBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.group_map)
    }

    fun setFlightPath(startLat: Float, startLng: Float, endLat: Float, endLng: Float) {
        worldMapBitmap?.let { bitmap ->
            val startPoint = latLngToPoint(startLat, startLng, bitmap)
            val endPoint = latLngToPoint(endLat, endLng, bitmap)

            // Create a quadratic Bézier curve
            path.reset()
            val controlX = (startPoint.x + endPoint.x) / 2
            val controlY = min(startPoint.y, endPoint.y) - 200 // Adjust for curve height

            path.moveTo(startPoint.x, startPoint.y)
            path.quadTo(controlX, controlY, endPoint.x, endPoint.y)

            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the world map
        worldMapBitmap?.let {
            canvas.drawBitmap(it, null, Rect(0, 0, width, height), null)
        }

        // Draw the flight path
        canvas.drawPath(path, paintPath)

        // Draw the moving marker
        canvas.drawCircle(markerX, markerY, 10f, paintMarker)
    }

    fun startFlightAnimation() {
        val pathMeasure = PathMeasure(path, false)
        val pathLength = pathMeasure.length

        animator = ValueAnimator.ofFloat(0f, pathLength).apply {
            duration = 5000 // 5 seconds animation
            addUpdateListener { animation ->
                val distance = animation.animatedValue as Float
                val position = FloatArray(2)
                pathMeasure.getPosTan(distance, position, null)

                markerX = position[0]
                markerY = position[1]
                invalidate()
            }
        }
        animator?.start()
    }

    private fun latLngToPoint(lat: Float, lng: Float, bitmap: Bitmap): PointF {
        // Map latitude and longitude to bitmap coordinates
        val x = (lng + 180) * (bitmap.width / 360f) // Convert longitude to x
        val y = (90 - lat) * (bitmap.height / 180f) // Convert latitude to y
        return PointF(x, y)
    }
}

