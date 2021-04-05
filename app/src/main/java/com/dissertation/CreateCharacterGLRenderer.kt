package com.dissertation

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class CreateCharacterGLRenderer(private var context: Context, private var ballModel: Model) : GLSurfaceView.Renderer {
    override fun onDrawFrame(gl: GL10?) {
        TODO("Not yet implemented")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        TODO("Not yet implemented")
    }
//    lateinit var ball : Ball
//
//    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
//        ball = Ball(context, ballModel)
//    }
//
//    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
//        GLES20.glViewport(0,0, width, height);
//    }
//
//    override fun onDrawFrame(gl: GL10?) {
//        ball.draw()
//    }
//
//    companion object {
//        fun loadShader(type: Int, shaderCode: String): Int {
//            return GLES20.glCreateShader(type).also { shader ->
//                GLES20.glShaderSource(shader, shaderCode)
//                GLES20.glCompileShader(shader)
//            }
//        }
//    }
}