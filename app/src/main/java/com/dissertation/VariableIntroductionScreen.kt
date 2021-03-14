package com.dissertation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VariableIntroductionScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.variable_introduction_screen)

        val textView = findViewById<TextView>(R.id.variableScreenTextView)
        textView.setText(R.string.variableText1)

        val nextButton = findViewById<ImageButton>(R.id.variableNextButton)
        val backButton = findViewById<ImageButton>(R.id.variableBackButton)
        val finishedButton = findViewById<ImageButton>(R.id.variableFinishedButton)

        nextButton.setOnClickListener {
            onNextClick(textView, nextButton, finishedButton)
        }

        backButton.setOnClickListener {
            onBackClick(textView, nextButton, finishedButton)
        }

        finishedButton.setOnClickListener {
            val intent = Intent(this, CreateCharacterScreen::class.java)
            startActivity(intent)
        }
    }

    private fun onNextClick(textView: TextView, nextButton: ImageButton, finishedButton: ImageButton) {
        if (textView.text.toString() == getString(R.string.variableText1)) {
            textView.setText(R.string.variableText2)
        } else if (textView.text.toString() == getString(R.string.variableText2)) {
            textView.setText(R.string.variableText3)
        } else if (textView.text.toString() == getString(R.string.variableText3)) {
            textView.setText(R.string.variableText4)
        } else if (textView.text.toString() == getString(R.string.variableText4)) {
            textView.setText(R.string.variableText5)
        } else if (textView.text.toString() == getString(R.string.variableText5)) {
            textView.setText(R.string.variableText6)
        } else if (textView.text.toString() == getString(R.string.variableText6)) {
            textView.setText(R.string.variableText7)
        } else if (textView.text.toString() == getString(R.string.variableText7)) {
            textView.setText(R.string.variableText8)
        } else if (textView.text.toString() == getString(R.string.variableText8)) {
            textView.setText(R.string.variableText9)
        } else if (textView.text.toString() == getString(R.string.variableText9)) {
            textView.setText(R.string.variableText10)
        } else if (textView.text.toString() == getString(R.string.variableText10)) {
            textView.setText(R.string.variableText11)
            nextButton.visibility = View.GONE
            finishedButton.visibility = View.VISIBLE
        }
    }

    private fun onBackClick(textView: TextView, nextButton: ImageButton, finishedButton: ImageButton) {
        if (textView.text.toString() == getString(R.string.variableText1)) {
            val intent = Intent(this, WelcomeIntroductionScreen::class.java)
            startActivity(intent)
        } else if (textView.text.toString() == getString(R.string.variableText2)) {
            textView.setText(R.string.variableText1)
        } else if (textView.text.toString() == getString(R.string.variableText3)) {
            textView.setText(R.string.variableText2)
        } else if (textView.text.toString() == getString(R.string.variableText4)) {
            textView.setText(R.string.variableText3)
        } else if (textView.text.toString() == getString(R.string.variableText5)) {
            textView.setText(R.string.variableText4)
        } else if (textView.text.toString() == getString(R.string.variableText6)) {
            textView.setText(R.string.variableText5)
        } else if (textView.text.toString() == getString(R.string.variableText7)) {
            textView.setText(R.string.variableText6)
        } else if (textView.text.toString() == getString(R.string.variableText8)) {
            textView.setText(R.string.variableText7)
        } else if (textView.text.toString() == getString(R.string.variableText9)) {
            textView.setText(R.string.variableText8)
        } else if (textView.text.toString() == getString(R.string.variableText10)) {
            textView.setText(R.string.variableText9)
        } else if (textView.text.toString() == getString(R.string.variableText11)) {
            textView.setText(R.string.variableText10)
            finishedButton.visibility = View.GONE
            nextButton.visibility = View.VISIBLE
        }
    }
}