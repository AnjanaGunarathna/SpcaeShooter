package com.example.spcaeshooter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Handler
import android.view.Display
import android.view.MotionEvent
import android.view.View
import java.util.Random

class SpaceShooter(context: Context) : View(context) {
    private val context: Context = context
    private val random: Random = Random()
    private var enemyShots: ArrayList<Shot> = ArrayList()
    private var ourShots: ArrayList<Shot> = ArrayList()
    private var explosions: ArrayList<Explosion> = ArrayList()
    private var handler: Handler = Handler()
    private var UPDATE_MILLIS: Long = 30
    private var points = 0
    private var life = 3
    private val TEXT_SIZE = 80
    private var paused = false
    private lateinit var ourSpaceship: OurSpaceship
    private lateinit var enemySpaceship: EnemySpaceship
    private lateinit var background: Bitmap
    private lateinit var lifeImage: Bitmap
    private lateinit var scorePaint: Paint

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            invalidate()
        }
    }

    init {
        val display: Display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
        screenHeight = size.y
        ourSpaceship = OurSpaceship(context)
        enemySpaceship = EnemySpaceship(context)
        background = BitmapFactory.decodeResource(context.resources, R.drawable.background2)
        lifeImage = BitmapFactory.decodeResource(context.resources, R.drawable.life)
        scorePaint = Paint()
        scorePaint.color = Color.RED
        scorePaint.textSize = TEXT_SIZE.toFloat()
        scorePaint.textAlign = Paint.Align.LEFT
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(background, 0f, 0f, null)
        canvas.drawText("Pt:$points", 0f, TEXT_SIZE.toFloat(), scorePaint)
        for (i in life downTo 1) {
            canvas.drawBitmap(lifeImage, (screenWidth - lifeImage.width * i).toFloat(), 0f, null)
        }
        if (life == 0) {
            paused = true
            handler.removeCallbacks(runnable)
            val intent = Intent(context, GameOver::class.java)
            intent.putExtra("points", points)
            context.startActivity(intent)
            (context as Activity).finish()
            return
        }
        enemySpaceship.ex += enemySpaceship.enemyVelocity
        if (enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() >= screenWidth) {
            enemySpaceship.enemyVelocity *= -1
        }
        if (enemySpaceship.ex <= 0) {
            enemySpaceship.enemyVelocity *= -1
        }
        if (!enemyShotAction && enemySpaceship.ex >= 200 + random.nextInt(400)) {
            val enemyShot = Shot(context, enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() / 2, enemySpaceship.ey)
            enemyShots.add(enemyShot)
            enemyShotAction = true
        }
        if (!enemyExplosion) {
            canvas.drawBitmap(enemySpaceship.getEnemySpaceship(), enemySpaceship.ex.toFloat(), enemySpaceship.ey.toFloat(), null)
        }
        if (ourSpaceship.isAlive) {
            if (ourSpaceship.ox > screenWidth - ourSpaceship.getOurSpaceshipWidth()) {
                ourSpaceship.ox = (screenWidth - ourSpaceship.getOurSpaceshipWidth()).toFloat().toInt()
            } else if (ourSpaceship.ox < 0) {
                ourSpaceship.ox = 0
            }
            canvas.drawBitmap(ourSpaceship.getOurSpaceship(), ourSpaceship.ox.toFloat(), ourSpaceship.oy.toFloat(), null)
        }
        var i = 0
        while (i < enemyShots.size) {
            enemyShots[i].shy += 15
            canvas.drawBitmap(enemyShots[i].getShot(), enemyShots[i].shx.toFloat(), enemyShots[i].shy.toFloat(), null)
            if (enemyShots[i].shx >= ourSpaceship.ox &&
                enemyShots[i].shx <= ourSpaceship.ox + ourSpaceship.getOurSpaceshipWidth() &&
                enemyShots[i].shy >= ourSpaceship.oy &&
                enemyShots[i].shy <= screenHeight) {
                life--
                enemyShots.removeAt(i)
                var explosion = Explosion(context, ourSpaceship.ox, ourSpaceship.oy)
                explosions.add(explosion)
            } else if (enemyShots[i].shy >= screenHeight) {
                enemyShots.removeAt(i)
            }
            if (enemyShots.isEmpty()) {
                enemyShotAction = false
            }
            i++
        }
        i = 0
        while (i < ourShots.size) {
            ourShots[i].shy -= 15
            canvas.drawBitmap(ourShots[i].getShot(), ourShots[i].shx.toFloat(), ourShots[i].shy.toFloat(), null)
            if (ourShots[i].shx >= enemySpaceship.ex &&
                ourShots[i].shx <= enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() &&
                ourShots[i].shy <= enemySpaceship.getEnemySpaceshipHeight() &&
                ourShots[i].shy >= enemySpaceship.ey) {
                points++
                ourShots.removeAt(i)
                var explosion = Explosion(context, enemySpaceship.ex, enemySpaceship.ey)
                explosions.add(explosion)
            } else if (ourShots[i].shy <= 0) {
                ourShots.removeAt(i)
            }
            i++
        }
        i = 0
        while (i < explosions.size) {
            explosions[i].getExplosion(explosions[i].explosionFrame)?.let {
                canvas.drawBitmap(
                    it,
                    explosions[i].eX.toFloat(),
                    explosions[i].eY.toFloat(), null)
            }
            explosions[i].explosionFrame++
            if (explosions[i].explosionFrame > 8) {
                explosions.removeAt(i)
            }
            i++
        }
        if (!paused) {
            handler.postDelayed(runnable, UPDATE_MILLIS)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x.toInt()
        if (event.action == MotionEvent.ACTION_UP) {
            if (ourShots.size < 3) {
                val ourShot = Shot(context, ourSpaceship.ox + ourSpaceship.getOurSpaceshipWidth() / 2, ourSpaceship.oy)
                ourShots.add(ourShot)
            }
        }
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
            ourSpaceship.ox = touchX.toFloat().toInt()
        }
        return true
    }

    companion object {
        var screenWidth = 0
        var screenHeight = 0
        var enemyExplosion = false
        var enemyShotAction = false
    }
}
