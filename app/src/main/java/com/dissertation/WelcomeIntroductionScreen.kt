/* This class contains the code used for displaying the Welcome Introduction Screen. */
package com.dissertation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeIntroductionScreen : AppCompatActivity() {

    /*
   When this class is called, display the Welcome Introduction Screen. Store references to the
   on-screen components for use elsewhere in the class.

   Set up the onClickListeners for the buttons on-screen providing the main functionality.

   When the user presses the "Finish" button, change to the Variable Introduction Screen.
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_introduction_screen)

        val textView = findViewById<TextView>(R.id.welcomeScreenTextView)
        textView.setText(R.string.welcomeText1)

        val nextButton = findViewById<ImageButton>(R.id.welcomeNextButton)
        val backButton = findViewById<ImageButton>(R.id.welcomeBackButton)
        val finishedButton = findViewById<ImageButton>(R.id.welcomeFinishedButton)

        nextButton.setOnClickListener {
            onNextClick(textView, nextButton, backButton, finishedButton)
        }

        backButton.setOnClickListener {
            onBackClick(textView, nextButton, backButton, finishedButton)
        }

        finishedButton.setOnClickListener {
            val intent = Intent(this, VariableIntroductionScreen::class.java)
            startActivity(intent)
        }
    }

    /*
    Depending on the currently displayed text passage, update the textView or make the "Finished"
    button visible when the user presses the "Next" button.
     */
    private fun onNextClick(textView: TextView, nextButton: ImageButton, backButton: ImageButton, finishedButton: ImageButton) {
        if (textView.text.toString() == getString(R.string.welcomeText1)) {
            textView.setText(R.string.welcomeText2)
            backButton.visibility = View.VISIBLE
        } else if (textView.text.toString() == getString(R.string.welcomeText2)) {
            textView.setText(R.string.welcomeText3)
            nextButton.visibility = View.INVISIBLE
            backButton.visibility = View.VISIBLE
            finishedButton.visibility = View.VISIBLE
        }
    }

    /*
      Depending on the currently displayed text passage, update the textView or make the "Finished"
      button invisible when the user presses the "Back" button.
       */
    private fun onBackClick(textView: TextView, nextButton: ImageButton, backButton: ImageButton, finishedButton: ImageButton) {
        if (textView.text.toString() == getString(R.string.welcomeText1)) {
            backButton.visibility = View.INVISIBLE
        } else if (textView.text.toString() == getString(R.string.welcomeText2)) {
            textView.setText(R.string.welcomeText1)
            backButton.visibility = View.INVISIBLE
        } else if (textView.text.toString() == getString(R.string.welcomeText3)) {
            textView.setText(R.string.welcomeText2)
            nextButton.visibility = View.VISIBLE
            finishedButton.visibility = View.INVISIBLE
        }
    }
}