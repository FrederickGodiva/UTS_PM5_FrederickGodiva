package com.lab5.myquizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.lab5.myquizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            handleStartButtonClick()
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

    private fun handleStartButtonClick() {
        val name = binding.tietName.text.toString()
        if (binding.tietName.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(this, QuizQuestionActivity::class.java)
            intent.putExtra("username", name)
            startActivity(intent)
            finish()
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