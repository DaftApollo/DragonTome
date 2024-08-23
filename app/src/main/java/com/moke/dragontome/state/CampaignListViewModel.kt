package com.moke.dragontome.state

import androidx.lifecycle.ViewModel
import com.moke.dragontome.data.Campaign
import com.moke.dragontome.data.FirebaseObject
import com.moke.dragontome.data.User
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CampaignListViewModel (firebaseObject:FirebaseObject) :ViewModel() {
    private var _campaignCodeList = MutableStateFlow<List<String>>(emptyList())
    var campaignCodeList = _campaignCodeList.asStateFlow()
    var firebaseObject = firebaseObject

    init {
        getCampaignList()
    }


    fun getCampaignList(){
        firebaseObject.firestoreDB.collection("users")
            .document(firebaseObject.currentUser!!.uid).addSnapshotListener{value, error ->
                if(error != null){
                    return@addSnapshotListener
                }
                if(value != null){
                    _campaignCodeList.value = value.toObject<User>()!!.campaignList
                }
            }
    }
}