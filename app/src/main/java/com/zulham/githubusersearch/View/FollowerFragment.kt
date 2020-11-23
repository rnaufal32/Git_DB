package com.zulham.githubusersearch.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zulham.githubusersearch.Adapter.FollowerAdapter
import com.zulham.githubusersearch.Model.User
import com.zulham.githubusersearch.R
import com.zulham.githubusersearch.ViewModel.FollowerViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.fragment_follower.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FollowerFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var followerViewModel: FollowerViewModel
    private lateinit var rvFollower: RecyclerView

    companion object{

        val USERNAME = "username"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(USERNAME)

        rvFollower = view.findViewById(R.id.rv_Follower)
        rvFollower.layoutManager = LinearLayoutManager(activity)

        showLoading(true)

        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)
        username?.let { followerViewModel.setFollower(it) }

        followerViewModel.getIsError().observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> showErrorMessage()
                else -> showFollower()
            }
        })
    }

    private fun showFollower() {
        followerViewModel.getFollower().observe(viewLifecycleOwner, {
            Toast.makeText(view?.context, it.size.toString(), Toast.LENGTH_SHORT).show()
            listFollower(it)

            showLoading(false)
        })
    }

    private fun showErrorMessage() {
        followerViewModel.getErrorMessage().observe(viewLifecycleOwner, Observer {
            Toast.makeText(view?.context, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun listFollower(users: ArrayList<User>) {
        val adapter = FollowerAdapter(users)
        rvFollower.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            followerprogressBar.visibility = View.VISIBLE
        } else {
            followerprogressBar.visibility = View.GONE
        }
    }
}