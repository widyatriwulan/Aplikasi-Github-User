package com.widya.submission1bfaa.ViewModul

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.widya.submission1bfaa.Model.User
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailViewModel : ViewModel() {

    val detailUser = MutableLiveData<User>()

    fun getDetailUser(): LiveData<User> = detailUser

    fun setDetailUser(username: String) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {

                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    val user = User()
                    user.username = responseObject.getString("login")
                    user.name = responseObject.getString("name")
                    user.avatar = responseObject.getString("avatar_url")
                    user.repository = responseObject.getString("public_repos")
                    user.company = responseObject.getString("company")
                    user.location = responseObject.getString("location")
                    user.followers = responseObject.getString("followers")
                    user.following = responseObject.getString("following")

                    detailUser.postValue(user)


                } catch (e: Exception) {
                    Log.d(TAG, e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error?.message.toString())

            }
        })
    }

    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
    }

}