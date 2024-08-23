package com.moke.dragontome.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Tab
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moke.dragontome.R
import com.moke.dragontome.data.Campaign
import com.moke.dragontome.data.CampaignMember
import com.moke.dragontome.data.DiceRollerObject
import com.moke.dragontome.data.FirebaseObject
import com.moke.dragontome.data.Message
import com.moke.dragontome.data.OnlineCharacterSheetHolder
import com.moke.dragontome.data.Spell
import com.moke.dragontome.state.AppViewModel
import com.moke.dragontome.state.CampaignViewModel
import com.moke.dragontome.ui.theme.primaryContainerLight
import com.moke.dragontome.ui.theme.primaryLight
import com.moke.dragontome.ui.theme.removeColor
import com.moke.dragontome.ui.theme.tertiaryContainerLight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class CampaignViewScreens{
    ROLLS_AND_CHAT,
    CHARACTERS,
    SPELLS,
    SETTINGS,
    CHARACTER
}


@Composable
fun CampaignViewScreen(firebaseObject: FirebaseObject, viewModel: CampaignViewModel = CampaignViewModel(fireBaseObject = firebaseObject), appviewModel:AppViewModel, navBackToCampaigns:() -> Unit){
    var navController:NavHostController = rememberNavController()

    val campaign by viewModel.campaign.collectAsStateWithLifecycle()


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
                        CampaignCharacterOverview(campaign = campaign!!, firebaseObject = firebaseObject, viewModel = viewModel, navController = navController)
                    }
                    composable(route = CampaignViewScreens.SPELLS.name) {
                        CampaignSpells(viewModel = viewModel, appviewModel = appviewModel, campaign = campaign!!, firebaseObject = firebaseObject)
                    }
                    composable(route = CampaignViewScreens.SETTINGS.name) {
                        CampaignSettings(campaign = campaign!!, firebaseObject = firebaseObject, navBackToCampaigns = navBackToCampaigns, viewModel = viewModel)
                    }
                    composable(route = CampaignViewScreens.CHARACTER.name) {
                        OnlineCharacterSheetScreen(onlineCharacterSheetHolder = viewModel.currentCharacter, viewModel = viewModel, campaign = campaign!!, firebaseObject = firebaseObject)
                    }
                }
            }
            
            var openChangeNicknameDialogue by remember {
                mutableStateOf(false)
            }

            if(campaign != null && getMemberProfile(campaign!!, firebaseObject.currentUser!!.uid)?.nickname == null){
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
            .height(75.dp)
            .border(width = 2.dp, color = Color.DarkGray)

    ) {
        Tab(selected = selectedItem == 0, onClick = {
            selectedItem = 0
            navController.navigate(CampaignViewScreens.ROLLS_AND_CHAT.name)
        },
            modifier = Modifier
                .height(75.dp)
                .border(width = 1.dp, color = Color.LightGray)) {
            Text(text = "Rolls/Chat")
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
        }
        Tab(selected = selectedItem == 1, onClick = {
            selectedItem = 1
            navController.navigate(CampaignViewScreens.CHARACTERS.name)
        },
            modifier = Modifier.border(width = 1.dp, color = Color.LightGray)) {
            Text(text = "Characters")
            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
        }
        Tab(selected = selectedItem == 2, onClick = {
            selectedItem = 2
            navController.navigate(CampaignViewScreens.SPELLS.name)
        },
            modifier = Modifier.border(width = 1.dp, color = Color.LightGray)) {
            Text(text = "Spells")
            Icon(imageVector = Icons.Filled.Star, contentDescription = "")
        }
        Tab(selected = selectedItem == 3, onClick = {
            selectedItem = 3
            navController.navigate(CampaignViewScreens.SETTINGS.name)
        },
            modifier = Modifier.border(width = 1.dp, color = Color.LightGray)) {
            Text(text = "Settings")
            Icon(imageVector = Icons.Filled.Settings, contentDescription = "")
        }
    }
}

