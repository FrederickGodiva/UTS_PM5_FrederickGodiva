package com.lab5.myquizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.lab5.myquizapp.databinding.ActivityQuizQuestionBinding

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionBinding
    private lateinit var userName: String
    private var score: Int = 0
    private var currentQuestion: Int = 1
    private lateinit var questions: ArrayList<QuizQuestion.Question>
    private var selectedOption: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra("username").toString()

        questions = QuizQuestion().getQuestions()

        binding.tvAnswer1.setOnClickListener(this)
        binding.tvAnswer2.setOnClickListener(this)
        binding.tvAnswer3.setOnClickListener(this)
        binding.tvAnswer4.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener {
            if (selectedOption == 0) {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
            } else {
                handleButtonClick()
            }
        }

        binding.themeModeButton.setOnClickListener {
            toggleThemeMode()
        }

        savedInstanceState?.let {
            currentQuestion = it.getInt("currentQuestion", 1)
            selectedOption = it.getInt("selectedOption", 0)
            score = it.getInt("correctAnswer", 0)
        }

        setQuestion()

        updateThemeModeButton()
    }

    override fun onResume() {
        super.onResume()
        updateThemeModeButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentQuestion", currentQuestion)
        outState.putInt("selectedOption", selectedOption)
        outState.putInt("correctAnswer", score)
    }

    private fun setQuestion() {
        val question = questions[currentQuestion - 1]

        binding.ivLogo.setImageResource(question.image)
        binding.progressBar.progress = currentQuestion
        binding.tvProgress.text = "$currentQuestion / ${binding.progressBar.max}"
        binding.tvQuestion.text = question.question

        binding.tvAnswer1.text = question.optionOne
        binding.tvAnswer2.text = question.optionTwo
        binding.tvAnswer3.text = question.optionThree
        binding.tvAnswer4.text = question.optionFour

        binding.btnSubmit.text = if (currentQuestion == questions.size) {
            "FINISH"
        } else {
            "SUBMIT"
        }

        if (selectedOption != 0) {
            when (selectedOption) {
                1 -> selectedOptionView(binding.tvAnswer1, 1)
                2 -> selectedOptionView(binding.tvAnswer2, 2)
                3 -> selectedOptionView(binding.tvAnswer3, 3)
                4 -> selectedOptionView(binding.tvAnswer4, 4)
            }
        }
        defaultOptionsView()
    }

    private fun defaultOptionsView() {
        val options = listOf(
            binding.tvAnswer1,
            binding.tvAnswer2,
            binding.tvAnswer3,
            binding.tvAnswer4
        )

        for (option in options) {
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        selectedOption = selectedOptionNum
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.tvAnswer1 -> selectedOptionView(binding.tvAnswer1, 1)
            binding.tvAnswer2 -> selectedOptionView(binding.tvAnswer2, 2)
            binding.tvAnswer3 -> selectedOptionView(binding.tvAnswer3, 3)
            binding.tvAnswer4 -> selectedOptionView(binding.tvAnswer4, 4)
            binding.btnSubmit -> handleButtonClick()
        }
    }

    private fun handleButtonClick() {
        val isSubmit = binding.btnSubmit.text == "SUBMIT"

        if (isSubmit) {
            val question = questions[currentQuestion - 1]
            question.let {
                if (it.correctAnswer != selectedOption) {
                    answerView(selectedOption, R.drawable.wrong_option_border)
                } else {
                    score++
                }
                answerView(it.correctAnswer, R.drawable.correct_option_border)
            }

            binding.btnSubmit.text = if (currentQuestion == questions.size) "FINISH" else "NEXT QUESTION"
        } else {
            if (++currentQuestion <= questions.size) {
                setQuestion()
                binding.btnSubmit.text = "SUBMIT"
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("username", userName)
                intent.putExtra("score", score)
                intent.putExtra("total_questions", questions.size)
                startActivity(intent)
                finish()
            }
            selectedOption = 0
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> binding.tvAnswer1.background = ContextCompat.getDrawable(this, drawableView)
            2 -> binding.tvAnswer2.background = ContextCompat.getDrawable(this, drawableView)
            3 -> binding.tvAnswer3.background = ContextCompat.getDrawable(this, drawableView)
            4 -> binding.tvAnswer4.background = ContextCompat.getDrawable(this, drawableView)
        }
    }

    private fun toggleThemeMode() {
        val nightModeFlags = AppCompatDelegate.getDefaultNightMode()

        if (nightModeFlags == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun updateThemeModeButton() {
        val nightModeFlags = AppCompatDelegate.getDefaultNightMode()
        if (nightModeFlags == AppCompatDelegate.MODE_NIGHT_YES) {
            binding.themeModeButton.setBackgroundResource(R.drawable.moon)
        } else {
            binding.themeModeButton.setBackgroundResource(R.drawable.sun)
        }
    }
}
