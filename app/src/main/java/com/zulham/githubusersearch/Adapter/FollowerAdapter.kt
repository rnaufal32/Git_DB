package com.zulham.githubusersearch.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zulham.githubusersearch.Model.User
import com.zulham.githubusersearch.R
import kotlinx.android.synthetic.main.user.view.*

class FollowerAdapter(private val follower: ArrayList<User>) : RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {
    inner class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind (follower: User) {

            with(itemView) {
                Glide.with(itemView.context)
                    .load(follower.avatar_url)
                    .apply(RequestOptions().override(55, 55))
                    .into(userImg)
                userId.text = follower.login
                IDuser.text = follower.id.toString()
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowerAdapter.FollowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user, parent, false)
        return FollowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowerAdapter.FollowerViewHolder, position: Int) {
        holder.bind(follower[position])
    }

    override fun getItemCount(): Int = follower.size
}