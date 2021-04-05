package com.dissertation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GameStyleChoiceScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_style_choice_screen)

        val singlePlayerButton = findViewById<Button>(R.id.gameStyleChoiceSinglePlayerButton)
        singlePlayerButton.setOnClickListener{
            val intent = Intent(this, SinglePlayerMainGameScreen::class.java)
            startActivity(intent)
        }

        val multiPlayerButton = findViewById<Button>(R.id.gameStyleChoiceMultiPlayerButton)
        multiPlayerButton.setOnClickListener{
            val intent = Intent(this, MultiPlayerConnectionScreen::class.java)
            startActivity(intent)
        }
    }
}