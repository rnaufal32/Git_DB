package com.zulham.githubusersearch.View

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zulham.githubusersearch.Adapter.ListUserAdapter
import com.zulham.githubusersearch.Database.db.FavHelper
import com.zulham.githubusersearch.Model.User
import com.zulham.githubusersearch.R
import com.zulham.githubusersearch.ViewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private var backPressed: Long = 0

    private lateinit var favHelper: FavHelper

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_User.setHasFixedSize(true)

        showLoading(true)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )

        mainViewModel.setUser("jpsim")

        mainViewModel.getIsError().observe(this, Observer {
            when (it) {
                true -> showErrorMessage()
                else -> showUser()
            }
        })

        favHelper = FavHelper.getInstance(applicationContext)
        favHelper.open()

    }

    override fun onDestroy() {
        super.onDestroy()
        favHelper.close()
    }

    private fun showErrorMessage() {
        mainViewModel.getErrorMessage().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun showUser() {
        mainViewModel.getUser().observe(this, Observer {

            recycleListUser(it)

            showLoading(false)
        })
    }

    private fun recycleListUser(listUser: ArrayList<User>) {

        rv_User.layoutManager = LinearLayoutManager(this)

        val listUserAdapter = ListUserAdapter(listUser)

        rv_User.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                val user = data
                intent.putExtra("user", user)
                startActivity(intent)
            }

        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()

                showLoading(true)

                mainViewModel.setUser(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.setting -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
            }

            R.id.favourite -> {
                val i = Intent(this, FavouriteActivity::class.java)
                startActivity(i)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        if (backPressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(baseContext, "Press again to exit", Toast.LENGTH_SHORT).show()
        }

        backPressed = System.currentTimeMillis()
    }

}