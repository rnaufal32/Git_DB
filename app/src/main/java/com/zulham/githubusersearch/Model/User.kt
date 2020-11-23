package com.zulham.githubusersearch.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: Int,
    var login: String,
    var avatar_url: String
) : Parcelable