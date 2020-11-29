package com.zulham.githubusersearch.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.animation.AnimationUtils
import com.zulham.githubusersearch.Database.db.FavHelper
import com.zulham.githubusersearch.Model.User
import com.zulham.githubusersearch.R
import kotlinx.coroutines.InternalCoroutinesApi

class SplashScreen : AppCompatActivity() {

    private lateinit var imgSplash: ImageView
    private lateinit var textSplash: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        imgSplash = findViewById(R.id.imgSplashScreen)
        textSplash = findViewById(R.id.splashscreen)

        imgSplash.alpha = 0f
        imgSplash.animate().setDuration(1500).alpha(1f).withEndAction {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

        textSplash.alpha = 0f
        textSplash.animate().setDuration(1500).alpha(1f).withEndAction {
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}