@Composable
fun CampaignSpells(viewModel: CampaignViewModel, appviewModel: AppViewModel, campaign: Campaign, firebaseObject: FirebaseObject){
    OnlineSpellList(appViewModel = appviewModel, additionMode = true, viewModel = viewModel, updateFunction = {
        updateCampaign(campaign, firebaseObject)
    })
}

@Composable
fun OnlineSpellList(
    appViewModel: AppViewModel,
    spellList: List<Spell> = appViewModel.spellList,
    additionMode: Boolean,
    modifier: Modifier = Modifier,
    updateFunction: () -> Unit = {},
    viewModel: CampaignViewModel,
    isEditable: Boolean = viewModel.fireBaseObject.currentUser!!.uid == viewModel.currentCharacter.owner
) {

    LazyColumn (modifier = modifier, contentPadding = PaddingValues(all = 5.dp)) {
        items(spellList) {
                spell->
            SpellCard(spell = spell, modifier = modifier, isEditable = isEditable, onClick = {
                appViewModel.addSpell(additionMode = additionMode, spell = spell, characterSheet = viewModel.currentCharacter.characterSheet)
            },
                updateFunction = updateFunction,
                additionMode = additionMode
            )
            Spacer(modifier = Modifier.padding(all = 3.dp))

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
        })
    }

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
            coroutineScope.launch {
                state.scrollToItem(index = state.layoutInfo.totalItemsCount) }
