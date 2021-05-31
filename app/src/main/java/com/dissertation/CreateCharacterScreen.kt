/* This class contains the code used for displaying all aspects of the Create Character Screen. */
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
    private var finishedButton : ImageButton? = null
    private var glSurfaceView : GLSurfaceView? = null
    private var ball : Ball? = null

    /*
    When the class is called, displayed the Create Character Screen XML layout file. Store a
    reference to the Surface View within this XML file and set the version of OpenGL ES used within
    the application.

    A GL Renderer class is instantiated and used within this class also to display the Ball model
    on the screen.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_character_screen)
        glSurfaceView = findViewById(R.id.characterSurfaceView)
        glSurfaceView?.setEGLContextClientVersion(2);

        /*
        Create a new instance of the GL Renderer class and implement the abstract functions.
         */
        glSurfaceView?.setRenderer(object : GLSurfaceView.Renderer {
            /*
             This function calls the Draw function associated with the Ball class.
            */
            override fun onDrawFrame(gl: GL10?) {
                ball?.draw()
            }

            /*
             This function creates the viewport used within the Surface View.
            */
            override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
                GLES20.glViewport(0,0, width, height)
            }

            /*
            This function sets the render mode of the Surface View to only update when the view
            changes. It also creates a new instance of the Ball class.
             */
            override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
                glSurfaceView?.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
                ball = Ball(applicationContext)
            }
        })

        /*
        When the class is called, show the Dialog Fragment.

        Create the Shared Preference for the score.

        Store a reference to the "Finished" button and add the functionality to change to the
        Game Style Choice Screen.
         */
        introductionDialog.show(supportFragmentManager, "enter_name_introduction")

        createScoreSharedPreference()

        finishedButton = findViewById(R.id.confirmChoiceButton)
        finishedButton?.setOnClickListener {
            val intent = Intent(this, GameStyleChoiceScreen::class.java)
            startActivity(intent)
        }
    }

    /*
    When the Dialog Interface is dismissed, update the TextView to display the newly entered
    character name.
     */
    override fun onDismiss(dialog: DialogInterface?) {
       updateName()
    }

    /*
    When the Dialog Fragment has been dismissed, access the "username" Shared Preference and set
    the "Name" textView in the screen as the value of the "username" Shared Preference.
     */
    private fun updateName() {
        val nameTextView = findViewById<TextView>(R.id.createCharacterScreenNameLabelTextView)
        val sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE)
        val characterName = sharedPreferences.getString("username", "")
        nameTextView.visibility = View.VISIBLE
        nameTextView.text = characterName
        showNameOverlay()
    }

    /*
    Display an overlay across the "Name" textView showing the user their newly entered Character
    Name.
     */
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
            .onDismissListener {
                showScoreOverlay()
            }
            .build()
            .show()
    }

    /*
    Display an overlay across the "Score" textView showing the user their initial Score.
   */
    private fun showScoreOverlay() {
        val view: View = findViewById(R.id.createCharacterScreenScoreLabelTextView)

        SimpleTooltip.Builder(this)
            .anchorView(view)
            .text(R.string.createCharacterScreenOverlayText2)
            .gravity(Gravity.BOTTOM)
            .dismissOnInsideTouch(false)
            .animated(true)
            .transparentOverlay(false)
            .showArrow(false)
            .overlayWindowBackgroundColor(Color.BLACK)
            .build()
            .show()
    }

    /*
    Save the Score as a Shared Preference. The Score will initially be 0 until the player gains
    points within the main game.
     */
    private fun createScoreSharedPreference() {
        val sharedPreferences = this.getSharedPreferences("application", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("score", "0").apply()

        val scoreTextView = findViewById<TextView>(R.id.createCharacterScreenScoreLabelTextView)
        val score = sharedPreferences.getString("score", "")
        scoreTextView.text = score
    }

}