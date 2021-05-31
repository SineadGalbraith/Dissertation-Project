/* This class is not used within the current implementation of the application.
This class is one of several mentioned within Section 4.5.3 of the dissertation report.
*/
package com.dissertation

import java.nio.FloatBuffer

/*
Data class that takes in an object of type Object Loader. Initializes mutable values for the buffers,
number of faces and shaders associated with the model object.
 */
data class Model(val obj : ObjLoader) {
    lateinit var vertexBuffer : FloatBuffer
    lateinit var normalBuffer : FloatBuffer
    lateinit var texCoordsBuffer : FloatBuffer
    var meshNum : Int = 0
    var vertexShader : Int = 0
    var fragmentShader : Int = 0
}