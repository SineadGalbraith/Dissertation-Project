package com.dissertation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import java.util.Collections.shuffle

class SinglePlayerLevel1Screen : AppCompatActivity() {
    private val introductionDialog = SinglePlayerLevel1IntroductionFragment()
    private var questionsAndAnswers : MutableMap<Int, Array<Int>> = mutableMapOf()

    private var indexArray : MutableList<Int> = mutableListOf()
    private var questionNumbers : MutableList<Int> = mutableListOf()

    private var score : Int = 0
    private var currentPageNumber : Int = 1
    private var questionAttempts : Int = 0
    private var page1 : Boolean = true
    private var page2 : Boolean = false
    private var page3 : Boolean = false
    private var page4 : Boolean = false
    private var page5 : Boolean = false
    private var page6 : Boolean = false
    private var page7 : Boolean = false
    private var page8 : Boolean = false
    private var page9 : Boolean = false

    private var levelProgress : TextView? = null
    private var questionParameter1 : TextView? = null
    private var questionParameter2 : TextView? = null
    private var questionAnswer : TextView? = null
    private var answerFeedback : ImageView? = null
    private var operationsTextView: TextView? = null
    private var nextButton : ImageButton? = null
    private var finishedButton : ImageButton? = null
    private var tryAgain : TextView? = null
    private var wellDone : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_player_level_1_screen)
        introductionDialog.show(supportFragmentManager, "level_1_introduction")

        // Initialise Components
        val additionButton = findViewById<ImageButton>(R.id.additionButton)
        val subtractionButton = findViewById<ImageButton>(R.id.subtractionButton)
        val multiplicationButton = findViewById<ImageButton>(R.id.multiplicationButton)
        val divisionButton = findViewById<ImageButton>(R.id.divisionButton)
        nextButton = findViewById(R.id.level1NextButton)
        finishedButton = findViewById(R.id.level1FinishedButton)

        levelProgress = findViewById(R.id.level1QuestionProgress)
        operationsTextView = findViewById(R.id.level1QuestionOperator)
        questionParameter1 = findViewById(R.id.level1QuestionParameter1)
        questionParameter2 = findViewById(R.id.level1QuestionParameter2)
        questionAnswer = findViewById(R.id.level1QuestionSectionAnswer)
        answerFeedback = findViewById(R.id.feedbackImageView)
        tryAgain = findViewById(R.id.tryAgainTextView)
        wellDone = findViewById(R.id.wellDoneTextView)
        pageSetup()

        nextButton?.setOnClickListener {
            onNextClick()
        }

        finishedButton?.setOnClickListener {
            finishLevel()
            finish()
        }

        for (i in 0..8) {
            indexArray.add(i, i)
            questionNumbers.add(i, i)
        }
        shuffle(indexArray)

        generateQuestions()
        displayQuestions(currentPageNumber)

        additionButton.setOnClickListener {
            questionAttempts++
            operationsTextView?.setText(R.string.additionSign)
            checkAnswer(currentPageNumber, 1)
        }

        subtractionButton.setOnClickListener {
            questionAttempts++
            operationsTextView?.setText(R.string.subtractionSign)
            checkAnswer(currentPageNumber, 2)
        }

        multiplicationButton.setOnClickListener {
            questionAttempts++
            operationsTextView?.setText(R.string.multiplicationSign)
            checkAnswer(currentPageNumber, 3)
        }

        divisionButton.setOnClickListener {
            questionAttempts++
            operationsTextView?.setText(R.string.divisionSign)
            checkAnswer(currentPageNumber, 4)
        }

    }

    private fun pageSetup() {
        val levelTextView = findViewById<TextView>(R.id.levelNumberDisplayTextView)
        levelTextView.setText(R.string.One)

        val nameTextView = findViewById<TextView>(R.id.playerNameDisplayTextView)
        val sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE)
        val characterName = sharedPreferences.getString("username", "")
        nameTextView.text = if (!characterName.isNullOrEmpty()) characterName else ""

        tryAgain?.visibility = View.INVISIBLE
        answerFeedback?.visibility = View.INVISIBLE
        nextButton?.visibility = View.INVISIBLE
        wellDone?.visibility = View.INVISIBLE
        operationsTextView?.setText(R.string.questionMark)

        updateScore()
    }

    private fun updateScore() {
        val scoreTextView = findViewById<TextView>(R.id.scoreTextView)
        scoreTextView.text = score.toString()
    }

    private fun checkAnswer(pageNumber : Int, guess : Int) {
        val index = indexArray[pageNumber - 1]
        val questionAnswer = questionsAndAnswers[index]?.get(2)

        if(questionAnswer == guess) {
            if (!page9) {
                nextButton?.visibility = View.VISIBLE
            } else if (page9) {
                finishedButton?.visibility = View.VISIBLE
            }
            answerFeedback?.visibility = View.VISIBLE
            wellDone?.visibility = View.VISIBLE
            tryAgain?.visibility = View.INVISIBLE
            answerFeedback?.setBackgroundResource(R.drawable.correct_icon)
            if (questionAttempts == 1) {
                score += 3

            } else if (questionAttempts == 2) {
                score += 2
            } else if (questionAttempts > 2) {
                score += 1
            }
        } else if (questionAnswer != guess) {
            answerFeedback?.visibility = View.VISIBLE
            tryAgain?.visibility = View.VISIBLE
            wellDone?.visibility = View.INVISIBLE
            answerFeedback?.setBackgroundResource(R.drawable.incorrect_icon)
        }

        updateScore()
    }

    private fun generateQuestions() {
        // Add numbers between 0 and 100
        for (i in 0..2) {
            val num1 = (1..100).random()
            val num2 = (1..100).random()
            val answer = num1 + num2
            val correctOperation = 1 // (1 for add)
            questionsAndAnswers[i] = arrayOf(num1, num2, correctOperation, answer)
        }

        // Subtract numbers between 0 and 100
        for (i in 3..5) {
            val num1 = (1..100).random()
            val num2 = (1..100).random()
            val answer = num1 - num2
            val correctOperation = 2 // (2 for subtract)
            questionsAndAnswers[i] = arrayOf(num1, num2, correctOperation, answer)
        }

        // Multiply numbers between 12 and 12
        for (i in 6..8) {
            val num1 = (1..12).random()
            val num2 = (1..12).random()
            val answer = num1 * num2
            val correctOperation = 3 // (3 for multiply)
            questionsAndAnswers[i] = arrayOf(num1, num2, correctOperation, answer)
        }

        for (i in 9..11) {
            val num1 = (1..12).random()
            val num2 = (1..12).random()
            val answer = num1 * num2
            val correctOperation = 4 // (4 for divide)
            questionsAndAnswers[i] = arrayOf(answer, num2, correctOperation, num1)
        }
    }

    private fun displayQuestions(pageNumber : Int) {
        levelProgress?.text = pageNumber.toString()
        val index = indexArray[pageNumber - 1]

        questionParameter1?.text = questionsAndAnswers[index]?.get(0).toString()
        questionParameter2?.text = questionsAndAnswers[index]?.get(1).toString()
        questionAnswer?.text = questionsAndAnswers[index]?.get(3).toString()
    }

    private fun finishLevel() {
        val scoreTextView = findViewById<TextView>(R.id.scoreTextView)
        val score = scoreTextView.text.toString()
        val sharedPreferences = this.getSharedPreferences("application", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("score", score).apply()
    }

    private fun onNextClick() {
        answerFeedback?.visibility = View.INVISIBLE
        nextButton?.visibility = View.INVISIBLE
        tryAgain?.visibility = View.INVISIBLE
        wellDone?.visibility = View.INVISIBLE
        operationsTextView?.setText(R.string.questionMark)
        questionAttempts = 0
        if (page1) {
            page1 = false
            page2 = true
            currentPageNumber = 2
            displayQuestions(2)
        } else if (page2) {
            page2 = false
            page3 = true
            currentPageNumber = 3
            displayQuestions(3)
        } else if (page3) {
            page3 = false
            page4 = true
            currentPageNumber = 4
            displayQuestions(4)
        } else if (page4) {
            page4 = false
            page5 = true
            currentPageNumber = 5
            displayQuestions(5)
        } else if (page5) {
            page5 = false
            page6 = true
            currentPageNumber = 6
            displayQuestions(6)
        } else if (page6) {
            page6 = false
            page7 = true
            currentPageNumber = 7
            displayQuestions(7)
        } else if (page7) {
            page7 = false
            page8 = true
            currentPageNumber = 8
            displayQuestions(8)
        } else if (page8) {
            page8 = false
            page9 = true
            currentPageNumber = 9
            displayQuestions(9)
        }
    }
}