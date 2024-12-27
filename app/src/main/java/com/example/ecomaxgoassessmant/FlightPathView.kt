package com.example.ecomaxgoassessmant

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class FlightPathView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paintPath = Paint().apply {
        color = Color.parseColor("#2E2E2E")
        strokeWidth = 5f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val paintGreenDot = Paint().apply {
        color = Color.parseColor("#CFE550")
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val path = Path()
    private var markerX = 0f
    private var markerY = 0f

    private var animator: ValueAnimator? = null

    private var worldMapBitmap: Bitmap? = null

    // Declare these variables as class properties
    private var startLat: Float = 0f
    private var startLng: Float = 0f
    private var endLat: Float = 0f
    private var endLng: Float = 0f

    init {
        // Load your world map image
        worldMapBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.group_map)
    }

    // This method sets the flight path and stores the start and end coordinates
    fun setFlightPath(startLat: Float, startLng: Float, endLat: Float, endLng: Float) {
        this.startLat = startLat
        this.startLng = startLng
        this.endLat = endLat
        this.endLng = endLng

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

        // Draw the two green dots at the start and end of the curve
        worldMapBitmap?.let { bitmap ->
            val startPoint = latLngToPoint(startLat, startLng, bitmap)
            val endPoint = latLngToPoint(endLat, endLng, bitmap)

            // Draw the green dot at the starting point
            canvas.drawCircle(startPoint.x, startPoint.y, 10f, paintGreenDot)

            // Draw the green dot at the ending point
            canvas.drawCircle(endPoint.x, endPoint.y, 10f, paintGreenDot)
        }

        // Draw the moving marker as an icon (instead of the blue circle)
        val markerBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.plane_marker) // Your custom icon (e.g., plane or pin)
        val markerWidth = markerBitmap.width
        val markerHeight = markerBitmap.height

        // Draw the icon at the current position of the marker
        canvas.drawBitmap(markerBitmap, markerX - markerWidth / 2, markerY - markerHeight / 2, null)
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
