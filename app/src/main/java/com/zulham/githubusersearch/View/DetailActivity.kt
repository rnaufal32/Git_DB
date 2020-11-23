package com.zulham.githubusersearch.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.loopj.android.http.AsyncHttpClient.log
import com.zulham.githubusersearch.Adapter.PagerAdapter
import com.zulham.githubusersearch.R
import com.zulham.githubusersearch.Model.User
import com.zulham.githubusersearch.ViewModel.DetailViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import com.zulham.githubusersearch.Model.UserDetail as UserDetail

class DetailActivity : AppCompatActivity() {

    private lateinit var userImage: CircleImageView
    private lateinit var userName: TextView
    private lateinit var userLoc: TextView
    private lateinit var userComp: TextView
    private lateinit var userRepos: TextView
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userImage = findViewById(R.id.userdetailImg)
        userName = findViewById(R.id.userName)
        userLoc = findViewById(R.id.userLoc)
        userComp = findViewById(R.id.userCompny)
        userRepos = findViewById(R.id.userRepo)

        val sectionsPagerAdapter = PagerAdapter(this, supportFragmentManager)
        VPList.adapter = sectionsPagerAdapter
        tabLayout.setupWithViewPager(VPList)

        showLoading(true)

        val user = intent.getParcelableExtra<User>("user")

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        user.let {
            sectionsPagerAdapter.username = it?.login
            detailViewModel.setDetail(it?.login)
        }


        detailViewModel.getIsError().observe(this, Observer {
            when (it) {
                true -> showErrorMessage()
                else -> showDetail()
            }
        })

    }

    private fun showDetail(){

        detailViewModel.getDetail().observe(this, {
            detail(it)

            showLoading(false)
        })

    }

    private fun showErrorMessage() {
        detailViewModel.getErrorMessage().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun detail(user: UserDetail) {
        Glide.with(this@DetailActivity)
            .load(user.avatar_url)
            .apply(RequestOptions().override(110, 110))
            .into(userImage)

        userName.text = user.name
        userLoc.text = user.location
        userComp.text = user.company
        userRepos.text = user.repository.toString()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            detailprogressBar.visibility = View.VISIBLE
        } else {
            detailprogressBar.visibility = View.GONE
        }
    }

}