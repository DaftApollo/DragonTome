package com.example.dragontome.state

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.dragontome.data.Campaign
import com.example.dragontome.data.FirebaseObject
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
                Log.d("debug", "Campaign Item View Model. Grabbed Campaign: ${campaign.toString()}")
            }
            .addOnFailureListener(){e ->
                Log.d("debug", "Failed to grab campaign within Campaign Item view Model")
            }
    }
}