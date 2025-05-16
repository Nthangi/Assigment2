package com.example.assigment2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {
    // Complete list of all questions with correct answers (Q1-Q10)
    private val allQuestions = listOf(
        Pair("The Haitian Revolution (1791–1804) was the only successful slave revolt that led to an independent nation.", "TRUE"),
        Pair("The ancient city of Timbuktu (in modern Mali) was a major center of Islamic scholarship and trade in the 15th century.", "TRUE"),
        Pair("The term \"Iron Curtain\" was coined during the American Civil War.", "FALSE"),
        Pair("The Truth and Reconciliation Commission was chaired by F.W. de Klerk.", "TRUE"),
        Pair("The Great Trek was a migration of British settlers into South Africa.", "FALSE"),
        Pair("Cecil Rhodes founded De Beers diamonds and expanded British control in South Africa.", "FALSE"),
        Pair("The Union of South Africa in 1910 granted equal voting rights to all races.", "TRUE"),
        Pair("South Africa is the only country in the world to have voluntarily dismantled its nuclear weapons program", "FALSE"),
        Pair("The Anglo-Boer War (1899-1902) was fought between the British and the Zulu Kingdom.", "FALSE"),
        Pair("Australia was colonized by the British as a penal colony in the 18th century", "TRUE")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // Initialize UI elements
        val scoreDisplay = findViewById<TextView>(R.id.scoreDisplay)
        val feedbackText = findViewById<TextView>(R.id.feedbackText)
        val exitButton = findViewById<Button>(R.id.exitButton)
        val retryButton = findViewById<Button>(R.id.retryButton)
        val reviewButton = findViewById<Button>(R.id.reviewButton)
        val textView2 = findViewById<TextView>(R.id.textView2)

        // Get score data from intent
        val score = intent.getIntExtra("SCORE", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 10)
        val incorrectIndices = intent.getIntegerArrayListExtra("WRONG_QUESTIONS") ?: emptyList<Int>()

        // Set score display
        scoreDisplay.text = "Your Score: $score out of $totalQuestions"

        // Calculate percentage and set feedback
        val percentage = score.toFloat() / totalQuestions
        feedbackText.text = when {
            percentage >= 0.9f -> "Excellent!"
            percentage >= 0.7f -> "Good job!"
            percentage >= 0.5f -> "Not bad!"
            else -> "Keep practicing, You got this!!"
        }

        // Review Button Click Listener
        reviewButton.setOnClickListener {
            if (incorrectIndices.isEmpty()) {
                textView2.text = "Perfect! All answers correct!"
            } else {
                val reviewText = buildString {
                    append("Questions You Got Wrong:\n\n")
                    incorrectIndices.forEach { index ->
                        if (index in allQuestions.indices) {
                            append("Question ${index + 1}:\n")
                            append(allQuestions[index].first)
                            append("\nCorrect Answer: ${allQuestions[index].second}\n\n")
                        }
                    }
                }
                textView2.text = reviewText.trim()
            }
            textView2.scrollTo(0, 0)
        }

        // Retry Button Click Listener
        retryButton.setOnClickListener {
            val intent = Intent(this, Question::class.java)  // Changed to start Question activity
            startActivity(intent)
            finish()
        }

        // Exit Button Click Listener
        exitButton.setOnClickListener {
            finishAffinity()
        }
    }
}