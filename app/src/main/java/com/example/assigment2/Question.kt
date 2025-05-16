package com.example.assigment2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class Question : AppCompatActivity() {

    private val questions = arrayOf(
        "The Haitian Revolution (1791–1804) was the only successful slave revolt that led to an independent nation.",
        "The ancient city of Timbuktu (in modern Mali) was a major center of Islamic scholarship and trade in the 15th century.",
        "The term \"Iron Curtain\" was coined during the American Civil War.",
        "The Truth and Reconciliation Commission was chaired by F.W. de Klerk.",
        "The Great Trek was a migration of British settlers into South Africa.",
        "Cecil Rhodes founded De Beers diamonds and expanded British control in South Africa.",
        "The Union of South Africa in 1910 granted equal voting rights to all races.",
        "South Africa is the only country in the world to have voluntarily dismantled its nuclear weapons program",
        "The Anglo-Boer War (1899–1902) was fought between the British and the Zulu Kingdom.",
        "Australia was colonized by the British as a penal colony in the 18th century"

    )

    private val correctAnswers = booleanArrayOf(
        true, true, false, true, false,
        false, true, false, false, true
    )

    private var currentQuestionIndex = 0
    private var score = 0
    private val userAnswers = BooleanArray(10) { false }
    private val wrongQuestions = ArrayList<Int>()

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var feedbackText: TextView
    private lateinit var questionText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        initializeViews()
        setupButtonListeners()
        displayQuestion()
        disableNextButton()
    }

    private fun initializeViews() {
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        nextButton = findViewById(R.id.nextButton)
        feedbackText = findViewById(R.id.feedbackText)
        questionText = findViewById(R.id.questionText)
    }

    private fun setupButtonListeners() {
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
                calculateWrongQuestions()
                navigateToScoreScreen()
            }
        }
    }

    private fun displayQuestion() {
        questionText.text = questions[currentQuestionIndex]
    }

    private fun checkAnswer(userAnswer: Boolean) {
        userAnswers[currentQuestionIndex] = userAnswer
        if (userAnswer == correctAnswers[currentQuestionIndex]) {
            score++
            feedbackText.text = "You Nailed it!"
            feedbackText.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
        } else {
            feedbackText.text = "Oops! Try Again"
            feedbackText.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        }
    }

    private fun calculateWrongQuestions() {
        wrongQuestions.clear()
        userAnswers.forEachIndexed { index, answer ->
            if (answer != correctAnswers[index]) {
                wrongQuestions.add(index)
            }
        }
    }

    private fun navigateToScoreScreen() {
        Intent(this, ScoreActivity::class.java).apply {
            putExtra("SCORE", score)
            putExtra("TOTAL_QUESTIONS", questions.size)
            putIntegerArrayListExtra("WRONG_QUESTIONS", wrongQuestions)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
        finish()
    }

    private fun enableNextButton() {
        nextButton.isEnabled = true
        nextButton.alpha = 1f
    }

    private fun disableNextButton() {
        nextButton.isEnabled = false
        nextButton.alpha = 0.5f
    }
}