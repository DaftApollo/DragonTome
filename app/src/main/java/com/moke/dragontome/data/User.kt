package com.moke.dragontome.data

data class User(
    val email:String ="",
    var campaignList:List<String> = emptyList()
)
