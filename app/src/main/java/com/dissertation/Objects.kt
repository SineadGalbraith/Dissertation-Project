package com.dissertation

import android.opengl.GLES20
import android.opengl.Matrix

class Objects() {
    var projectionMatrix = FloatArray(16)
    var viewMatrix = FloatArray(16)
    var productMatrix = FloatArray(16)

    fun draw(program: Int, model: Model) {
        val position = GLES20.glGetAttribLocation(program, "position")
        GLES20.glEnableVertexAttribArray(position)
        GLES20.glVertexAttribPointer(position, 3, GLES20.GL_FLOAT, false, 3 * 4, model.vertexBuffer);

        Matrix.frustumM(projectionMatrix, 0,
            -1.0f, 1.0f,
            -1/0f, 1.0f,
            2.0f, 9.0f);

        Matrix.setLookAtM(viewMatrix, 0,
            0.0f, 3.0f, -4.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f);

        Matrix.multiplyMM(productMatrix, 0,
            projectionMatrix, 0,
            viewMatrix, 0);

        val matrix = GLES20.glGetUniformLocation(program, "matrix")
        GLES20.glUniformMatrix4fv(matrix, 1, false, productMatrix, 0)

        GLES20.glDrawElements(GLES20.GL_TRIANGLES,
            model.obj.getVertices().size * 3, GLES20.GL_UNSIGNED_SHORT, model.vertexBuffer);

        GLES20.glDisableVertexAttribArray(position);
    }
}