package com.moke.dragontome.state

import androidx.lifecycle.ViewModel
import com.moke.dragontome.data.Campaign
import com.moke.dragontome.data.FirebaseObject
import com.moke.dragontome.data.OnlineCharacterSheetHolder
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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