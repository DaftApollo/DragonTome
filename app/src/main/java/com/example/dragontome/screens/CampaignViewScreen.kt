package com.example.dragontome.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Tab
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dragontome.R
import com.example.dragontome.data.Campaign
import com.example.dragontome.data.CampaignMember
import com.example.dragontome.data.DiceRollerObject
import com.example.dragontome.data.FirebaseObject
import com.example.dragontome.data.Message
import com.example.dragontome.state.CampaignViewModel
import com.example.dragontome.ui.theme.primaryContainerLight
import com.example.dragontome.ui.theme.primaryLight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

enum class CampaignViewScreens{
    ROLLS_AND_CHAT,
    CHARACTERS,
    NOTES,
    SETTINGS
}


@Composable
fun CampaignViewScreen(firebaseObject: FirebaseObject, viewModel: CampaignViewModel = CampaignViewModel(fireBaseObject = firebaseObject)){
    var navController:NavHostController = rememberNavController()

    val campaign by viewModel.campaign.collectAsStateWithLifecycle()
    SideEffect {
        Log.d("debug", "Within the CampaignViewScreen. Current value of the campaign: ${campaign.toString()}")
    }



    if(campaign != null){
        Scaffold(topBar = { CampaignNavBar(navController = navController)}) {innerPadding ->


            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.grunge_background),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.matchParentSize()
                )

                NavHost(
                    navController = navController,
                    startDestination = CampaignViewScreens.ROLLS_AND_CHAT.name,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(route = CampaignViewScreens.ROLLS_AND_CHAT.name) {
                        CampaignChat(campaign = campaign!!, firebaseObject = firebaseObject, viewModel = viewModel)
                    }
                    composable(route = CampaignViewScreens.CHARACTERS.name) {
                        CampaignCharacterOverview(viewModel = viewModel)
                    }
                    composable(route = CampaignViewScreens.NOTES.name) {
                        CampaignNotes(viewModel = viewModel)
                    }
                    composable(route = CampaignViewScreens.SETTINGS.name) {
                        CampaignSettings(campaign = campaign!!, firebaseObject = firebaseObject)

                    }
                }
            }
            
            var openChangeNicknameDialogue by remember {
                mutableStateOf(false)
            }

            SideEffect {
                if(campaign != null){
                    Log.d("debug", "Member Profile: ${getMemberProfile(campaign!!, firebaseObject.currentUser!!.uid)}")
                }
            }

            if(campaign != null && getMemberProfile(campaign!!, firebaseObject.currentUser!!.uid)?.nickname == null){
                Log.d("debug", "Nickname read as null.")
                openChangeNicknameDialogue = true
            }

            if(openChangeNicknameDialogue){
                ChangeNicknameDialogue(
                    memberProfile = getMemberProfile(campaign = campaign!!, firebaseObject.currentUser!!.uid)!!,
                    onDismissRequest = {
                        updateCampaign(campaign = campaign!!, firebaseObject = firebaseObject)
                        openChangeNicknameDialogue = false
                    })
            }

        }
    }
    else{
        SideEffect {
            Log.d("debug", "Current Campaign found to be null")
        }

    }


}