//TODO: Add logic to not scroll to bottom if the user has scrolled up to view previous messages. Getting pulled down all the time while trying to view previous messages is fucking annoying

    var text by remember {
        mutableStateOf("")
    }

    var openRollInfoDialog by remember {
        mutableStateOf(false)
    }

    TextField(
        value = text,
        onValueChange = { text = it },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
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
        leadingIcon = {
                      IconButton(onClick = { openRollInfoDialog = true }) {
                          Icon(imageVector = Icons.Outlined.Info, contentDescription = "Rolling information")
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

        if (openRollInfoDialog){
            RollInfoDialog(onDismissRequest = {openRollInfoDialog = false})
        }
}
}



@Composable
fun CampaignCharacterOverview(campaign: Campaign, firebaseObject: FirebaseObject, viewModel: CampaignViewModel, navController: NavHostController){

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ){
        LazyColumn(modifier = Modifier.fillMaxHeight(0.80f), contentPadding = PaddingValues(10.dp)) {
            items(campaign.characterList) { character ->
                OnlineCharacterSheetCard(onlineCharacterSheetHolder = character, onClick = {
                    viewModel.currentCharacter = it
                    navController.navigate(CampaignViewScreens.CHARACTER.name)
                }, campaign = campaign, firebaseObject = firebaseObject)
            }
            //TODO: Add dm sheets
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 10.dp, end = 10.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom) {
            androidx.compose.material3.IconButton(
                onClick = {
                    campaign.characterList = campaign.characterList.plus(OnlineCharacterSheetHolder(owner = firebaseObject.currentUser!!.uid))
                    updateCampaign(campaign, firebaseObject)
                },
                modifier = Modifier.size(size = 75.dp)

            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Filled.Add, contentDescription = "Create New Sheet",
                    modifier = Modifier
                        .size(size = 50.dp)
                        .border(
                            width = 1.dp,
                            color = Color.DarkGray,
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                        .background(
                            color = primaryContainerLight,
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                )

            }
        }

    }

}

@Composable
fun CampaignSettings(campaign: Campaign, firebaseObject: FirebaseObject, navBackToCampaigns: () -> Unit, viewModel: CampaignViewModel){

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 20.dp)
        .verticalScroll(state = rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {

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

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            var openLeaveCampaignDialogue by remember {
                mutableStateOf(false)
            }
            var leaveError by remember {
                mutableStateOf(false)
            }

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                TextButton(
                    onClick = {
                        if (firebaseObject.currentUser!!.uid != campaign.gameMaster)
                            openLeaveCampaignDialogue = true
                        else
                            leaveError = true
                    },

                     modifier = Modifier
                         .fillMaxWidth()
                         .background(
                             color = tertiaryContainerLight,
                             shape = RoundedCornerShape(8.dp)
                         )
                         .border(
                             width = 2.dp,
                             color = Color.Black,
                             shape = RoundedCornerShape(8.dp)
                         ), shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Leave Campaign", color = Color.Black)
                }
                if(leaveError){
                    Text(text = "Error: Cannot leave the campaign while DM. Transfer DM ownership or delete campaign instead.", color = Color.Black, textAlign = TextAlign.Center)
                }
            }

            if(openLeaveCampaignDialogue){
                LeaveCampaignDialog(
                    member = getMemberProfile(campaign, firebaseObject.currentUser!!.uid),
                    campaign = campaign,
                    onDismissRequest = { openLeaveCampaignDialogue = false },
                    firebaseObject = firebaseObject,
                    navBackToCampaigns = navBackToCampaigns
                )
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

                var selectedUser:CampaignMember? by remember {
                    mutableStateOf(null)
                }

                Text(text = "Transfer DM status to... ", modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(60.dp)
                    .padding(20.dp))

                IconButton(onClick = { expanded = true }, modifier = Modifier
                    .size(size = 20.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Change DM to...", modifier = Modifier.size(20.dp))
                }

                Box (contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth()) {
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(color = primaryContainerLight)
                    ) {
                        for (campaignMember in campaign.memberProfiles) {
                            if (firebaseObject.currentUser!!.uid != campaignMember.userID && campaign.membersIDs.contains(campaignMember.userID)) {
                                androidx.compose.material3.DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = if (campaignMember.nickname != null) campaignMember.nickname!! else campaignMember.userID,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    },
                                    onClick = {
                                        selectedUser = campaignMember
                                        openConfirmationDialog = true
                                        expanded = false
                                    })
                            }
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

            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                var openKickConfirmationDialog by remember {
                    mutableStateOf(false)
                }

                var expanded by remember {
                    mutableStateOf(false)
                }

                var selectedUser:CampaignMember? by remember {
                    mutableStateOf(null)
                }

                Text(text = "Kick User...", modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(60.dp)
                    .padding(20.dp))

                IconButton(onClick = { expanded = true }, modifier = Modifier
                    .size(size = 20.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Kick Member", modifier = Modifier.size(20.dp))
                }

                Box (contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth()) {
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.background(color = primaryContainerLight)) {
                        for (campaignMember in campaign.memberProfiles) {
                            if (firebaseObject.currentUser!!.uid != campaignMember.userID && campaign.membersIDs.contains(campaignMember.userID)) {
                                androidx.compose.material3.DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = if (campaignMember.nickname != null) campaignMember.nickname!! else campaignMember.userID,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    },
                                    onClick = {
                                        selectedUser = campaignMember
                                        openKickConfirmationDialog = true
                                        expanded = false
                                    },
                                    colors = androidx.compose.material3.MenuDefaults.itemColors(textColor = Color.Black))
                            }
                        }
                    }
                }

                if(openKickConfirmationDialog){
                    KickConfirmationDialogue(
                        member = selectedUser,
                        campaign = campaign,
                        onDismissRequest = { openKickConfirmationDialog = false },
                        firebaseObject = firebaseObject
                    )
                }
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                var openDeleteCampaignDialogue by remember {
                    mutableStateOf(false)
                }


                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    TextButton(
                        onClick = {
                            if (firebaseObject.currentUser!!.uid == campaign.gameMaster)
                                openDeleteCampaignDialogue = true
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = tertiaryContainerLight,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(8.dp)
                            ), shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Delete Campaign", color = Color.Black)
                    }
                }

                if(openDeleteCampaignDialogue){
                    DeleteCampaignDialog(
                        campaign = campaign,
                        onDismissRequest = { openDeleteCampaignDialogue = false },
                        firebaseObject = firebaseObject,
                        navBackToCampaigns = navBackToCampaigns,
                        viewModel = viewModel
                    )
                }

            }

        }

    }

}

