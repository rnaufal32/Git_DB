package com.zulham.githubusersearch.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zulham.githubusersearch.R

class FavouriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        setUpToolbar()
    }

    private fun setUpToolbar() {
        supportActionBar?.setTitle("Favourite User")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}