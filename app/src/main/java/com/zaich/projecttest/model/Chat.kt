package com.zaich.projecttest.model

data class Chat(
    var senderId: String = "",
    var receiverId: String = "",
    var message: String = "",
    var type : String? = "",
    var time : String? = "",
    var date : String? = ""
)