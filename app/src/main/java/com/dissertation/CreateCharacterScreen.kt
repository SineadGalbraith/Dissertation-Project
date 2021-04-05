package com.dissertation

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class CreateCharacterScreen : AppCompatActivity() , DialogInterface.OnDismissListener {
    private val introductionDialog = CreateCharacterIntroductionParentFragment()

    // Surface View
    private lateinit var characterSurfaceView : GLSurfaceView
    private var finishedButton : ImageButton? = null
    // List of Models
    private var ball : Ball? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_character_screen)
        introductionDialog.show(supportFragmentManager, "enter_name_introduction")
        finishedButton = findViewById(R.id.createCharacterScreenFinishedButton)

        characterSurfaceView = findViewById(R.id.characterSurfaceView)
        characterSurfaceView.setEGLContextClientVersion(2)
        characterSurfaceView.setRenderer(object : GLSurfaceView.Renderer {
            override fun onDrawFrame(gl: GL10?) {
                ball?.draw()
            }

            override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
                GLES20.glViewport(0,0, width, height);
            }

            override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
                characterSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY;
                ball = Ball(applicationContext);
            } // More code goes here
        })

        finishedButton?.setOnClickListener {
            val intent = Intent(this, GameStyleChoiceScreen::class.java)
            startActivity(intent)
        }


//        val ballObj = ObjLoader(this, "src/main/assets/ball.obj")
//        val ballModel = Model(ballObj)
//        modelList.add(ballModel)
//        Handle3DModels(this, modelList)
//        Ball(this, ballModel)

//        characterSurfaceView.setRenderer(CreateCharacterGLRenderer(this, ballModel))
//        characterSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }
//
    override fun onDismiss(dialog: DialogInterface?) {
       updateName()
    }

    private fun updateName() {
        val nameTextView = findViewById<TextView>(R.id.createCharacterScreenNameLabelTextView)
        val sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE)
        val characterName = sharedPreferences.getString("username", "")
        nameTextView.visibility = View.VISIBLE
        nameTextView.text = characterName
        showNameOverlay()
    }

    private fun showNameOverlay() {
        val view: View = findViewById(R.id.createCharacterScreenNameLabelTextView)

        SimpleTooltip.Builder(this)
            .anchorView(view)
            .text(R.string.createCharacterScreenOverlayText1)
            .gravity(Gravity.BOTTOM)
            .dismissOnInsideTouch(false)
            .animated(true)
            .transparentOverlay(false)
            .showArrow(false)
            .overlayWindowBackgroundColor(Color.BLACK)
//            .onDismissListener {
//                buildCharacter()
//            }
            .build()
            .show()
    }
//
//    private fun buildCharacter() {
////        choicesDialog.show(supportFragmentManager, "avatar_choices")
//    }

}