@Composable
fun DeleteCampaignDialog(campaign: Campaign, onDismissRequest: () -> Unit, firebaseObject: FirebaseObject, navBackToCampaigns: () -> Unit, viewModel: CampaignViewModel){
        Dialog(onDismissRequest = onDismissRequest) {
            Card (modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = primaryLight),
                shape = RoundedCornerShape(16.dp)) {
                Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Are you sure you'd like to delete this campaign?",
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
                            Text(text = "Cancel", color = Color.Black)
                        }

                        TextButton(onClick = {
                            if (firebaseObject.currentUser!!.uid == campaign.gameMaster) {
                                campaign.membersIDs.forEach { member ->
                                    firebaseObject.removeUserfromCampaign(
                                        campaignCode = campaign.ID,
                                        userID = member
                                    )
                                }
                                viewModel.isDeleted = true
                                campaign.isDeleted = true
                                navBackToCampaigns()
                                firebaseObject.deleteCampaign(campaign.ID)
                                onDismissRequest()
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
                            Text(text = "Confirm", color = Color.Red)
                        }
                    }


                }
            }
        }
    }


@Composable
fun LeaveCampaignDialog(member: CampaignMember?, campaign: Campaign, onDismissRequest: () -> Unit, firebaseObject: FirebaseObject, navBackToCampaigns: () -> Unit){
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
                        text = "Are you sure you'd like to leave this campaign?",
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
                            firebaseObject.removeUserfromCampaign(member.userID, campaignCode = campaign.ID)
                            campaign.membersIDs = campaign.membersIDs.minus(member.userID)
                            updateCampaign(campaign = campaign, firebaseObject = firebaseObject)
                            navBackToCampaigns()
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

@Composable
fun OnlineCharacterSheetCard(onlineCharacterSheetHolder: OnlineCharacterSheetHolder, onClick: (OnlineCharacterSheetHolder) -> Unit, firebaseObject: FirebaseObject, campaign: Campaign){
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = primaryContainerLight)

    ) {
        var characterSheet = onlineCharacterSheetHolder.characterSheet
        Row(verticalAlignment = Alignment.CenterVertically){
            Column(modifier = Modifier.padding(top = 2.dp)) {

                //Character name
                Text(
                    text = characterSheet.charName,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    fontSize = 25.sp
                )

                //Character Class and their levels. Supports multiclassing
                var levelText: String = ""
                var count = 0
                for (pair in characterSheet.characterClass){
                    levelText = levelText + "Level ${pair.classLevel} ${pair.characterClass.toString().
                    lowercase().
                    capitalize()}"

                    count++

                    if (count < characterSheet.characterClass.size){
                        levelText = levelText +", "
                    }
                }
                Text(
                    text = levelText,
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 0.dp, bottom = 5.dp)
                        .fillMaxWidth(.75f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Column(verticalArrangement = Arrangement.SpaceBetween) {

                var openDeletionDialog by remember {
                    mutableStateOf(false)
                }
                CharacterItemButton(onClick = { onClick(onlineCharacterSheetHolder) })

                if(onlineCharacterSheetHolder.owner == firebaseObject.currentUser!!.uid){
                    androidx.compose.material3.IconButton(
                        onClick = { openDeletionDialog = true },
                        modifier = Modifier
                            .padding(bottom = 10.dp, start = 15.dp)
                            .size(size = 20.dp)

                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete Character"
                        )
                    }
                }


                if(openDeletionDialog){
                    Dialog(onDismissRequest = { openDeletionDialog = false }) {
                        androidx.compose.material.Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .padding(16.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Delete Character \"${characterSheet.charName}\"?",
                                    modifier = Modifier.padding(vertical = 10.dp)
                                )
                                androidx.compose.material.Divider(
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                                Row {
                                    androidx.compose.material.TextButton(
                                        onClick = {
                                            //TODO deletion
                                            campaign.characterList = campaign.characterList.minus(onlineCharacterSheetHolder)
                                            updateCampaign(campaign, firebaseObject)
                                            openDeletionDialog = false
                                        },
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp)
                                            .background(
                                                color = removeColor,
                                                shape = RoundedCornerShape(size = 5.dp)
                                            )
                                            .border(
                                                width = 1.dp,
                                                color = Color.Black,
                                                shape = RoundedCornerShape(size = 5.dp)
                                            )

                                    ) {
                                        Text(text = "Delete")
                                    }
                                    androidx.compose.material.TextButton(
                                        onClick = {
                                            openDeletionDialog = false

                                        },
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp)
                                            .background(
                                                color = primaryContainerLight,
                                                shape = RoundedCornerShape(size = 5.dp)
                                            )
                                            .border(
                                                width = 1.dp,
                                                color = Color.Black,
                                                shape = RoundedCornerShape(size = 5.dp)
                                            )
                                    ) {
                                        Text(text = "Cancel")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RollInfoDialog(onDismissRequest: () -> Unit){
    Dialog(onDismissRequest = onDismissRequest) {
        Card (modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = primaryLight, contentColor = Color.Black),
            shape = RoundedCornerShape(16.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
                Text(text = "How to do rolls:", textAlign = TextAlign.Center, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "Rolls within chat can be performed by using the /roll command.", textAlign = TextAlign.Center)
                Text(text = "To use the command, simply type /roll or one of its variants, followed by the roll(s).", textAlign = TextAlign.Center)
                Text(text = "The roll command can be called by using a period(.) or either slash character(\\,/), followed by the letter r or the word 'roll'", textAlign = TextAlign.Center)
                Text(text = "Rolls take the form of the common syntax [digit]d[digit], such as '4d6'.", textAlign = TextAlign.Center)
                Text(text = "Multiple sets of dice can be rolled within a single command, such as  /roll 2d4 3d10", textAlign = TextAlign.Center)
                Text(text = "Rolls can also be added or subtracted from each other: /roll d6 - 2d4", textAlign = TextAlign.Center)
                Text(text = "Note that rolls cannot be combined with regular chat messages.", textAlign = TextAlign.Center)
            }
        }
    }
}

public fun SendMessage(campaign: Campaign, firebaseObject: FirebaseObject, message: Message){
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
    everySecond()
    refreshChat(everySecond)
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
                            Icon(painter = painterResource(id = R.drawable.role_playing), contentDescription = "Roll",
                                Modifier
                                    .size(18.dp)
                                    .offset(y = 3.dp))
                        }
                        Text(
                            text = (if (user.nickname != null) user.nickname else user.userID)!!,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = if(message.userID == firebaseObject.currentUser!!.uid) TextAlign.End else TextAlign.Start
                            //TODO: Get Username text alignment correct. Adding in a row to the column in order to put a roll icon for messages marked as rolls caused errors with previous alignment modifiers
                        )

                        if(message.userID != firebaseObject.currentUser!!.uid && message.isRoll){
                            Icon(painter = painterResource(id = R.drawable.role_playing), contentDescription = "Roll",
                                Modifier
                                    .size(18.dp)
                                    .offset(y = 3.dp))
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
    }
    return null
}

public fun updateCampaign(campaign: Campaign, firebaseObject: FirebaseObject){
    if(campaign != null && !campaign.isDeleted) {
        firebaseObject.firestoreDB.collection("campaigns")
            .document(firebaseObject.currentCampaign!!).set(campaign)
    }
}

@Composable
fun KickConfirmationDialogue(member: CampaignMember?, campaign: Campaign, onDismissRequest: () -> Unit, firebaseObject: FirebaseObject){
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
                        text = "Are you sure you'd like to kick " + if (member!!.nickname != null) "${member!!.nickname}?" else "${member.userID}?",
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
                            firebaseObject.removeUserfromCampaign(member.userID, campaignCode = campaign.ID)
                            campaign.membersIDs = campaign.membersIDs.minus(member.userID)
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
                        text = "Are you sure you'd like to transfer ownership of the campaign to " + if (member!!.nickname != null) "${member!!.nickname}?" else "${member.userID}?",
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