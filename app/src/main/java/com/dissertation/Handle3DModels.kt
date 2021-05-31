/* This class is not used within the current implementation of the application.
This class is one of several mentioned within Section 4.5.3 of the dissertation report.
*/
package com.dissertation

import android.content.Context
import android.opengl.GLES20
import org.apache.commons.io.IOUtils
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.Charset

class Handle3DModels(context: Context, models: MutableList<Model>) {
    /*
    Create the Shaders needed for drawing the models. Access the Shader Source files from the Assets
    folders, create the Shaders using OpenGL ES and compile them.

    For each model in the list of models, create the object buffers for the vertices, normals and
    texture coordinates. Save the buffers, number of faces and shaders in the Model data class
    for the particular object.

    Link and use the shader programs.
     */
    init {
        val vertexShaderStream: InputStream = context.resources.openRawResource(R.raw.model_vs)
        val vertexShaderCode: String = IOUtils.toString(vertexShaderStream, Charset.defaultCharset())
        vertexShaderStream.close()

        val fragmentShaderStream: InputStream = context.resources.openRawResource(R.raw.model_fs)
        val fragmentShaderCode: String = IOUtils.toString(fragmentShaderStream, Charset.defaultCharset())
        fragmentShaderStream.close()

        val vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER)
        GLES20.glShaderSource(vertexShader, vertexShaderCode)

        val fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER)
        GLES20.glShaderSource(fragmentShader, fragmentShaderCode)

        GLES20.glCompileShader(vertexShader);
        GLES20.glCompileShader(fragmentShader);

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
            model.vertexShader = vertexShader
            model.fragmentShader = fragmentShader
        }

        val program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);

        GLES20.glLinkProgram(program);
        GLES20.glUseProgram(program);
    }
}