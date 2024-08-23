package com.moke.dragontome.data

data class Campaign
    (var name: String = "",

     var characterList:List<OnlineCharacterSheetHolder> = emptyList(),

     var chatLog: List<Message> = emptyList(),

     var membersIDs: List<String> = emptyList(),

     var memberProfiles: List<CampaignMember> = emptyList(),

     var gameMaster: String = "",

     var ID:String = "",

     var isDeleted: Boolean = false
            )
