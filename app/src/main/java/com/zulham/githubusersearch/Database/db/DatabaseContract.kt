package com.zulham.githubusersearch.Database.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class FavColumns: BaseColumns{
        companion object{
            const val TABLE_NAME = "Favourite"
            const val _ID = "_id"
            const val IMG_USER = "User Image"
            const val USER_ID = "User ID"
            const val USER_NAME = "User Name"
        }
    }

}