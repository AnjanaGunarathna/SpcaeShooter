package com.example.spcaeshooter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameOver : AppCompatActivity() {
    private lateinit var tvPoints: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over)
        val points = intent.extras?.getInt("points", 0) ?: 0
        tvPoints = findViewById(R.id.tvPoints)
        tvPoints.text = points.toString()
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
