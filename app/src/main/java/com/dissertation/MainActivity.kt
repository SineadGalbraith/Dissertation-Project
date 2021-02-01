package com.dissertation

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GestureDetectorCompat

class MainActivity : AppCompatActivity() {

    private var gLView: GLSurfaceView? = null
    private lateinit var detector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gLView = SurfaceView(this)
        setContentView(gLView)
    }
}