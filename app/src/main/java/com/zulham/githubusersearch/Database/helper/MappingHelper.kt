package com.zulham.githubusersearch.Database.helper

import android.database.Cursor
import com.zulham.githubusersearch.Database.db.DatabaseContract
import com.zulham.githubusersearch.Database.entity.FavUser

object MappingHelper {

    fun mapCursorToArrayList(favsCursor: Cursor?): ArrayList<FavUser> {
        val favsList = ArrayList<FavUser>()
        favsCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavColumns._ID))
                val imgUser = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.IMG_USER))
                val userID = getInt(getColumnIndexOrThrow(DatabaseContract.FavColumns.USER_ID))
                val UserName = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.USER_NAME))
                favsList.add(FavUser(id, imgUser, userID, UserName))
            }
        }
        return favsList
    }

}