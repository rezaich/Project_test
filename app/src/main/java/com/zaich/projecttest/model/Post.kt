package com.zaich.projecttest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Post(
    var text : String? = null ,
    var uid : String? =null,
//    var name : String?=null,
//    var profileUri : String?=null,
    var type : String? = null,
    var postUri : String? = null,
    var time : String? = null
)
