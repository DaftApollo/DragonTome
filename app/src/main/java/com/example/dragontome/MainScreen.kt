package com.example.dragontome

import android.content.Context
import android.content.res.Resources
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dragontome.screens.CampaignScreen
import com.example.dragontome.screens.CharacterScreen
import com.example.dragontome.screens.SpellsScreen
import com.example.dragontome.state.AppViewModel
import com.example.dragontome.ui.theme.primaryLight
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

enum class DragonTomeLocalScreens() {
    Campaigns,
    Characters,
    Spells
}


//Navbar Composable. Creates navigation UI
@Composable
fun LocalNavBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    BottomNavigation(
        modifier = modifier.fillMaxWidth(), backgroundColor = primaryLight
        ) {
        //TODO: Add proper icons for navigation
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Rounded.Menu, contentDescription = null)},
                label = { Text(text = stringResource(R.string.campaigns))},
                selected = selectedItem == 1,
                onClick = {
                    selectedItem = 1
                    //Navigate to the Campaigns Screen on click
                    navController.navigate(DragonTomeLocalScreens.Campaigns.name)
                }
            )
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Rounded.AccountCircle, contentDescription = null)},
                label = { Text(text = stringResource(R.string.characters))},
                selected = selectedItem == 2,
                onClick = {
                    selectedItem = 2
                    //Navigate to the Characters Screen on click
                    navController.navigate(DragonTomeLocalScreens.Characters.name)
                }
            )
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Rounded.Star, contentDescription = null)},
                label = { Text(text = stringResource(R.string.spells))},
                selected = selectedItem == 3,
                onClick = {
                    selectedItem = 3
                    //Navigate to the Spells Screen on click
                    navController.navigate(DragonTomeLocalScreens.Spells.name)
                }
            )


        }
    }


@Composable
fun DragonTomeApp(
    navController: NavHostController = rememberNavController(),
    context: Context,
    appViewModel: AppViewModel
) {




    Scaffold(bottomBar = { LocalNavBar(navController = navController) }) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = DragonTomeLocalScreens.Characters.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = DragonTomeLocalScreens.Campaigns.name) {
                CampaignScreen(appViewModel, context = context)
            }

            composable(route = DragonTomeLocalScreens.Characters.name) {
                CharacterScreen(appViewModel)
            }

            composable(route = DragonTomeLocalScreens.Spells.name) {
                SpellsScreen(context = context, appViewModel)
            }
        }
    }
}