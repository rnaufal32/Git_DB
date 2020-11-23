package com.zulham.githubusersearch.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zulham.githubusersearch.R
import com.zulham.githubusersearch.Model.User
import kotlinx.android.synthetic.main.user.view.*

class ListUserAdapter(private val users: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(user: User) {
            with(itemView){
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .apply(RequestOptions().override(55, 55))
                    .into(userImg)


                userId.text = user.login
                IDuser.text = user.id.toString()

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size
}