@Composable
fun CampaignNavBar(navController: NavHostController) {
    var selectedItem by remember {
        mutableIntStateOf(0)
    }

    TabRow(selectedTabIndex = selectedItem, contentColor = Color.Black,
        containerColor = primaryLight,
        modifier = Modifier
            .border(width = 2.dp, color = Color.DarkGray)
            .height(50.dp)
    ) {
        Tab(selected = selectedItem == 0, onClick = {
            selectedItem = 0
            navController.navigate(CampaignViewScreens.ROLLS_AND_CHAT.name)
        },
            modifier = Modifier.border(width = 1.dp, color = Color.LightGray)) {
            Text(text = "Rolls/Chat")
        }
        Tab(selected = selectedItem == 1, onClick = {
            selectedItem = 1
            navController.navigate(CampaignViewScreens.CHARACTERS.name)
        },
            modifier = Modifier.border(width = 1.dp, color = Color.LightGray)) {
            Text(text = "Characters")
        }
        Tab(selected = selectedItem == 2, onClick = {
            selectedItem = 2
            navController.navigate(CampaignViewScreens.NOTES.name)
        },
            modifier = Modifier.border(width = 1.dp, color = Color.LightGray)) {
            Text(text = "Notes")
        }
        Tab(selected = selectedItem == 3, onClick = {
            selectedItem = 3
            navController.navigate(CampaignViewScreens.SETTINGS.name)
        },
            modifier = Modifier.border(width = 1.dp, color = Color.LightGray)) {
            Text(text = "Settings")
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CampaignChat(campaign: Campaign, firebaseObject: FirebaseObject, viewModel: CampaignViewModel) {

    var flag by remember {
        mutableStateOf(false)
    }

   SideEffect {
        if (campaign.chatLog.isNotEmpty()) {
            campaign.chatLog = campaign.chatLog.sortedBy { it.timeStamp }
        }
    }

    LaunchedEffect(key1 = null) {
        refreshChat(everySecond = {
            flag = !flag
            Log.d("debug","State of flag: ${flag}")
        })
    }

    Log.d("debug", "Printed chatlog")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        val state = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyColumn(modifier = Modifier.fillMaxHeight(0.80f), state = state, contentPadding = PaddingValues(10.dp)) {
            flag = flag
            items(campaign.chatLog) { message ->
                MessageItem(message = message, campaign = campaign, firebaseObject = firebaseObject)
            }

        }
            coroutineScope.launch { state.scrollToItem(index = state.layoutInfo.totalItemsCount) }
//TODO: Add logic to not scroll to bottom if the user has scrolled up to view previous messages. Getting pulled down all the time while trying to view previous messages is fucking annoying

    var text by remember {
        mutableStateOf("")
    }
    TextField(
        value = text,
        onValueChange = { text = it },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp)),
        trailingIcon = {
            IconButton(onClick = {
               if(text != "") {
                    SendMessage(
                        campaign,
                        firebaseObject,
                        Message(userID = firebaseObject.currentUser!!.uid, text = text)
                    )
                    text = ""
                }
            }) {
                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Send")
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
        keyboardActions = KeyboardActions(onGo = {
            if(text != ""){
                SendMessage(
                    campaign,
                    firebaseObject,
                    Message(userID = firebaseObject.currentUser!!.uid, text = text)
                )
                text = ""
            }
        })
    )
}
}

fun SendMessage(campaign: Campaign, firebaseObject: FirebaseObject, message: Message){
    val diceRoller:DiceRollerObject = DiceRollerObject

    if(diceRoller.detectRoll(message.text)){
            message.isRoll = true
            message.text = diceRoller.doRoll(message.text)
    }

    campaign.chatLog = campaign.chatLog.plus(message)

    updateCampaign(campaign, firebaseObject)
}

suspend fun refreshChat(everySecond:() -> Unit){
    delay(1000)
    Log.d("debug", "Delayed function")
    everySecond()
    refreshChat(everySecond)
}

@Composable
fun CampaignCharacterOverview(viewModel: CampaignViewModel){

}

@Composable
fun CampaignNotes(viewModel: CampaignViewModel){

}

@Composable
fun CampaignSettings(campaign: Campaign, firebaseObject: FirebaseObject){

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Player Settings", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Divider(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp), thickness = 2.dp, color = Color.Black)

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            var openNameChangeDialogue by remember {
                mutableStateOf(false)
            }

            Text(text = "Name: ", modifier = Modifier
                .fillMaxWidth(0.25f)
                .height(60.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 0.dp,
                        bottomStart = 8.dp,
                        bottomEnd = 0.dp
                    )
                )
                .padding(20.dp))
            TextButton(onClick = { openNameChangeDialogue = true }) {
                Text(text = getMemberProfile(campaign, firebaseObject.currentUser!!.uid)?.nickname.toString(), maxLines = 1, overflow = TextOverflow.Ellipsis, color = Color.Black,
                    modifier = Modifier.fillMaxWidth(0.75f), textAlign = TextAlign.Left)
            }

            if(openNameChangeDialogue){
                ChangeNicknameDialogue(
                    memberProfile = getMemberProfile(campaign = campaign!!, firebaseObject.currentUser!!.uid)!!,
                    onDismissRequest = {
                        updateCampaign(campaign = campaign, firebaseObject = firebaseObject)
                        openNameChangeDialogue = false
                    })
            }

        }

        if(campaign.gameMaster == firebaseObject.currentUser!!.uid){
            Text(text = "DM Settings", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(top = 10.dp))
            Divider(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp), thickness = 2.dp, color = Color.Black)

            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                var openConfirmationDialog by remember {
                    mutableStateOf(false)
                }

                var expanded by remember {
                    mutableStateOf(false)
                }

                var selectedUser:CampaignMember? = null

                Text(text = "Transfer DM status to... ", modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(60.dp)
                    .padding(20.dp))

                IconButton(onClick = { expanded = true }, modifier = Modifier
                    .size(size = 20.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Change DM to...", modifier = Modifier.size(20.dp))
                }

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    for (campaignMember in campaign.memberProfiles){
                        if (firebaseObject.currentUser!!.uid != campaignMember.userID){
                            androidx.compose.material3.DropdownMenuItem(
                                text = { Text(text = if (campaignMember.nickname != null) campaignMember.nickname!! else campaignMember.userID, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                                onClick = {
                                    selectedUser = campaignMember
                                    openConfirmationDialog = true
                                    expanded = false
                                })
                        }
                    }
                }

                if(openConfirmationDialog){
                    ConfirmationDialog(
                        member = selectedUser,
                        campaign = campaign,
                        onDismissRequest = { openConfirmationDialog = false },
                        firebaseObject = firebaseObject
                    )
                }
            }

            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                var email by remember {
                    mutableStateOf("")
                }

                Text(text = "Invite Player:", modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .height(60.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 0.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 5.dp),
                    minLines = 2)

                androidx.compose.material3.TextField(
                    value = email,
                    onValueChange = {email = it},
                    placeholder = { Text(text = "Player email")},
                    colors = TextFieldDefaults.colors(unfocusedPlaceholderColor = Color.LightGray, unfocusedContainerColor = Color.White, focusedContainerColor = Color.LightGray),
                    trailingIcon = {
                        IconButton(onClick = { firebaseObject.addUserToCampaign(email, campaignCode = campaign.ID, campaign = campaign, onSuccess = { updateCampaign(campaign, firebaseObject)}) }) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "Add")
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = {firebaseObject.addUserToCampaign(email, campaignCode = campaign.ID, campaign = campaign, onSuccess = { updateCampaign(campaign, firebaseObject)})}),
                    singleLine = true

                )


            }

        }

    }

}

