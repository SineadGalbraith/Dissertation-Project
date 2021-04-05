package com.dissertation

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import org.apache.commons.io.IOUtils
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import java.nio.charset.Charset


class Ball(context: Context) {
    private var vertices: MutableList<String>? = null
    private var faces: MutableList<String>? = null
    private var verticesBuffer: FloatBuffer? = null
    private var facesBuffer: ShortBuffer? = null
    private var program: Int= 0

    var projectionMatrix = FloatArray(16)
    var viewMatrix = FloatArray(16)

    var productMatrix = FloatArray(16)

    init {
        vertices = mutableListOf()
        faces = mutableListOf()

        val reader: BufferedReader?
        val `in` = InputStreamReader(context.assets.open("ball.obj"))
        reader = BufferedReader(`in`)

        // read file until EOF
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            val parts = line?.split(" ".toRegex())?.toTypedArray()
            when (parts?.get(0)) {
                "v" -> {
                    line?.let { vertices!!.add(it) }
                }
                "f" -> {
                    // faces: vertex/texture/normal
                    faces!!.add(parts[1])
                    faces!!.add(parts[2])
                    faces!!.add(parts[3])
                }
            }
        }
        val buffer1: ByteBuffer = ByteBuffer.allocateDirect(vertices!!.size * 3 * 4)
        buffer1.order(ByteOrder.nativeOrder())
        verticesBuffer = buffer1.asFloatBuffer()

        val buffer2 = ByteBuffer.allocateDirect(faces!!.size * 3 * 2)
        buffer2.order(ByteOrder.nativeOrder())
        facesBuffer = buffer2.asShortBuffer()

        for (vertex in vertices!!) {
            val coords = vertex.split(" ".toRegex()).toTypedArray() // Split by space
            val x = coords[1].toFloat()
            val y = coords[2].toFloat()
            val z = coords[3].toFloat()
            verticesBuffer?.put(x)
            verticesBuffer?.put(y)
            verticesBuffer?.put(z)
        }
        verticesBuffer?.position(0);

        for (face in faces!!) {
            val vertexIndices =
                face.split("/".toRegex()).toTypedArray()
            val vertex1 = vertexIndices[0].toShort()
            val vertex2 = vertexIndices[1].toShort()
            val vertex3 = vertexIndices[2].toShort()
            facesBuffer?.put((vertex1 - 1).toShort())
            facesBuffer?.put((vertex2 - 1).toShort())
            facesBuffer?.put((vertex3 - 1).toShort())
        }
        facesBuffer?.position(0)

        val vertexShaderStream: InputStream =
            context.resources.openRawResource(R.raw.model_vs)
        val vertexShaderCode: String = IOUtils.toString(vertexShaderStream, Charset.defaultCharset())
        vertexShaderStream.close()

        val fragmentShaderStream: InputStream =
            context.resources.openRawResource(R.raw.model_fs)
        val fragmentShaderCode: String = IOUtils.toString(fragmentShaderStream, Charset.defaultCharset())
        fragmentShaderStream.close()

        val vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER)
        GLES20.glShaderSource(vertexShader, vertexShaderCode)

        val fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER)
        GLES20.glShaderSource(fragmentShader, fragmentShaderCode)

        GLES20.glCompileShader(vertexShader)
        GLES20.glCompileShader(fragmentShader)

        program = GLES20.glCreateProgram()
        GLES20.glAttachShader(program, vertexShader)
        GLES20.glAttachShader(program, fragmentShader)

        GLES20.glLinkProgram(program);
        GLES20.glUseProgram(program);
    }

    fun draw() {
        val position = GLES20.glGetAttribLocation(program, "position")
        GLES20.glEnableVertexAttribArray(position)

        GLES20.glVertexAttribPointer(position,
            3, GLES20.GL_FLOAT, false, 3 * 4, verticesBuffer)

        Matrix.frustumM(projectionMatrix, 0,
            -1f, 1f,
            -1f, 1f,
            2f, 9f)

        Matrix.setLookAtM(viewMatrix, 0,
            0f, 3f, -4f,
            0f, 0f, 0f,
            0f, 1f, 0f)

        Matrix.multiplyMM(productMatrix, 0,
            projectionMatrix, 0,
            viewMatrix, 0);

        val matrix = GLES20.glGetUniformLocation(program, "matrix")
        GLES20.glUniformMatrix4fv(matrix, 1, false, productMatrix, 0)

        GLES20.glDrawElements(GLES20.GL_TRIANGLES,
            (faces?.size ?: 0) * 3, GLES20.GL_UNSIGNED_SHORT, facesBuffer);

        GLES20.glDisableVertexAttribArray(position);
    }
}