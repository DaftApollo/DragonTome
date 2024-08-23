package com.moke.dragontome.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.getField
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
                currentUser = auth.currentUser
                firestoreDB.collection("users").document("${currentUser!!.uid}")
                    .set(User(email = email))
                onSuccess()
            }
            else {
                onFailure()
                currentUser = auth.currentUser
            }
        }

    }

    fun signInUser(email:String, password:String, onFailure: () -> Unit, onSuccess: () -> Unit){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {task ->
            if(task.isSuccessful){
                currentUser = auth.currentUser
                onSuccess()
            }
            else{
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
            }
                .addOnFailureListener() {
                }


        }
        else{
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


        var campaign:Campaign = Campaign(ID = randomId)
        campaign.name = name
        campaign.membersIDs = campaign.membersIDs.plus(currentUser!!.uid)
        campaign.memberProfiles = campaign.memberProfiles.plus(CampaignMember(currentUser!!.uid))
        campaign.gameMaster = currentUser!!.uid

        firestoreDB.collection("campaigns").document(randomId).set(campaign)
            .addOnSuccessListener {
                currentCampaign = randomId
                campaignCodes = campaignCodes.plus(randomId)
                updateUser()
            }
            .addOnFailureListener{
            }
    }

fun deleteCampaign(campaignCode: String){
    firestoreDB.collection("campaigns").document(campaignCode).delete().addOnSuccessListener {
    }
}

    fun updateUser(){
        if(currentUser != null){
            firestoreDB.collection("users").document(currentUser!!.uid).update("campaignList", campaignCodes).addOnSuccessListener {
            } .addOnFailureListener(){
            }
        }

    }

    fun signOut(){
        auth.signOut()
        currentUser = null
    }

    fun addUserToCampaign(email:String, campaignCode:String, campaign: Campaign, onSuccess: () -> Unit){
        val query = firestoreDB.collection("users").whereEqualTo("email", email)
            .get().addOnSuccessListener { documents ->
                for (document in documents){

                    document.reference.update("campaignList", FieldValue.arrayUnion(campaignCode))
                    if(!campaign.membersIDs.contains(document.id)){
                        campaign.membersIDs = campaign.membersIDs.plus(document.id)
                        if (campaign.memberProfiles.filter { it.userID == document.id }.isEmpty())
                            campaign.memberProfiles = campaign.memberProfiles.plus(CampaignMember(userID = document.id))
                    }
                    onSuccess()
                }
            }.addOnFailureListener{exception ->
            }


    }
    fun removeUserfromCampaign(userID:String, campaignCode:String){
        firestoreDB.collection("users").document(userID).update("campaignList", FieldValue.arrayRemove(campaignCode))
            .addOnSuccessListener {
            }
    }

    fun addCharacter(owner:String, campaignCode: String){
        firestoreDB.collection("campaigns").document(campaignCode).update("characterList", FieldValue.arrayUnion(OnlineCharacterSheetHolder(owner = owner)))
            .addOnSuccessListener {
            }
    }

    fun deleteCharacter(onlineCharacterSheetHolder: OnlineCharacterSheetHolder, campaignCode: String){
        firestoreDB.collection("campaigns").document(campaignCode).update("characterList", FieldValue.arrayRemove(onlineCharacterSheetHolder))
            .addOnSuccessListener {
            }
    }

}