@Composable
fun MessageItem(message:Message, campaign: Campaign, firebaseObject: FirebaseObject){
    val user = getMemberProfile(campaign, memberID = message.userID)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if(message.userID == firebaseObject.currentUser!!.uid) Arrangement.End else Arrangement.Start) {
        Card(modifier = Modifier
            .padding(vertical = 5.dp)
            .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp)), shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = primaryContainerLight)) {
            Column(modifier =Modifier.padding(horizontal = 5.dp)) {
                if (user != null) {
                    Row (horizontalArrangement = if(message.userID == firebaseObject.currentUser!!.uid) Arrangement.End else Arrangement.Start) {
                        if(message.userID == firebaseObject.currentUser!!.uid && message.isRoll){
                            Icon(painter = painterResource(id = R.drawable.role_playing), contentDescription = "Roll", Modifier.size(18.dp))
                        }
                        Text(
                            text = (if (user.nickname != null) user.nickname else user.userID)!!,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = if(message.userID == firebaseObject.currentUser!!.uid) TextAlign.End else TextAlign.Start
                            //TODO: Get Username text alignment correct. Adding in a row to the column in order to put a roll icon for messages marked as rolls caused errors with previous alignment modifiers
                        )

                        if(message.userID != firebaseObject.currentUser!!.uid && message.isRoll){
                            Icon(painter = painterResource(id = R.drawable.role_playing), contentDescription = "Roll", Modifier.size(18.dp))
                        }
                    }

                } else {
                    Text(text = "User Not Found", fontStyle = FontStyle.Italic)
                }
                Text(
                    text = message.text,
                    fontSize = 16.sp,
                    modifier = if(message.userID == firebaseObject.currentUser!!.uid) Modifier
                        .padding(end = 10.dp)
                        .align(alignment = Alignment.End) else Modifier
                        .padding(start = 10.dp)
                        .align(alignment = Alignment.Start),
                    textAlign = if(message.userID == firebaseObject.currentUser!!.uid) TextAlign.End else TextAlign.Left,
                )
            }
        }
    }
}

