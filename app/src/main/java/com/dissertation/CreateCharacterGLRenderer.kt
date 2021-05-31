/* This class is not used within the current implementation of the application.
This class is one of several mentioned within Section 4.5.3 of the dissertation report.
*/
package com.dissertation

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class CreateCharacterGLRenderer(private var context: Context, private var ballModel: Model) : GLSurfaceView.Renderer {
    lateinit var ball : Ball

    /*
    The class implements the three abstract functions from the GL Renderer class.
     */

    /*
    This function creates a new instance of the Ball class.
     */
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        ball = Ball(context)
    }

    /*
    This function creates the viewport used within the Surface View.
     */
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0,0, width, height);
    }

    /*
    This function calls the Draw function associated with the Ball class.
     */
    override fun onDrawFrame(gl: GL10?) {
        ball.draw()
    }

    /*
    This companion object contains a function loadShader which can be used to compile shaders from
    other locations in the application.
     */
    companion object {
        fun loadShader(type: Int, shaderCode: String): Int {
            return GLES20.glCreateShader(type).also { shader ->
                GLES20.glShaderSource(shader, shaderCode)
                GLES20.glCompileShader(shader)
            }
        }
    }
}