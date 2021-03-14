package com.dissertation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        val welcomeButton = findViewById<Button>(R.id.PlayButton)
            welcomeButton.setOnClickListener{
                val intent = Intent(this, WelcomeIntroductionScreen::class.java)
                startActivity(intent)
            }
    }





}