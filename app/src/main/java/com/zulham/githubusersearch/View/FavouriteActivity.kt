package com.zulham.githubusersearch.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zulham.githubusersearch.Adapter.FavouriteAdapter
import com.zulham.githubusersearch.Adapter.ListUserAdapter
import com.zulham.githubusersearch.Database.db.FavHelper
import com.zulham.githubusersearch.Database.entity.FavUser
import com.zulham.githubusersearch.Model.User
import com.zulham.githubusersearch.R
import kotlinx.android.synthetic.main.activity_favourite.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi

class FavouriteActivity : AppCompatActivity() {

    private val listFav = ArrayList<FavUser>()

    private lateinit var favHelper: FavHelper

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        rv_FavUser.setHasFixedSize(true)

        recycleFavUser()

        setUpToolbar()

        favHelper = FavHelper.getInstance(applicationContext)
        favHelper.open()
    }

    override fun onDestroy() {
        super.onDestroy()
        favHelper.close()
    }

    private fun recycleFavUser() {

        rv_FavUser.layoutManager = LinearLayoutManager(this)

        val favouriteAdapter = FavouriteAdapter(listFav)

        rv_FavUser.adapter = favouriteAdapter

    }

    private fun setUpToolbar() {
        supportActionBar?.setTitle("Favourite User")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}