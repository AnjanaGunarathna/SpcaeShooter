package com.example.spcaeshooter

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameOver : AppCompatActivity() {
    private lateinit var tvPoints: TextView
    private lateinit var tvHighScore: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over)

        val points = intent.extras?.getInt("points", 0) ?: 0
        tvPoints = findViewById(R.id.tvPoints)
        tvHighScore = findViewById(R.id.tvHighScore)

        // Update points
        tvPoints.text = points.toString()

        // Retrieve and update high score
        sharedPreferences = getSharedPreferences("high_score", MODE_PRIVATE)
        val highScore = sharedPreferences.getInt("high_score", 0)
        if (points > highScore) {
            // Save new high score
            sharedPreferences.edit().putInt("high_score", points).apply()
            tvHighScore.text = "High Score: $points"
        } else {
            tvHighScore.text = "High Score: $highScore"
        }
    }

    fun restart(v: View) {
        val intent = Intent(this, StartUp::class.java)
        startActivity(intent)
        finish()
    }

    fun exit(v: View) {
        finish()
    }
}
