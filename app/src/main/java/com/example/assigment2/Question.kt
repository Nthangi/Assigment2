package com.example.assigment2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class Question : AppCompatActivity() {

    private val questions = arrayOf(
        "Nelson Mandela became South Africa's first democratically elected president in 1994.",
        "Apartheid was officially implemented in South Africa in 1948.",
        "The Sharpeville Massacre occurred during a protest against education policies.",
        "The 1976 Soweto Uprising began as a protest against Afrikaans being used in schools.",
        "The Truth and Reconciliation Commission was chaired by F.W. de Klerk.",
        "The Great Trek was a migration of British settlers into South Africa.",
        "Cecil Rhodes founded De Beers diamonds and expanded British control in South Africa.",
        "The Union of South Africa in 1910 granted equal voting rights to all races.",
        "The Marikana Massacre happened during apartheid."
    )
    private val answers = booleanArrayOf(true, true, false, true, false, false, true, false, false)

    private var currentQuestionIndex = 0
    private var score = 0

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var feedbackText: TextView
    private lateinit var questionText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        nextButton = findViewById(R.id.nextButton)
        feedbackText = findViewById(R.id.feedbackText)
        questionText = findViewById(R.id.questionText)

        trueButton.setOnClickListener {
            checkAnswer(true)
            enableNextButton()
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            enableNextButton()
        }

        nextButton.setOnClickListener {
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                displayQuestion()
                feedbackText.text = ""
                disableNextButton()
            } else {
                navigateToScoreScreen()
            }
        }

        displayQuestion()
        disableNextButton()
    }

    private fun displayQuestion() {
        questionText.text = questions[currentQuestionIndex]
    }

    private fun checkAnswer(userAnswer: Boolean) {
        if (userAnswer == answers[currentQuestionIndex]) {
            score++
            feedbackText.text = "Correct!"
            feedbackText.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
        } else {
            feedbackText.text = "Incorrect"
            feedbackText.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        }
    }

    private fun enableNextButton() {
        nextButton.isEnabled = true
        nextButton.alpha = 1f
    }

    private fun disableNextButton() {
        nextButton.isEnabled = false
        nextButton.alpha = 0.5f
    }

    private fun navigateToScoreScreen() {
        val intent = Intent(this, ScoreActivity::class.java).apply {
            putExtra("SCORE", score)
            putExtra("TOTAL_QUESTIONS", questions.size)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }
}