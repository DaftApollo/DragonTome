package com.moke.dragontome.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.moke.dragontome.data.FirebaseObject
import com.moke.dragontome.state.AppViewModel
import com.moke.dragontome.state.CampaignItemViewModel
import com.moke.dragontome.state.CampaignListViewModel
import com.moke.dragontome.ui.theme.primaryContainerLight

enum class CampaignScreens{
    SIGNUP,
    SIGNIN,
    CAMPAIGNS,
    CAMPAIGN
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignScreen(appViewModel: AppViewModel, context: Context){
    var navController: NavHostController = rememberNavController()
    val firebaseObject = FirebaseObject

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.grunge_background),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        if (appViewModel.isOnline(context)) {
            NavHost(
                navController = navController,
                startDestination = if(firebaseObject.currentUser != null) CampaignScreens.CAMPAIGNS.name else  CampaignScreens.SIGNIN.name
            )
            {
                composable(route = CampaignScreens.SIGNUP.name) {
                    SignUpScreen(
                        firebaseObject = firebaseObject,
                        swapScreen = {navController.navigate(CampaignScreens.SIGNIN.name)},
                        onSuccess = {navController.navigate(CampaignScreens.CAMPAIGNS.name)}
                    )
                }
                composable(route = CampaignScreens.SIGNIN.name) {
                    SignInScreen(
                        firebaseObject = firebaseObject,
                        swapScreen = {navController.navigate(CampaignScreens.SIGNUP.name)},
                        onSuccess = {navController.navigate(CampaignScreens.CAMPAIGNS.name)}
                    )
                }
                composable(route = CampaignScreens.CAMPAIGNS.name) {
                    CampaignScreen(firebaseObject = firebaseObject, onSuccess = {navController.navigate(CampaignScreens.CAMPAIGN.name)}, swapToSignOut = {navController.navigate(CampaignScreens.SIGNIN.name)})
                }
                composable(route = CampaignScreens.CAMPAIGN.name){
                    CampaignViewScreen(firebaseObject = firebaseObject, appviewModel = appViewModel, navBackToCampaigns = {
                        navController.navigate(route = CampaignScreens.CAMPAIGNS.name) {popUpTo(0)}
                    })
                }
            }
        } else {

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No internet connection detected. Online Campaigns require an internet connection. Please check your settings and try again.",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun SignUpScreen(
    firebaseObject: FirebaseObject,
    swapScreen: () -> Unit,
    onSuccess: () -> Unit
){
    Column(
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {

        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        var confirmedPassword by remember {
            mutableStateOf("")
        }
        var errorType by remember {
            mutableStateOf(ErrorType.NO_ERROR)
        }

        Text(text = "DragonTome", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = "Sign Up", fontSize = 16.sp)
        Spacer(modifier = Modifier.padding(5.dp))
        
        TextButton(onClick = swapScreen) {
            Text(text = "Already have an account? Log in here", fontStyle = FontStyle.Italic, color = Color.Black )
        }

        Spacer(modifier = Modifier.padding(20.dp))

        TextField(value = email, onValueChange = {
            email = it
        },
            colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.LightGray, focusedLabelColor = Color.Black),
            singleLine = true,
            label = { Text(text = "Email Address")},
            placeholder = {
                Text(text = "email")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        if(errorType == ErrorType.EMAIL_INVALID){
            Text(
                text = "Email format invalid. Please try again",
                modifier = Modifier
                    .padding(top = 10.dp),
                color = Color.Black
            )
        }
        
        Spacer(modifier = Modifier.padding(10.dp))
        TextField(value = password, onValueChange = {
            password = it
        },
            colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.LightGray, focusedLabelColor = Color.Black),
            singleLine = true,
            label = {
                Text(text = "Password")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
            visualTransformation = PasswordVisualTransformation()
        )
        if(errorType == ErrorType.PASSWORD_INVALID){
            Text(
                text = "Password format invalid. Passwords must be at least 6 letters long, contain a digit, and an uppercase letter. Please try again",
                modifier = Modifier
                    .padding(top = 10.dp, start = 30.dp, end = 30.dp),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))
        TextField(value = confirmedPassword, onValueChange = {
            confirmedPassword = it
        },
            colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.LightGray, focusedLabelColor = Color.Black),
            singleLine = true,
            label = {
                Text(text = "Re-enter password:")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            visualTransformation = PasswordVisualTransformation())
        if(errorType == ErrorType.PASSWORD_MISMATCH){
            Text(
                text = "Passwords do not match. Please try again",
                modifier = Modifier
                    .padding(top = 10.dp, start = 30.dp, end = 30.dp),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.padding(20.dp))

        TextButton(onClick = {
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if(password.length >= 6 && password.count(Char::isDigit) > 0 && password.count(Char::isUpperCase) > 0){
                    if(password.equals(confirmedPassword)){
                        errorType = ErrorType.NO_ERROR
                        firebaseObject.registerUser(email, password, onFailure = {errorType = ErrorType.ACCOUNT_ALREADY_EXISTS}, onSuccess = onSuccess)
                    }
                    else errorType = ErrorType.PASSWORD_MISMATCH
                }
                else errorType = ErrorType.PASSWORD_INVALID
            }
            else errorType = ErrorType.EMAIL_INVALID

        },
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(size = 8.dp))
                .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(size = 8.dp))

        ) {
            Text(text = "Sign up", color = Color.Black)
        }
        if(errorType == ErrorType.ACCOUNT_ALREADY_EXISTS){
            Text(
                text = "Failure to create account. Account likely already exists under this email",
                modifier = Modifier
                    .padding(top = 10.dp, start = 50.dp, end = 50.dp),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SignInScreen(
    firebaseObject: FirebaseObject,
    swapScreen: () -> Unit,
    onSuccess: () -> Unit
){
    Column(
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        var errorType by remember {
            mutableStateOf(ErrorType.NO_ERROR)
        }

        Text(text = "DragonTome", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = "Sign In", fontSize = 16.sp)
        Spacer(modifier = Modifier.padding(5.dp))

        TextButton(onClick = swapScreen) {
            Text(text = "Don't have an account? Sign up here", fontStyle = FontStyle.Italic, color = Color.Black )
        }

        Spacer(modifier = Modifier.padding(20.dp))

        TextField(value = email, onValueChange = {
            email = it
        },
            colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.LightGray, focusedLabelColor = Color.Black),
            singleLine = true,
            label = { Text(text = "Email Address")},
            placeholder = {
                Text(text = "email")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        if(errorType == ErrorType.EMAIL_INVALID){
            Text(
                text = "Email format invalid. Please try again",
                modifier = Modifier
                    .padding(top = 10.dp),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))
        TextField(value = password, onValueChange = {
            password = it
        },
            colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.LightGray, focusedLabelColor = Color.Black),
            singleLine = true,
            label = {
                Text(text = "Password")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if(password.length >= 6 && password.count(Char::isDigit) > 0 && password.count(Char::isUpperCase) > 0){
                        errorType = ErrorType.NO_ERROR
                        firebaseObject.signInUser(email, password, onFailure = {errorType = ErrorType.ACCOUNT_DOES_NOT_EXIST}, onSuccess = onSuccess)
                    }
                    else errorType = ErrorType.PASSWORD_INVALID
                }
                else errorType = ErrorType.EMAIL_INVALID

            }),
            visualTransformation = PasswordVisualTransformation()
        )
        if(errorType == ErrorType.PASSWORD_INVALID){
            Text(
                text = "Password format invalid. Passwords must be at least 6 letters long, contain a digit, and an uppercase letter. Please try again",
                modifier = Modifier
                    .padding(top = 10.dp, start = 30.dp, end = 30.dp),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.padding(20.dp))

        TextButton(onClick = {
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if(password.length >= 6 && password.count(Char::isDigit) > 0 && password.count(Char::isUpperCase) > 0){
                    errorType = ErrorType.NO_ERROR
                    firebaseObject.signInUser(email, password, onFailure = {errorType = ErrorType.ACCOUNT_DOES_NOT_EXIST}, onSuccess = onSuccess)
                }
                else errorType = ErrorType.PASSWORD_INVALID
            }
            else errorType = ErrorType.EMAIL_INVALID

        },
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(size = 8.dp))
                .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(size = 8.dp))

        ) {
            Text(text = "Sign in", color = Color.Black)
        }
        if(errorType == ErrorType.ACCOUNT_DOES_NOT_EXIST){
            Text(
                text = "Failure to sign in. Account does not exist",
                modifier = Modifier
                    .padding(top = 10.dp, start = 50.dp, end = 50.dp),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CampaignScreen(
    firebaseObject: FirebaseObject,
    onSuccess: () -> Unit,
    swapToSignOut: () -> Unit,
    viewModel: CampaignListViewModel = CampaignListViewModel(firebaseObject)
) {
    if(firebaseObject.currentUser != null){

        firebaseObject.getCampaigns()

        val campaigns by viewModel.campaignCodeList.collectAsStateWithLifecycle()
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, top = 10.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {

                TextButton(
                    onClick = swapToSignOut, modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                ) {
                    Text(text = "Back to Sign In", color = Color.Black)
                }
            }

            var refreshflag by remember {
                mutableStateOf(false)
            }
            Text(text = "Campaigns", textAlign = TextAlign.Center, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
            LazyColumn (contentPadding = PaddingValues(all = 5.dp)) {
                items(campaigns){campaignCode ->
                    CampaignItem(campaignCode = campaignCode, firebaseObject = firebaseObject, onSuccess = onSuccess, viewModel = CampaignItemViewModel(firebaseObject, campaignCode))
                }
                item {
                    CreateCampaignCard(firebaseObject = firebaseObject, onSuccess = onSuccess)
                 }
            }
        }
    }
    else{
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = "Error. Current user read as null, please try signing in again.", textAlign = TextAlign.Center, fontSize = 20.sp)
        }
    }
}




@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CampaignItem(
    campaignCode:String,
    firebaseObject: FirebaseObject,
    onSuccess: () -> Unit,
    viewModel: CampaignItemViewModel
) {

    val campaign by viewModel.campaign.collectAsStateWithLifecycle()

    if(campaign != null) {
        Card (modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp)),
            colors = CardDefaults.cardColors(containerColor = primaryContainerLight)
        ) {
            Row (modifier = Modifier
                .fillMaxWidth()
                .sizeIn(minHeight = 100.dp)
                .padding(start = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (campaign!!.name != null || campaign!!.name != "") {
                    Text( text = "Campaign: " + campaign!!.name, maxLines = 2, fontSize = 20.sp, overflow = TextOverflow.Ellipsis, modifier = Modifier.fillMaxWidth(0.75f))
                } else {
                    Text(text = "No name set yet. Campaign ID: " + campaignCode, maxLines = 1, fontSize = 20.sp)
                }

                IconButton(onClick = {
                    firebaseObject.currentCampaign = campaignCode
                    onSuccess()
                }) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Enter campaign")
                }


            }
        }
    }

}

@Composable
fun CreateCampaignCard(firebaseObject: FirebaseObject, onSuccess: () -> Unit){
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = primaryContainerLight)
    ) {

        var openCampaignCreationDialogue by remember {
            mutableStateOf(false)
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 100.dp)
            .padding(start = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text( text = "Create new campaign", maxLines = 1, fontSize = 20.sp, fontStyle = FontStyle.Italic)
            IconButton(onClick = { openCampaignCreationDialogue = true}) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Create campaign")
            }


        }
        if(openCampaignCreationDialogue){

            var campaignName by remember {
                mutableStateOf("")
            }

            Dialog(onDismissRequest = { openCampaignCreationDialogue = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp)
                        .verticalScroll(state = rememberScrollState()),
                    colors = CardDefaults.cardColors(containerColor = primaryContainerLight),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 20.dp)) {
                        Text(text = "Create a new campaign?", textAlign = TextAlign.Center, modifier = Modifier.padding(bottom = 10.dp))
                        TextField(
                            value = campaignName,
                            onValueChange = {campaignName = it},
                            label = { Text(text = "Campaign Name") },
                            placeholder = { Text(text = "Campaign Name") },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.LightGray,
                                unfocusedContainerColor = Color.White,
                                focusedLabelColor = Color.Black
                            )
                        )
                        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                            TextButton(onClick = { openCampaignCreationDialogue = false }) {
                                Text(text = "Cancel", color = Color.Black)
                            }
                            TextButton(onClick = {
                                firebaseObject.createCampaign(campaignName)
                                openCampaignCreationDialogue = false
                            }) {
                                Text(text = "Create", color = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class ErrorType{
    EMAIL_INVALID,
    PASSWORD_INVALID,
    PASSWORD_MISMATCH,
    ACCOUNT_ALREADY_EXISTS,
    ACCOUNT_DOES_NOT_EXIST,
    NO_ERROR
}