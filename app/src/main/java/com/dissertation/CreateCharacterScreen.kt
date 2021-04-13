package com.dissertation

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip

class CreateCharacterScreen : AppCompatActivity() , DialogInterface.OnDismissListener {
    private val introductionDialog = CreateCharacterIntroductionParentFragment()
    private var finishedButton : ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_character_screen)

        // Name Input Dialog Fragment
        introductionDialog.show(supportFragmentManager, "enter_name_introduction")

        // Initiate Score
        createScoreSharedPreference()

        // Confirm Choice Button (TO BE CHANGED)
        finishedButton = findViewById(R.id.confirmChoiceButton)
        finishedButton?.setOnClickListener {
            val intent = Intent(this, GameStyleChoiceScreen::class.java)
            startActivity(intent)
        }
    }

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
            .onDismissListener {
                showScoreOverlay()
            }
            .build()
            .show()
    }

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

    private fun createScoreSharedPreference() {
        val sharedPreferences = this.getSharedPreferences("application", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("score", "0").apply()

        val scoreTextView = findViewById<TextView>(R.id.createCharacterScreenScoreLabelTextView)
        val score = sharedPreferences.getString("score", "")
        scoreTextView.text = score
    }

}