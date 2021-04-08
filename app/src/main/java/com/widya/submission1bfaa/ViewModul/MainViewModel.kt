package com.widya.submission1bfaa.ViewModul

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.widya.submission1bfaa.Model.User
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {

    val listuser = MutableLiveData<ArrayList<User>>()

    fun getUserGithub(): LiveData<ArrayList<User>> = listuser
    fun getSearchUser(): LiveData<ArrayList<User>> = listuser
    fun getFollowers(): LiveData<ArrayList<User>> = listuser
    fun getFollowing(): LiveData<ArrayList<User>> = listuser


    fun setUserGithub() {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val listUser = ArrayList<User>()
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObject = JSONArray(result)
                    for (i in 0 until responseObject.length()) {
                        val item = responseObject.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val url = item.getString("url")
                        val user = User(
                            username = username,
                            url = url,
                            avatar = avatar
                        )

                        listUser.add(user)
                    }
                    listuser.postValue(listUser)
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

    fun setDataByUsername(username: String) {
        val listItems = ArrayList<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$username"
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
                    val count = responseObject.getInt("total_count")
                    if (count >= 1) {
                        val items = responseObject.getJSONArray("items")

                        for (i in 0 until items.length()) {
                            val item = items.getJSONObject(i)
                            val user = User()
                            user.username = item.getString("login")
                            user.url = item.getString("url")
                            user.avatar = item.getString("avatar_url")
                            listItems.add(user)
                        }
                        listuser.postValue(listItems)

                    } else {
                        listuser.postValue(listItems)

                    }
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

    fun setFollowers(username: String) {
        val listItems = ArrayList<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/followers"
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
                    val responseArray = JSONArray(result)
                    if (responseArray.length() > 0) {
                        for (i in 0 until responseArray.length()) {
                            val item = responseArray.getJSONObject(i)
                            val user = User()
                            user.username = item.getString("login")
                            user.url = item.getString("url")
                            user.avatar = item.getString("avatar_url")
                            listItems.add(user)
                        }
                        listuser.postValue(listItems)

                    } else {
                        listuser.postValue(listItems)

                    }
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

    fun setFollowing(username: String) {
        val listItems = ArrayList<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/following"
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
                    val responseArray = JSONArray(result)
                    if (responseArray.length() > 0) {
                        for (i in 0 until responseArray.length()) {
                            val item = responseArray.getJSONObject(i)
                            val user = User()
                            user.username = item.getString("login")
                            user.url = item.getString("url")
                            user.avatar = item.getString("avatar_url")
                            listItems.add(user)
                        }
                        listuser.postValue(listItems)

                    } else {
                        listuser.postValue(listItems)

                    }
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
        private val TAG = MainViewModel::class.java.simpleName
    }
}