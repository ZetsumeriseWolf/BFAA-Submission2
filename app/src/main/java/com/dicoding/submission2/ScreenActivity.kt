package com.dicoding.submission2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dicoding.submission2.databinding.ActivityScreenBinding

class ScreenActivity : AppCompatActivity() {

    private lateinit var screenBinding: ActivityScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenBinding = ActivityScreenBinding.inflate(layoutInflater)
        setContentView(screenBinding.root)

        val handlerScreen = Handler(mainLooper)

        handlerScreen.postDelayed({
            val intent = Intent(this@ScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME_SCREEN_ACTIVITY)

        supportActionBar?.hide()
    }

    companion object {
        const val TIME_SCREEN_ACTIVITY = 1500L
    }
}