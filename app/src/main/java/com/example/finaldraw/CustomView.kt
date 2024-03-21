package com.example.finaldraw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CustomView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private lateinit var bitmap: Bitmap
    private lateinit var bitmapCanvas: Canvas
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    var readyToDrawCircle = false
    private var circleColor: Int = Color.BLACK

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = 4f
        // Removed the drawing operation from init
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // Initialize bitmap and canvas with the view's dimensions
        if (::bitmap.isInitialized) bitmap.recycle() // Recycle the old bitmap if it exists
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmapCanvas = Canvas(bitmap)
        bitmapCanvas.drawColor(Color.WHITE)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (readyToDrawCircle) {
                    drawCircleAtPosition(x, y, circleColor)
                    readyToDrawCircle = false
                } else {
                    path.moveTo(x, y)
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (!readyToDrawCircle) {
                    path.lineTo(x, y)
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (!readyToDrawCircle) {
                    bitmapCanvas.drawPath(path, paint)
                    path.reset()
                    invalidate()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun drawCircleAtPosition(x: Float, y: Float, color: Int) {
        val localPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = color
            style = Paint.Style.FILL
        }
        bitmapCanvas.drawCircle(x, y, 50f, localPaint)
        invalidate()
    }

    fun prepareForCircleDrawing(ready: Boolean, color: Int) {
        this.readyToDrawCircle = ready
        this.circleColor = color
    }

    fun changePenColor(color: Int) {
        paint.color = color
    }

    fun changePenSize(size: Float) {
        paint.strokeWidth = size
        // Invalidate the view to ensure the change is reflected
        invalidate()
    }

    // Save stuff
    fun saveDrawingToFile(context: Context): String {
        // Generate a unique file name or use a specific one
        val fileName = "drawing_${System.currentTimeMillis()}.png"
        val file = File(context.filesDir, fileName)
        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return fileName // Return the file name for storing in the database
    }

//    fun loadBitmapFromFile(context: Context, fileName: String) {
//        val file = File(context.filesDir, fileName)
//        if (file.exists()) {
//            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
//            // Set the bitmap to the current view
//            this.bitmap = bitmap
//            bitmapCanvas = Canvas(this.bitmap)
//            invalidate() // Redraw the view
//        }
//    }

    fun loadBitmapFromFile(context: Context, fileName: String) {
        val file = File(context.filesDir, fileName)
        if (file.exists()) {
            val options = BitmapFactory.Options().apply {
                inMutable = true
            }
            val newBitmap = BitmapFactory.decodeFile(file.absolutePath, options)
            if (newBitmap != null) {
                // Recycle the old bitmap if it is initialized and different from the new bitmap
                if (::bitmap.isInitialized && bitmap != newBitmap && !bitmap.isRecycled) {
                    bitmap.recycle()
                }
                bitmap = newBitmap
                bitmapCanvas = Canvas(bitmap)
                invalidate()
            } else {
                Log.e("CustomView", "Failed to decode bitmap from file: $fileName")
            }
        }
    }
}