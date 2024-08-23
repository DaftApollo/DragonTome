package com.moke.dragontome.state

import android.util.Log
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.moke.dragontome.data.Campaign
import com.moke.dragontome.data.CampaignMember
import com.moke.dragontome.data.FirebaseObject
import com.moke.dragontome.data.OnlineCharacterSheetHolder
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect

class CampaignViewModel(fireBaseObject:FirebaseObject): ViewModel() {
    var fireBaseObject = fireBaseObject

    private var _campaign = MutableStateFlow<Campaign?>(null)
    var campaign = _campaign.asStateFlow()

    var currentCharacter = OnlineCharacterSheetHolder()

    var isDeleted = false
    init {
        val campaignRef = fireBaseObject.firestoreDB.collection("campaigns").document(fireBaseObject.currentCampaign!!)
        campaignRef.addSnapshotListener{ snapshot, e ->
            if(e != null){
                return@addSnapshotListener
            }
            if (snapshot != null && !isDeleted) {
                _campaign.value = snapshot.toObject<Campaign>()!!
            }

        }
    }
}