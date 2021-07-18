package com.alaaeddinalbarghoth.mygallery.presentation.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.alaaeddinalbarghoth.mygallery.R
import com.alaaeddinalbarghoth.mygallery.presentation.activities.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // This is an optional screen
        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                showNextView()
            }, 500)
        }

    }

    private fun showNextView() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}