// Taken from https://stackoverflow.com/questions/41012719/how-to-load-and-display-obj-file-in-android-with-opengl-es-2
package com.dissertation

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class ObjLoader(context: Context, file: String) {
    private val meshNumFaces: Int
    private val meshVertices: FloatArray
    private val meshNormals: FloatArray
    private val meshTextureCoordinates: FloatArray

    init {
        val vertices: Vector<Float> = Vector()
        val normals: Vector<Float> = Vector()
        val textures: Vector<Float> = Vector()
        val faces: Vector<String> = Vector()
        var reader: BufferedReader? = null
        try {
            val `in` = InputStreamReader(context.assets.open(file))
            reader = BufferedReader(`in`)

            // read file until EOF
            var line: String = ""
            while (reader.readLine().also { line = it } != null) {
                val parts = line.split(" ".toRegex()).toTypedArray()
                when (parts[0]) {
                    "v" -> {
                        // vertices
                        vertices.add(java.lang.Float.valueOf(parts[1]))
                        vertices.add(java.lang.Float.valueOf(parts[2]))
                        vertices.add(java.lang.Float.valueOf(parts[3]))
                    }
                    "vt" -> {
                        // textures
                        textures.add(java.lang.Float.valueOf(parts[1]))
                        textures.add(java.lang.Float.valueOf(parts[2]))
                    }
                    "vn" -> {
                        // normals
                        normals.add(java.lang.Float.valueOf(parts[1]))
                        normals.add(java.lang.Float.valueOf(parts[2]))
                        normals.add(java.lang.Float.valueOf(parts[3]))
                    }
                    "f" -> {
                        // faces: vertex/texture/normal
                        faces.add(parts[1])
                        faces.add(parts[2])
                        faces.add(parts[3])
                    }
                }
            }
        } catch (e: IOException) {
            // cannot load or read file
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    //log the exception
                }
            }
        }
        meshNumFaces = faces.size
        this.meshNormals = FloatArray(meshNumFaces * 3)
        meshTextureCoordinates = FloatArray(meshNumFaces * 2)
        meshVertices = FloatArray(meshNumFaces * 3)
        var positionIndex = 0
        var normalIndex = 0
        var textureIndex = 0
        for (face in faces) {
            val parts = face.split("/".toRegex()).toTypedArray()
            var index = 3 * (parts[0].toShort() - 1)
            meshVertices[positionIndex++] = vertices[index++]
            meshVertices[positionIndex++] = vertices[index++]
            meshVertices[positionIndex++] = vertices[index]
            index = 2 * (parts[1].toShort() - 1)
            meshTextureCoordinates[normalIndex++] = textures[index++]
            // NOTE: Bitmap gets y-inverted
            meshTextureCoordinates[normalIndex++] = 1 - textures[index]
            index = 3 * (parts[2].toShort() - 1)
            this.meshNormals[textureIndex++] = normals[index++]
            this.meshNormals[textureIndex++] = normals[index++]
            this.meshNormals[textureIndex++] = normals[index]
        }
    }

    fun getVertices() : FloatArray {
        return this.meshVertices
    }

    fun getNormals() : FloatArray {
        return this.meshNormals
    }

    fun getTexCoords() : FloatArray {
        return this.meshTextureCoordinates
    }

    fun getNumFaces() : Int {
        return this.meshNumFaces
    }
}