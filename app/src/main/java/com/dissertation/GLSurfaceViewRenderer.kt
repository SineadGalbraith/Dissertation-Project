package com.dissertation

import android.opengl.GLSurfaceView
//import androidx.test.core.app.ApplicationProvider.getApplicationContext
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class GLSurfaceViewRenderer(glSurfaceView: GLSurfaceView) : GLSurfaceView.Renderer{
    override fun onDrawFrame(gl: GL10?) {
        TODO("Not yet implemented")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        TODO("Not yet implemented")
    }
//    private lateinit var objects: Objects;
//    override fun onDrawFrame(gl: GL10?) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
//        objects = Objects()
//        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//        torus = new Torus(getApplicationContext());
//    }
//
//    override fun onSurfaceCreated(
//        gl10: GL10?,
//        eglConfig: EGLConfig?
//    ) {
//        mySurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY)
//        torus = Torus(ApplicationProvider.getApplicationContext())
//    }
}