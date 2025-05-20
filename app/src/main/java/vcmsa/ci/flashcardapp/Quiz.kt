package vcmsa.ci.flashcardapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Quiz : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var feedbackTextView: TextView

    companion object {
        val questions = arrayOf(
            "The largest whale in the world is the blue whale",
            "The tallest mountain in the world is Mount Everest",
            "The fastest land animal on Earth is the Cheetah",
            "The longest river in the world is the Nile",
            "The sun is the center of our solar system"
        )
        val answers = booleanArrayOf(true, true, true, false, true)
    }

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        // Initialize UI elements
        questionTextView = findViewById(R.id.question_text_view)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        feedbackTextView = findViewById(R.id.feedback_text_view) // 🔧 Added this line

        displayQuestion()

        trueButton.setOnClickListener { checkAnswer(true) }
        falseButton.setOnClickListener { checkAnswer(false) }

        nextButton.setOnClickListener {
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                displayQuestion()
                feedbackTextView.text = ""
                trueButton.isEnabled = true
                falseButton.isEnabled = true
                nextButton.isEnabled = false
            } else {
                val intent = Intent(this, Score::class.java)
                intent.putExtra("score", score)
                startActivity(intent)
                finish()
            }
        }

        nextButton.isEnabled = false
    }

    private fun displayQuestion() {
        questionTextView.text = questions[currentQuestionIndex]
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = answers[currentQuestionIndex]

        if (userAnswer == correctAnswer) {
            feedbackTextView.text = "Correct!"
            feedbackTextView.setTextColor(Color.GREEN)  //Set green for correct choices
            score++
        } else {
            feedbackTextView.text = "Incorrect"
            feedbackTextView.setTextColor(Color.RED)  //Set red for incorrect choices
        }

        trueButton.isEnabled = false
        falseButton.isEnabled = false
        nextButton.isEnabled = true
    }
}
