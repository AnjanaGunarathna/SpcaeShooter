package com.example.spcaeshooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Explosion(context: Context, eX: Int, eY: Int) {
    private val explosion = Array<Bitmap?>(9) { null }
    var explosionFrame = 0
    var eX = 0
    var eY = 0

    init {
        explosion[0] = BitmapFactory.decodeResource(context.resources, R.drawable.explore)
        explosion[1] = BitmapFactory.decodeResource(context.resources, R.drawable.explore)
        explosion[2] = BitmapFactory.decodeResource(context.resources, R.drawable.explore)
        explosion[3] = BitmapFactory.decodeResource(context.resources, R.drawable.explore)
        explosion[4] = BitmapFactory.decodeResource(context.resources, R.drawable.explore)
        explosion[5] = BitmapFactory.decodeResource(context.resources, R.drawable.explore)
        explosion[6] = BitmapFactory.decodeResource(context.resources, R.drawable.explore)
        explosion[7] = BitmapFactory.decodeResource(context.resources, R.drawable.explore)
        explosion[8] = BitmapFactory.decodeResource(context.resources, R.drawable.explore)
        explosionFrame = 0
        this.eX = eX
        this.eY = eY
    }

    fun getExplosion(explosionFrame: Int): Bitmap? {
        return explosion[explosionFrame]
    }
}