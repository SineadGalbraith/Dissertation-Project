package com.dissertation

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip

class SinglePlayerMainGameScreen : AppCompatActivity(), DialogInterface.OnDismissListener {
    private val introductionDialog = SinglePlayerMainGameIntroductionFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_player_main_game_screen)
        introductionDialog.show(supportFragmentManager, "main_game_introduction")

        val scrollView = findViewById<ScrollView>(R.id.single_player_main_game_screen_scroll_view)
        scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
        }

        val level1Button = findViewById<Button>(R.id.singlePlayerMainGameLevel1)
        level1Button.setOnClickListener {
            val intent = Intent(this, SinglePlayerLevel1Screen::class.java)
            startActivity(intent)
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        showIntroductionOverlay1()
    }

    private fun showIntroductionOverlay1() {
        val view: View = findViewById(R.id.singlePlayerMainGameLevel1)

        SimpleTooltip.Builder(this)
            .anchorView(view)
            .text(R.string.singlePlayerMainGameScreenOverlay1)
            .gravity(Gravity.TOP)
            .dismissOnInsideTouch(false)
            .animated(true)
            .transparentOverlay(false)
            .showArrow(false)
            .overlayWindowBackgroundColor(Color.BLACK)
            .onDismissListener {
                showIntroductionOverlay2()
            }
            .build()
            .show()
    }

    private fun showIntroductionOverlay2() {
        val view: View = findViewById(R.id.singlePlayerMainGameLevel2)
        val level1Button = findViewById<Button>(R.id.singlePlayerMainGameLevel1)
        SimpleTooltip.Builder(this)
            .anchorView(view)
            .text(R.string.singlePlayerMainGameScreenOverlay2)
            .gravity(Gravity.TOP)
            .dismissOnInsideTouch(false)
            .animated(true)
            .transparentOverlay(false)
            .showArrow(false)
            .overlayWindowBackgroundColor(Color.BLACK)
            .onDismissListener {
                level1Button.isEnabled = true
                showIntroductionOverlay3()
            }
            .build()
            .show()
    }

    private fun showIntroductionOverlay3() {
        val view: View = findViewById(R.id.singlePlayerMainGameLevel1)
        SimpleTooltip.Builder(this)
            .anchorView(view)
            .text(R.string.singlePlayerMainGameScreenOverlay3)
            .gravity(Gravity.TOP)
            .dismissOnInsideTouch(false)
            .animated(true)
            .transparentOverlay(false)
            .showArrow(false)
            .overlayWindowBackgroundColor(Color.BLACK)
            .build()
            .show()
    }
}