package com.moke.dragontome

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moke.dragontome.data.AdvertView
import com.moke.dragontome.screens.CampaignScreen
import com.moke.dragontome.screens.CharacterScreen
import com.moke.dragontome.screens.CreditsScreen
import com.moke.dragontome.screens.NotesScreen
import com.moke.dragontome.screens.SpellsScreen
import com.moke.dragontome.state.AppViewModel
import com.moke.dragontome.ui.theme.primaryContainerLight
import com.moke.dragontome.ui.theme.primaryLight

enum class DragonTomeLocalScreens() {
    Notes,
    Characters,
    Spells,
    Credits
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
                label = { Text(text = stringResource(R.string.notes))},
                selected = selectedItem == 1,
                onClick = {
                    selectedItem = 1
                    //Navigate to the Notes Screen on click
                    navController.navigate(DragonTomeLocalScreens.Notes.name)
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

//ca-app-pub-7188837080297377/3037058697


    Scaffold(bottomBar = {
        Column {
            LocalNavBar(navController = navController)
            AdvertView()
        } }, topBar = {
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(color = primaryLight),
            horizontalArrangement = Arrangement.Start) {
            IconButton(onClick = { navController.navigate(DragonTomeLocalScreens.Credits.name) }, modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .background(color = primaryContainerLight, shape = RoundedCornerShape(8.dp))
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))

            ) {
                Icon(imageVector = Icons.Rounded.Person, contentDescription = "")
            }
        }
    }) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = DragonTomeLocalScreens.Characters.name,
            modifier = Modifier.padding(innerPadding)
        ) {


            composable(route = DragonTomeLocalScreens.Characters.name) {
                CharacterScreen(appViewModel)
            }

            composable(route = DragonTomeLocalScreens.Spells.name) {
                SpellsScreen(context = context, appViewModel)
            }
            composable(route = DragonTomeLocalScreens.Notes.name) {
                NotesScreen(appViewModel = appViewModel)
            }
            composable(route = DragonTomeLocalScreens.Credits.name){
                CreditsScreen()
            }
        }
    }
}