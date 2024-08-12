package com.example.dragontome.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
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
                firestoreDB.collection("users").document("${currentUser!!.uid}")
                    .set(User(email = email))
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
                Log.d("debug", "Campaign code list before calling updateUser: ${campaignCodes}")
                updateUser()
            }
            .addOnFailureListener{
                Log.d("debug", "Failure to create campaign")
            }
    }



    fun updateUser(){
        if(currentUser != null){
            firestoreDB.collection("users").document(currentUser!!.uid).update("campaignList", campaignCodes).addOnSuccessListener {
                Log.d("debug", "Should have updated user ID ${currentUser!!.uid}}")
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

    fun addUserToCampaign(email:String, campaignCode:String, campaign: Campaign, onSuccess: () -> Unit){
        val query = firestoreDB.collection("users").whereEqualTo("email", email)
            .get().addOnSuccessListener { documents ->
                for (document in documents){
                    document.reference.update("campaignList", FieldValue.arrayUnion(campaignCode))
                    Log.d("debug", "Should have added a new user to campaign.")
                    Log.d("debug", "Document ID: ${document.id}")
                    campaign.membersIDs = campaign.membersIDs.plus(document.id)
                    campaign.memberProfiles = campaign.memberProfiles.plus(CampaignMember(userID = document.id))

                    Log.d("debug", "Should have updated relevant campaign fields")
                    onSuccess()
                }
            }.addOnFailureListener{exception ->
                Log.d("debug", exception.toString())
            }


    }
    fun removeUserfromCampaign(userID:String, campaignCode:String){
        firestoreDB.collection("users").document(userID).update("campaignList", FieldValue.arrayRemove(campaignCode))
            .addOnSuccessListener {
                Log.d("debug", "Should have removed a user from the campaign")
            }
    }

    fun addCharacter(owner:String, campaignCode: String){
        firestoreDB.collection("campaigns").document(campaignCode).update("characterList", FieldValue.arrayUnion(OnlineCharacterSheetHolder(owner = owner)))
            .addOnSuccessListener {
                Log.d("debug", "Should have added a character")
            }
    }

    fun deleteCharacter(onlineCharacterSheetHolder: OnlineCharacterSheetHolder, campaignCode: String){
        firestoreDB.collection("campaigns").document(campaignCode).update("characterList", FieldValue.arrayRemove(onlineCharacterSheetHolder))
            .addOnSuccessListener {
                Log.d("debug", "Should have removed a character")
            }
    }

}