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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import com.example.dragontome.data.CharacterSheet
import com.example.dragontome.state.AppViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dragontome.DragonTomeLocalScreens
import com.example.dragontome.R
import com.example.dragontome.data.CharacterPreviewProvider
import com.example.dragontome.data.CharacterSheetHolder
import com.example.dragontome.ui.theme.addColor
import com.example.dragontome.ui.theme.primaryContainerLight
import com.example.dragontome.ui.theme.primaryLight
import com.example.dragontome.ui.theme.removeColor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

enum class CharacterSheetScreens() {
    SheetList,
    Character
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CharacterScreen(appViewModel: AppViewModel, navController: NavHostController = rememberNavController()){
    Log.d("debug", "Should have gotten here as well. List: ${appViewModel.sheetList.toString()}")

    var refreshFlag by remember {
        mutableStateOf(false)
    }

    NavHost(
        navController = navController,
        startDestination = CharacterSheetScreens.SheetList.name){
        composable(route = CharacterSheetScreens.SheetList.name){
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.grunge_background),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.matchParentSize()
                )

                CharacterList(characterSheetList = appViewModel.sheetList,
                    appViewModel = appViewModel,
                    navController = navController,
                    refreshFlag = refreshFlag,
                    refreshContent = {
                        refreshFlag = !refreshFlag
                        Log.d("debug", "Should have refreshed")
                    }
                )
            }
        }
        composable(route = CharacterSheetScreens.Character.name){
            if(appViewModel.currentCharacter != null){
                CharacterSheetScreen(characterSheetHolder = appViewModel.currentCharacter!!, appViewModel = appViewModel)
            }
            else{
                Log.d("debug", "Current Character is null.")
            }

        }
    }


}

@Composable
fun CharacterList(
    characterSheetList: List<CharacterSheetHolder>,
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel,
    navController: NavHostController,
    refreshFlag: Boolean,
    refreshContent: () -> Unit = {}
    ){
    LazyColumn (modifier = modifier.padding(bottom = 85.dp), contentPadding = PaddingValues(all = 5.dp)) {
        items(characterSheetList) {
                characterSheet ->
            CharacterSheetCard(
                characterSheetHolder = characterSheet,
                modifier = modifier,
                appViewModel = appViewModel,
                navController = navController,
                refreshFlag = refreshFlag,
                refreshContent = refreshContent
            )
            Spacer(modifier = Modifier.padding(all = 3.dp))

        }
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(bottom = 10.dp, end = 10.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom) {
        IconButton(
            onClick = {
                appViewModel.AddToDatabase(CharacterSheet(), refreshContent = refreshContent)
                      },
            modifier = Modifier.size(size = 75.dp)

        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Create New Sheet",
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
                    ))

        }
    }
}

@Composable
fun CharacterSheetCard(
    @PreviewParameter(CharacterPreviewProvider::class) characterSheetHolder: CharacterSheetHolder,
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel,
    navController: NavHostController,
    refreshFlag: Boolean,
    refreshContent: () -> Unit = {}
) {
    androidx.compose.material3.Card(
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = primaryContainerLight)

    ) {
        var characterSheet = characterSheetHolder.characterSheet
        Row(verticalAlignment = Alignment.CenterVertically){
            Column(modifier = modifier.padding(top = 2.dp)) {

                    //Character name
                    Text(
                        text = characterSheet.charName,
                        modifier.padding(start = 10.dp, end = 10.dp),
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
                    modifier
                        .padding(start = 15.dp, end = 15.dp, top = 0.dp, bottom = 5.dp)
                        .fillMaxWidth(.75f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Column(verticalArrangement = Arrangement.SpaceBetween) {

                var openDeletionDialog by remember {
                    mutableStateOf(false)
                }
                CharacterItemButton(onClick = {
                    appViewModel.currentCharacter = characterSheetHolder
                    navController.navigate(CharacterSheetScreens.Character.name)
                    refreshContent()
                })

                IconButton(
                    onClick = { openDeletionDialog = true },
                    modifier = Modifier
                        .padding(bottom = 10.dp, start = 15.dp)
                        .size(size = 20.dp)

                ) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Character")
                }

                if(openDeletionDialog){
                    Dialog(onDismissRequest = { openDeletionDialog = false }) {
                        Card (modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(16.dp)
                        ){
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Text(text = "Delete Character \"${characterSheet.charName}\"?", modifier = Modifier.padding(vertical = 10.dp))
                                Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                                Row {
                                    TextButton(onClick = {
                                        appViewModel.RemoveFromDatabase(characterSheetHolder = characterSheetHolder, refreshContent = refreshContent)
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
                                    TextButton(onClick = {
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
private fun CharacterItemButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = "Go to Character",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}
