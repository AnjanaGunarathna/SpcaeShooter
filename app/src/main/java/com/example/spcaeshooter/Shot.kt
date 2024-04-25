package com.example.spcaeshooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Shot(context: Context, shx: Int, shy: Int) {
    private var shot: Bitmap
    private val context: Context = context
    var shx: Int = shx
        private set
    var shy: Int = shy

    init {
        shot = BitmapFactory.decodeResource(context.resources, R.drawable.shot)
        this.shx = shx
        this.shy = shy
    }

    fun getShot(): Bitmap {
        return shot
    }
}
