/* This class contains the code used for displaying the Welcome Screen. */
package com.dissertation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeScreen : AppCompatActivity() {

    /*
   When this class is called, display the Welcome Screen.

   If the user presses the "Play" button, change to the Welcome Introduction Screen.
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_screen)

        val welcomeButton = findViewById<Button>(R.id.playButton)
        welcomeButton.setOnClickListener{
            val intent = Intent(this, WelcomeIntroductionScreen::class.java)
            startActivity(intent)
        }
    }





}