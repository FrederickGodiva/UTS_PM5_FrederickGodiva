package com.lab5.myquizapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.lab5.myquizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.text = intent.getStringExtra("username")
        val totalQuestions = intent.getIntExtra("total_questions", 0)
        val correctAnswers = intent.getIntExtra("score", 0)
        binding.tvScore.text = "Your Score is $correctAnswers out of $totalQuestions"

        binding.btnFinish.setOnClickListener {
            handleFinishButtonClick()
        }

        binding.themeModeButton.setOnClickListener {
            toggleThemeMode()
        }

        updateThemeModeButton()
    }

    override fun onResume() {
        super.onResume()
        updateThemeModeButton()
    }

    private fun handleFinishButtonClick() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
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