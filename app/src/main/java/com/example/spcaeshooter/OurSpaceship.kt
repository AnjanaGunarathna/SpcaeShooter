package com.example.spcaeshooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.Random

class OurSpaceship(context: Context) {
    private val context: Context = context
    private var ourSpaceship: Bitmap
    var ox: Int = 0
    var oy: Int = 0
    var isAlive: Boolean = true
    var ourVelocity: Int = 0
    private val random: Random = Random()

    init {
        ourSpaceship = BitmapFactory.decodeResource(context.resources, R.drawable.rocket1)
        resetOurSpaceship()
    }

    fun getOurSpaceship(): Bitmap {
        return ourSpaceship
    }

    fun getOurSpaceshipWidth(): Int {
        return ourSpaceship.width
    }

    private fun resetOurSpaceship() {
        ox = random.nextInt(SpaceShooter.screenWidth)
        oy = SpaceShooter.screenHeight - ourSpaceship.height
        ourVelocity = 10 + random.nextInt(6)
    }
}