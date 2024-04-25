package com.example.spcaeshooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.Random

class EnemySpaceship(context: Context) {
    private val context: Context = context
    private var enemySpaceship: Bitmap
    var ex: Int = 0
    var ey: Int = 0
    var enemyVelocity: Int = 0
    private val random: Random = Random()

    init {
        enemySpaceship = BitmapFactory.decodeResource(context.resources, R.drawable.rocket2)
        restEnemySpaceship()
    }

    fun getEnemySpaceship(): Bitmap {
        return enemySpaceship
    }

    fun getEnemySpaceshipWidth(): Int {
        return enemySpaceship.width
    }

    fun getEnemySpaceshipHeight(): Int {
        return enemySpaceship.height
    }

    private fun restEnemySpaceship() {
        ex = 200 + random.nextInt(400)
        ey = 0
        enemyVelocity = 14 + random.nextInt(10)
    }
}
