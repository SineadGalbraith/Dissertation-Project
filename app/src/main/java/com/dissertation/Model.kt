package com.dissertation

import java.nio.FloatBuffer

data class Model(val obj : ObjLoader) {
    lateinit var vertexBuffer : FloatBuffer
    lateinit var normalBuffer : FloatBuffer
    lateinit var texCoordsBuffer : FloatBuffer
    var meshNum : Int = 0
}