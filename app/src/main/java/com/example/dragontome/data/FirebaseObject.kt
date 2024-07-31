package com.example.dragontome.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

object FirebaseObject {

    val firestoreDB = Firebase.firestore
    private var auth: FirebaseAuth = Firebase.auth

    var currentUser = auth.currentUser

    var campaignCodes:List<String> = emptyList()
    var currentCampaign:String? = null

    fun registerUser(email: String, password:String, onFailure: () -> Unit, onSuccess: () -> Unit){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Log.d("debug", "createUserWithEmail: Success")
                currentUser = auth.currentUser
                firestoreDB.collection("users").document("${currentUser!!.uid}").set(User())
                onSuccess()
            }
            else {
                Log.w("debug", "createUserWithEmail:Failure", task.exception)
                onFailure()
                currentUser = auth.currentUser
            }
        }

    }

    fun signInUser(email:String, password:String, onFailure: () -> Unit, onSuccess: () -> Unit){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {task ->
            if(task.isSuccessful){
                Log.d("debug", "signInWIthEmail: Success")
                currentUser = auth.currentUser
                onSuccess()
            }
            else{
                Log.w("debug", "signInWIthEmail: Failure", task.exception)
                onFailure()
                currentUser = auth.currentUser
            }
        }
    }

    fun getCampaigns(){

       if(currentUser != null) {
            val docRef = firestoreDB.collection("users").document(currentUser!!.uid)
            docRef.get().addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject<User>()
                campaignCodes = user!!.campaignList
                Log.d("debug", "Campaigns: $campaignCodes")
            }
                .addOnFailureListener() {
                    Log.d("debug", "Failure to assign campaigns")
                }


        }
        else{
            Log.d("debug", "Current user is null!")
       }
    }

    fun createCampaign(name: String = ""){
        var charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        var randomId:String

        randomId = ThreadLocalRandom
            .current()
            .ints(6,0,charPool.size)
            .asSequence()
            .map(charPool::get)
            .joinToString("")
        Log.d("debug", "Random ID generated: $randomId")


        var campaign:Campaign = Campaign(ID = randomId)
        campaign.name = name
        campaign.membersIDs = campaign.membersIDs.plus(currentUser!!.uid)
        campaign.memberProfiles = campaign.memberProfiles.plus(CampaignMember(currentUser!!.uid))
        campaign.gameMaster = currentUser!!.uid

        firestoreDB.collection("campaigns").document(randomId).set(campaign)
            .addOnSuccessListener {
                Log.d("debug", "Should have created a new campaign with id ${campaign.ID}")
                currentCampaign = randomId
                Log.d("debug", "Campaign code list before calling the plus method: ${campaignCodes}")
                campaignCodes = campaignCodes.plus(randomId)
                val user = User(campaignList = campaignCodes)
                Log.d("debug", "Campaign code list before calling updateUser: ${user.campaignList}")
                updateUser(user)
            }
            .addOnFailureListener{
                Log.d("debug", "Failure to create campaign")
            }
    }



    fun updateUser(user: User){
        if(currentUser != null){
            firestoreDB.collection("users").document(currentUser!!.uid).set(user).addOnSuccessListener {
                Log.d("debug", "Should have updated user ID ${currentUser!!.uid} with ${user.toString()}")
            } .addOnFailureListener(){
                Log.d("debug", "Failed to update user data")
            }
        }

    }

    fun signOut(){
        auth.signOut()
        currentUser = null
        Log.d("debug", "Should have signed out. Current user: $currentUser, ${auth.currentUser}")
    }

}