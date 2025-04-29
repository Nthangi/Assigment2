package com.example.assigment2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val score = intent.getIntExtra("SCORE", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 1)

        val scoreDisplay = findViewById<TextView>(R.id.scoreDisplay)
        val feedbackText = findViewById<TextView>(R.id.feedbackText)
        val exitButton = findViewById<Button>(R.id.exitButton)

        scoreDisplay.text = "Your Score: $score out of $totalQuestions"

        val percentage = score.toFloat() / totalQuestions
        feedbackText.text = when {
            percentage >= 0.9f -> "Excellent!"
            percentage >= 0.7f -> "Good job!"
            percentage >= 0.5f -> "Not bad!"
            else -> "Keep practicing!"
        }

        exitButton.setOnClickListener {
            finishAffinity()
        }
    }
}