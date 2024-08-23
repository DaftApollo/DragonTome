package com.moke.dragontome.state

import androidx.lifecycle.ViewModel
import com.moke.dragontome.data.Campaign
import com.moke.dragontome.data.FirebaseObject
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CampaignItemViewModel(firebaseObject:FirebaseObject, code: String) :ViewModel() {
    private var _campaign = MutableStateFlow<Campaign?>(null)
    var campaign = _campaign.asStateFlow()
    var firebaseObject = firebaseObject

    init {
        getCampaignById(code)
    }

    fun getCampaignById(id:String){
        firebaseObject.firestoreDB.collection("campaigns").document(id)
            .get().addOnSuccessListener { documentSnapshot ->
                _campaign.value = documentSnapshot.toObject<Campaign>()
            }
            .addOnFailureListener(){e ->
            }
    }
}