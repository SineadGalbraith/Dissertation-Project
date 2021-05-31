/* This class contains the code used for displaying the Single Player Main Game Screen. */
package com.dissertation

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip

class SinglePlayerMainGameScreen : AppCompatActivity(), DialogInterface.OnDismissListener {
    private val introductionDialog = SinglePlayerMainGameIntroductionFragment()

    /*
    When the class is called, display the Single Player Main Game Screen.

    Update the screen to include the player details including the Name and the Score.

    Set the Scroll View of the screen so that all of the levels, both locked and unlocked can be
    viewed by the scrolling motion.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_player_main_game_screen)
        introductionDialog.show(supportFragmentManager, "main_game_introduction")

        showPlayerDetails()

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

    /*
    When the user exits a game level, show the previous screen in the previous state, without the
    introduction elements.

    Change and update the user's score.
     */
    override fun onRestart() {
        super.onRestart()
        val scoreTextView = findViewById<TextView>(R.id.mainGameScoreTextView)
        val sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE)
        val score = sharedPreferences.getString("score", "")
        scoreTextView.text = score
    }

    /*
    Access the players Name and Score from the Shared Preferences and display them in the user
    details bar at the top of the screen.
     */
    private fun showPlayerDetails() {
        val sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE)

        val nameTextView = findViewById<TextView>(R.id.mainGameNameTextView)
        val characterName = sharedPreferences.getString("username", "")
        nameTextView.text = characterName

        val scoreTextView = findViewById<TextView>(R.id.mainGameScoreTextView)
        val score = sharedPreferences.getString("score", "")
        scoreTextView.text = score

    }

    /*
    Display an overlay over the user details bar at the top of the screen, explaining to the user
    where they can see their Character Name and current Score.
     */
    private fun showPlayerDetailsOverlay() {
        val view: View = findViewById(R.id.mainScreenHeaderRelativeLayout)

        SimpleTooltip.Builder(this)
            .anchorView(view)
            .text(R.string.singlePlayerMainGamePlayerProfileOverlayText1)
            .gravity(Gravity.BOTTOM)
            .dismissOnInsideTouch(false)
            .animated(true)
            .transparentOverlay(false)
            .showArrow(false)
            .overlayWindowBackgroundColor(Color.BLACK)
            .onDismissListener {
                showIntroductionOverlay1()
            }
            .build()
            .show()
    }

    /*
    Detect when the Dialog Fragment has been dismissed.
   */
    override fun onDismiss(dialog: DialogInterface?) {
        showPlayerDetailsOverlay()
    }

    /*
    Display an overlay over Level 1 to introduce the player to the game menu.
     */
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

    /*
    Display an overlay over Level 2 to explain how the game works and how the different levels can
    be unlocked.
     */
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

    /*
    Display an overlay over Level 1 again, advising the player that they can now begin to play the
    game.
     */
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