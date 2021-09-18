package com.zaich.projecttest.model

data class Chat(
    var senderUid: String? = "",
    var receiverUid: String? = "",
    var message: String? = "",
    var type : String? = "",
    var time : String? = ""
)