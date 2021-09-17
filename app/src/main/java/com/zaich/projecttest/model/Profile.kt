package com.zaich.projecttest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Profile(
    var name : String? =null,
    var url : String? = null,
    var uid : String? = null,
):Parcelable