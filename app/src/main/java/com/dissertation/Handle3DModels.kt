package com.dissertation

import android.content.Context
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Handle3DModels(context: Context, models: MutableList<Model>) {


    // View and Proj

    init {
//         Convert from txt to string
//        val vertexShaderStream: InputStream = context.resources.openRawResource(R.raw.modelVS)
//        val vertexShaderCode: String = IOUtils.toString(vertexShaderStream, Charset.defaultCharset())
//        vertexShaderStream.close()
//
//        val fragmentShaderStream: InputStream = context.resources.openRawResource(R.raw.modelFS)
//        val fragmentShaderCode: String = IOUtils.toString(fragmentShaderStream, Charset.defaultCharset())
//        fragmentShaderStream.close()

//        GLES20.glCompileShader(vertexShader);
//        GLES20.glCompileShader(fragmentShader);
//
//        // Create shader objects
//        vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER)
//        GLES20.glShaderSource(vertexShader, vertexShaderCode)
//
//        fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER)
//        GLES20.glShaderSource(fragmentShader, fragmentShaderCode)

        for (model in models)  {
            val vertexBuffer = ByteBuffer.allocateDirect(model.obj.getVertices().size * 3 * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
            vertexBuffer.put(model.obj.getVertices()).position(0)
            model.vertexBuffer = vertexBuffer

            val normalBuffer = ByteBuffer.allocateDirect(model.obj.getNormals().size * 3 * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer()
            normalBuffer.put(model.obj.getNormals()).position(0)
            model.normalBuffer = normalBuffer

            val texCoordsBuffer = ByteBuffer.allocateDirect(model.obj.getTexCoords().size * 3 * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer()
            texCoordsBuffer.put(model.obj.getTexCoords()).position(0)
            model.texCoordsBuffer = texCoordsBuffer

            model.meshNum = model.obj.getNumFaces()

//            model.vertexShader = vertexShader
//            model.fragmentShader = fragmentShader
        }

//        program = GLES20.glCreateProgram();
//        GLES20.glAttachShader(program, vertexShader);
//        GLES20.glAttachShader(program, fragmentShader);
//
//        GLES20.glLinkProgram(program);
//        GLES20.glUseProgram(program);
    }
}