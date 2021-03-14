package com.dissertation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeIntroductionScreen : AppCompatActivity() {
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

    private fun onNextClick(textView: TextView, nextButton: ImageButton, backButton: ImageButton, finishedButton: ImageButton) {
        if (textView.text.toString() == getString(R.string.welcomeText1)) {
            textView.setText(R.string.welcomeText2)
            backButton.visibility = View.VISIBLE
        } else if (textView.text.toString() == getString(R.string.welcomeText2)) {
            textView.setText(R.string.welcomeText3)
            nextButton.visibility = View.GONE
            backButton.visibility = View.VISIBLE
            finishedButton.visibility = View.VISIBLE
        }
    }

    private fun onBackClick(textView: TextView, nextButton: ImageButton, backButton: ImageButton, finishedButton: ImageButton) {
        if (textView.text.toString() == getString(R.string.welcomeText1)) {
            backButton.visibility = View.GONE
        } else if (textView.text.toString() == getString(R.string.welcomeText2)) {
            textView.setText(R.string.welcomeText1)
            backButton.visibility = View.GONE
        } else if (textView.text.toString() == getString(R.string.welcomeText3)) {
            textView.setText(R.string.welcomeText2)
            nextButton.visibility = View.VISIBLE
            finishedButton.visibility = View.GONE
        }
    }
}