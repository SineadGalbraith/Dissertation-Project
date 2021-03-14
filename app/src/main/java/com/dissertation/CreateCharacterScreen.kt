package com.dissertation

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip

class CreateCharacterScreen : AppCompatActivity(), DialogInterface.OnDismissListener {
    private val introductionDialog = CreateCharacterIntroductionParentFragment()
    private val choicesDialog = CreateCharacterAvatarChoicesParentFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_character_screen)
        introductionDialog.show(supportFragmentManager, "enter_name_introduction")
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
                buildCharacter()
            }
            .build()
            .show()
    }

    private fun buildCharacter() {
        choicesDialog.show(supportFragmentManager, "avatar_choices")
    }
}