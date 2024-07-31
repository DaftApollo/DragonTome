package com.example.dragontome.data

data class Campaign
    (var name: String = "",

     var characterList:List<CharacterSheetHolder> = emptyList(),

     var chatLog: List<Message> = emptyList(),

     var membersIDs: List<String> = emptyList(),

     var memberProfiles: List<CampaignMember> = emptyList(),

     var gameMaster: String = "",

     var ID:String = "")
