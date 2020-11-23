package com.zulham.githubusersearch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.zulham.githubusersearch.Model.User
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowerViewModel: ViewModel() {

    private val listFollower = MutableLiveData<ArrayList<User>>()

    private val isError: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    private val errorMessage = MutableLiveData<String>()

    fun setFollower(login: String){

        val data = ArrayList<User>()

        val client = AsyncHttpClient()

        val url = "https://api.github.com/users/$login/followers"

        client.addHeader("Authorization", "token 236f36197e2b0f563eb236ed80a67d3edcdc288d")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {

                try {
                    val result = responseBody?.let { String(it) }

                    val res = JSONArray(result)

                    for (i in 0 until res.length()){
                        val jsonObj = res.getJSONObject(i)
                        data.add(
                            User(
                                id = jsonObj.getInt("id"),
                                login = jsonObj.getString("login"),
                                avatar_url = jsonObj.getString("avatar_url")
                            )
                        )

                    }

                    listFollower.value = data

                } catch (e: Exception) {
                    e.message?.let { setError(true, it) }
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessages =
                    when (statusCode) {
                        401 -> "$statusCode : Bad Request"
                        403 -> "$statusCode : Forbidden"
                        404 -> "$statusCode : Not Found"
                        else -> "$statusCode : ${error?.message}"
                    }
                isError.value = true
                errorMessage.value = errorMessages
            }
        })
    }

    fun getFollower(): LiveData<ArrayList<User>> {
        return listFollower
    }

    fun setError(error: Boolean, message: String) {
        isError.value = error
        errorMessage.value = message
    }

    fun getIsError(): LiveData<Boolean> {
        return isError
    }

    fun getErrorMessage(): LiveData<String> {
        return errorMessage
    }
}