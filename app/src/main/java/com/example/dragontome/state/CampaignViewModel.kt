package com.example.dragontome.state

import android.util.Log
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.dragontome.data.Campaign
import com.example.dragontome.data.CampaignMember
import com.example.dragontome.data.FirebaseObject
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

    init {
        val campaignRef = fireBaseObject.firestoreDB.collection("campaigns").document(fireBaseObject.currentCampaign!!)
        campaignRef.addSnapshotListener{ snapshot, e ->
            if(e != null){
                Log.d("debug", "On snapshot: $e")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                _campaign.value = snapshot.toObject<Campaign>()!!

               // Log.d("debug", "Should have updated the campaign: ${campaign.toString()}")
            }

        }
    }





}