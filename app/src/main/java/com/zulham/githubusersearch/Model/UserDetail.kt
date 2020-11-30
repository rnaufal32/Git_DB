package com.zulham.githubusersearch.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDetail(

    var name: String,
    var location: String,
    var company: String,
    var repository: Int,
    var avatar_url: String,
    var id: Int

) : Parcelable