@Composable
fun ChangeNicknameDialogue(
    memberProfile:CampaignMember,
    onDismissRequest: () -> Unit){
    Dialog(onDismissRequest = onDismissRequest) {
        Card (modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = primaryLight),
            shape = RoundedCornerShape(16.dp)) {
            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Set your name within this campaign. It can be changed at any time.", textAlign = TextAlign.Center, modifier = Modifier.padding(10.dp), color = Color.Black)
                var name:String by remember {
                    mutableStateOf("")
                }
                
                var error:Boolean by remember {
                    mutableStateOf(false)
                }
                
                androidx.compose.material3.TextField(
                    value = name,
                    onValueChange = {name = it},
                    label = { Text(text = "Name:")},
                    placeholder = { Text(text = "Name")},
                    singleLine = true,
                    shape = RoundedCornerShape(size = 8.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color.LightGray, unfocusedContainerColor = Color.White, focusedLabelColor = Color.Black, unfocusedLabelColor = Color.Black)
                )

                
                if(error){
                    Text(text = "Name cannot be blank", textAlign = TextAlign.Center, color = Color.Red)
                }

                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                ) {

                    TextButton(onClick = {
                        if(!name.equals("")) { 
                            memberProfile.nickname = name
                            onDismissRequest()
                        }
                        else{
                            error = true
                        }
                    },
                        modifier = Modifier
                            .width(80.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(8.dp)
                            )
                        ) {
                        Text(text = "Confirm", color = Color.Black)
                    }
                }
                
                
            }
        }
    }
}

fun getMemberProfile(campaign:Campaign, memberID:String): CampaignMember? {
    if(campaign != null) {
        campaign!!.memberProfiles.forEach {
            if (it.userID == memberID) {
                return it
            }
        }
        Log.d("debug", "Exhausted the list of potential matches.")
    }
    Log.d("debug","Campaign's value found to be null. Returning null")
    return null
}

fun updateCampaign(campaign: Campaign, firebaseObject: FirebaseObject){
    if(campaign != null) {
        firebaseObject.firestoreDB.collection("campaigns")
            .document(firebaseObject.currentCampaign!!).set(campaign)
    }
}

@Composable
fun ConfirmationDialog(member: CampaignMember?, campaign: Campaign, onDismissRequest: () -> Unit, firebaseObject: FirebaseObject){
    if(member != null){
        Dialog(onDismissRequest = onDismissRequest) {
            Card (modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = primaryLight),
                shape = RoundedCornerShape(16.dp)) {
                Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Are you sure you'd like to transfer ownership of the campaign to." + if (member!!.nickname != null) "${member!!.nickname}" else "${member.userID}",
                        textAlign = TextAlign.Center, modifier = Modifier.padding(10.dp), color = Color.Black)

                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                    ) {

                        TextButton(onClick = {
                            onDismissRequest()
                        },
                            modifier = Modifier
                                .width(80.dp)
                                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            Text(text = "Cancel", color = Color.Red)
                        }

                        TextButton(onClick = {
                            campaign.gameMaster = member.userID
                            updateCampaign(campaign = campaign, firebaseObject = firebaseObject)
                            onDismissRequest()
                        },
                            modifier = Modifier
                                .width(80.dp)
                                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            Text(text = "Confirm", color = Color.Black)
                        }
                    }


                }
            }
        }
    }

}