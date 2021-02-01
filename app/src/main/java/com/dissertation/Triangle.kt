package com.dissertation

import android.opengl.GLES20
import android.opengl.Matrix
import com.dissertation.GLRenderer.Companion.loadShader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

private const val COORDS_PER_VERTEX = 3
var triangleCoords = floatArrayOf(
    0.0f, 0.622008459f, 0.0f,
    -0.5f, -0.311004243f, 0.0f,
    0.5f, -0.311004243f, 0.0f
)

private const val vertexShaderCode : String = "uniform mat4 uMVPMatrix;" +
        "attribute vec4 vPosition;" +
        "void main() {" +
        "gl_Position = uMVPMatrix * vPosition;" +
        "}"
private const val fragmentShaderCode : String = "precision mediump float;" + "uniform vec4 vColor;" +
        "void main() {" +
        "gl_FragColor = vColor;" +
        "}";

class Triangle {

    private var vertexBuffer: FloatBuffer =
        ByteBuffer.allocateDirect(triangleCoords.size * 4).run {
            order(ByteOrder.nativeOrder())
            asFloatBuffer().apply {
                put(triangleCoords)
                position(0)
            }
        }


    private val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
    private var mProgram = 0

    private var positionHandle: Int = 0
    private var mColorHandle: Int = 0

    private var vertexCount= triangleCoords.size / COORDS_PER_VERTEX
    private var vertexStride = COORDS_PER_VERTEX * 4

    private var vPMatrixHandle: Int = 0

    init {
        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        mProgram = GLES20.glCreateProgram().also {
            GLES20.glAttachShader(it, vertexShader)
            GLES20.glAttachShader(it, fragmentShader)
            GLES20.glLinkProgram(it)
        }
    }

    fun draw(mvpMatrix: FloatArray) {
        GLES20.glUseProgram(mProgram)
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition").also {
            GLES20.glEnableVertexAttribArray(it)
            GLES20.glVertexAttribPointer(
                it,
                COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer
            )

            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->

                GLES20.glUniform4fv(colorHandle, 1, color, 0)
            }

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)

            GLES20.glDisableVertexAttribArray(it)
        }

        vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
        GLES20.glDisableVertexAttribArray(positionHandle)
    }
}