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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moke.dragontome.data.CharacterSheet
import com.moke.dragontome.state.AppViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moke.dragontome.R
import com.moke.dragontome.data.CharacterPreviewProvider
import com.moke.dragontome.data.CharacterSheetHolder
import com.moke.dragontome.ui.theme.primaryContainerLight
import com.moke.dragontome.ui.theme.removeColor

enum class CharacterSheetScreens() {
    SheetList,
    Character
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CharacterScreen(appViewModel: AppViewModel, navController: NavHostController = rememberNavController()){

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
                    }
                )
            }
        }
        composable(route = CharacterSheetScreens.Character.name){
            if(appViewModel.currentCharacter != null){
                CharacterSheetScreen(characterSheetHolder = appViewModel.currentCharacter!!, appViewModel = appViewModel)
            }
            else{
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
    Column(Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, top = 10.dp)) {
        Text(text = "Characters", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        androidx.compose.material3.Divider(
            thickness = 1.dp,
            modifier = Modifier.padding(top = 5.dp),
            color = Color.Black
        )
    }
    LazyColumn (modifier = modifier.padding(bottom = 85.dp, top = 50.dp), contentPadding = PaddingValues(all = 5.dp)) {
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
public fun CharacterItemButton(
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
