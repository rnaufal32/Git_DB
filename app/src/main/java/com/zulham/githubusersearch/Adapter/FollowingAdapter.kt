package com.zulham.githubusersearch.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zulham.githubusersearch.Model.User
import com.zulham.githubusersearch.R
import kotlinx.android.synthetic.main.user.view.*

class FollowingAdapter(private val following: ArrayList<User>) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind (following: User){

            with(itemView) {
                Glide.with(itemView.context)
                    .load(following.avatar_url)
                    .apply(RequestOptions().override(55, 55))
                    .into(userImg)
                userId.text = following.login
                IDuser.text = following.id.toString()
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowingAdapter.FollowingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user, parent, false)
        return FollowingViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingAdapter.FollowingViewHolder, position: Int) {
        holder.bind(following[position])
    }

    override fun getItemCount(): Int = following.size

}