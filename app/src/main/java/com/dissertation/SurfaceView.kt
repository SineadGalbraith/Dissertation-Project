package com.dissertation

import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent

private const val TOUCH_SCALE_FACTOR: Float = 180.0f / 320f

class SurfaceView(context: Context?) : GLSurfaceView(context) {
    private val renderer: GLRenderer
    private var previousX: Float = 0f
    private var previousY: Float = 0f

    init {
        setEGLContextClientVersion(2)
        renderer = GLRenderer()
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }
    override fun onTouchEvent(e: MotionEvent): Boolean {
        val x: Float = e.x
        val y: Float = e.y

        when (e.action) {
            MotionEvent.ACTION_MOVE -> {

                var dx: Float = x - previousX
                var dy: Float = y - previousY

                if (y > height / 2) {
                    dx *= -1
                }

                if (x < width / 2) {
                    dy *= -1
                }

                renderer.angle += (dx + dy) * TOUCH_SCALE_FACTOR
                requestRender()
            }
        }
        previousX = x
        previousY = y
        return true
    }
}

//    mDetector = GestureDetectorCompat(context, this)
//    mDetector.setOnDoubleTapListener(this)
//    class SurfaceView(context: Context?) : GLSurfaceView(context), GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

//    val gestureDetector: GestureDetector =
//        GestureDetector(this, object : SimpleOnGestureListener() {
//            override fun onDoubleTap(e: MotionEvent): Boolean {
//                // code here
//                return super.onDoubleTap(e)
//            }
//
//            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
//                // code here
//                return super.onSingleTapConfirmed(e)
//            }
//        })

//    private var mDetector: GestureDetectorCompat
//    private lateinit var mGestureDetector : GestureDetector




//
//
//    override fun onDown(event: MotionEvent): Boolean {
////        println("onDown: $event")
//        return true
//    }
//
//    override fun onShowPress(event: MotionEvent) {
////        println("onShowPress: $event")
//    }
//
//    override fun onSingleTapUp(event: MotionEvent): Boolean {
////        println("onSingleTapUp: $event")
//        return true
//    }
//
//    override fun onFling(
//        event1: MotionEvent,
//        event2: MotionEvent,
//        velocityX: Float,
//        velocityY: Float
//    ): Boolean {
////        println("onFling: $event1 $event2")
//        return true
//    }
//
//    override fun onScroll(
//        event1: MotionEvent,
//        event2: MotionEvent,
//        distanceX: Float,
//        distanceY: Float
//    ): Boolean {
////        println("onScroll: $event1 $event2")
//        return true
//    }
//
//    override fun onLongPress(event: MotionEvent) {
////        println("onLongPress: $event")
//    }
//
//    override fun onDoubleTap(event: MotionEvent): Boolean {
////        println("onDoubleTap: $event")
//        return true
//    }
//
//    override fun onDoubleTapEvent(event: MotionEvent): Boolean {
////        println("onDoubleTapEvent: $event")
//        return true
//    }
//
//    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
////        println("onSingleTapConfirmed: $event")
//        renderer.onSingleTap = true
//        renderer.xPos = event.x
//        renderer.yPos = event.y
//
//        println("X: " + event.x + " Y:" + event.y)
//        return true
//    }




//    private const val TOUCH_SCALE_FACTOR: Float = 180.0f / 320f

//    private var mLastTouchX: Float = 0f
//    private var mLastTouchY: Float = 0f
//    private var mPosX: Float = 0f
//    private var mPosY: Float = 0f
//    private var previousX: Float = 0f
//    private var previousY: Float = 0f

//    private var activePointerId = INVALID_POINTER_ID
//    private var mScaleFactor = 1f
//    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
//
//        override fun onScale(detector: ScaleGestureDetector): Boolean {
//            mScaleFactor *= detector.scaleFactor
//
//            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f))
//
//            invalidate()
//            return true
//        }
//    }

//    private val scaleDetector = ScaleGestureDetector(context, scaleListener)

//        scaleDetector.onTouchEvent(e)

//        val x: Float = e.x
//        val y: Float = e.y
//
//        when (e.action) {
//            MotionEvent.ACTION_MOVE -> {
//
//                var dx: Float = x - previousX
//                var dy: Float = y - previousY
//
//                if (y > height / 2) {
//                    dx *= -1
//                }
//
//                if (x < width / 2) {
//                    dy *= -1
//                }
//
////                renderer.angle += (dx + dy) * TOUCH_SCALE_FACTOR
//                renderer.xPos += dx * TOUCH_SCALE_FACTOR
//                renderer.yPos += dy * TOUCH_SCALE_FACTOR
//                requestRender()
//            }
//        }
//        previousX = x
//        previousY = y
//        return true
//    }
//        when (e.actionMasked) {
//            MotionEvent.ACTION_DOWN -> {
//                e.actionMasked.also { pointerIndex ->
//                    mLastTouchX = e.getX(pointerIndex)
//                    mLastTouchY = e.getY(pointerIndex)
////                    renderer.xPos = e.getX(pointerIndex) * TOUCH_SCALE_FACTOR
////                    renderer.yPos = e.getY(pointerIndex) * TOUCH_SCALE_FACTOR
//                }
//                activePointerId = e.getPointerId(0)
//            }
//
//            MotionEvent.ACTION_MOVE -> {
//                val (x: Float, y: Float) =
//                    e.findPointerIndex(activePointerId).let { pointerIndex ->
//                        e.getX(pointerIndex) to
//                                e.getY(pointerIndex)
//                    }
//
//                mPosX += x - mLastTouchX
//                mPosY += y - mLastTouchY
////                renderer.xPos += x - mLastTouchX * TOUCH_SCALE_FACTOR
////                renderer.yPos += y - mLastTouchY * TOUCH_SCALE_FACTOR
//
//                invalidate()
//
//                mLastTouchX = x
//                mLastTouchY = y
//            }
//
//            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//                activePointerId = INVALID_POINTER_ID
//            }
//
//            MotionEvent.ACTION_POINTER_UP -> {
//
//                e.actionIndex.also { pointerIndex ->
//                    e.getPointerId(pointerIndex)
//                        .takeIf { it == activePointerId }
//                        ?.run {
//                            val newPointerIndex = if (pointerIndex == 0) 1 else 0
//                            mLastTouchX = e.getX(newPointerIndex)
//                            mLastTouchY = e.getY(newPointerIndex)
////                            renderer.xPos = e.getX(newPointerIndex) * TOUCH_SCALE_FACTOR
////                            renderer.yPos = e.getY(newPointerIndex) * TOUCH_SCALE_FACTOR
//                            activePointerId = e.getPointerId(newPointerIndex)
//                        }
//                }
//            }
//        }
//        return true
//    }
