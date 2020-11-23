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
import com.zulham.githubusersearch.ViewModel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_follower.*
import kotlinx.android.synthetic.main.fragment_follower.followerprogressBar
import kotlinx.android.synthetic.main.fragment_following.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FollowingFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var rvFollowing: RecyclerView

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(USERNAME)

        rvFollowing = view.findViewById(R.id.rv_Following)
        rvFollowing.layoutManager = LinearLayoutManager(activity)

        showLoading(true)

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        username?.let { followingViewModel.setFollowing(it) }

        followingViewModel.getIsError().observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> showErrorMessage()
                else -> showFollowing()
            }
        })

    }

    private fun showFollowing() {
        followingViewModel.getFollowing().observe(viewLifecycleOwner, {
            Toast.makeText(view?.context, it.size.toString(), Toast.LENGTH_SHORT).show()
            listFollowing(it)

            showLoading(false)
        })
    }

    private fun showErrorMessage() {
        followingViewModel.getErrorMessage().observe(viewLifecycleOwner, Observer {
            Toast.makeText(view?.context, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun listFollowing(users: ArrayList<User>) {
        val adapter = FollowerAdapter(users)
        rvFollowing.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            followingprogressBar.visibility = View.VISIBLE
        } else {
            followingprogressBar.visibility = View.GONE
        }
    }
}