package com.widya.submission1bfaa.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String?="",
    var name: String?="",
    var location: String?="",
    var repository: String?="",
    var company: String?="",
    var followers: String?="",
    var following: String?="",
    var url:String?="",
    var avatar: String?=""
) : Parcelable