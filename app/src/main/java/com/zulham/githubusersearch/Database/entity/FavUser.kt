package com.zulham.githubusersearch.Database.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavUser(
    var id: Int = 0,
    var imgUser: String? = null,
    var IDUser: Int,
    var UserName: String? = null
):Parcelable