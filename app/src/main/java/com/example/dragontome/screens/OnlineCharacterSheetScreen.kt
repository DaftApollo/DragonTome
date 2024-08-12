package com.example.dragontome.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dragontome.R
import com.example.dragontome.data.Campaign
import com.example.dragontome.data.CharacterSheet
import com.example.dragontome.data.CharacterSheetHolder
import com.example.dragontome.data.CharacterSheetInitializer
import com.example.dragontome.data.FirebaseObject
import com.example.dragontome.data.IntWrapper
import com.example.dragontome.data.OnlineCharacterSheetHolder
import com.example.dragontome.data.Spell
import com.example.dragontome.data.StatList
import com.example.dragontome.state.AppViewModel
import com.example.dragontome.state.CampaignViewModel
import com.example.dragontome.ui.theme.onPrimaryContainerLight
import com.example.dragontome.ui.theme.primaryContainerLight

@Composable
fun OnlineCharacterSheetScreen(
    onlineCharacterSheetHolder: OnlineCharacterSheetHolder,
    navController: NavHostController = rememberNavController(),
    viewModel: CampaignViewModel,
    firebaseObject:FirebaseObject,
    campaign: Campaign
){
    var characterSheet = onlineCharacterSheetHolder.characterSheet
    var updateFunction: () -> Unit = { updateCampaign(campaign, firebaseObject)}
    //Refresh flag to get the various composables to update properly. Previous issue caused stats in one
    //composable to not update when a relevant stat in a different composable updated. By passing in
    //the refresh flag to each function, when that flag is changed by a stat modification, it calls
    //recomposition for all the other functions that read the flag.
    var refreshFlag by remember {
        mutableStateOf(false)
    }

    remember {
        mutableStateOf(characterSheet)
    }

    if (characterSheet != null){
        var characterSheetInitializer: CharacterSheetInitializer = CharacterSheetInitializer(characterSheet = characterSheet)

        characterSheetInitializer.initializeCharacterSheet()

        Scaffold (topBar = { CharacterNavBar(navController = navController) }) { innerPadding ->
            var characterClassPopup: Boolean by remember { mutableStateOf(false) }

            NavHost(navController = navController,
                startDestination = characterSheetScreens.Stats.name,
                modifier = Modifier.padding(innerPadding)) {
                composable(route = characterSheetScreens.Stats.name) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(state = rememberScrollState())
                    ) {
                        //Background Image
                        Image(
                            painter = painterResource(id = R.drawable.grunge_background),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.matchParentSize()
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {
                            Row() {
                                //Column block holding the name and class fields
                                Column(
                                    modifier = Modifier
                                        .border(
                                            width = 2.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(size = 2.dp)
                                        )
                                        .background(
                                            color = primaryContainerLight,
                                            shape = RoundedCornerShape(size = 2.dp)
                                        )
                                        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    //Name Textfield
                                    var nameText by remember {
                                        mutableStateOf(characterSheet.charName)
                                    }
                                    TextField(
                                        value = nameText,
                                        onValueChange = {
                                            nameText = it
                                            characterSheet.charName = nameText
                                            updateFunction()
                                            Log.d("debug", "Current value of name: ${characterSheet.charName}")
                                        },
                                        label = {
                                            Text(
                                                text = "Name:",
                                                textAlign = TextAlign.Start,
                                                fontWeight = FontWeight(weight = 750)
                                            )
                                        },
                                        singleLine = true,
                                        textStyle = TextStyle(
                                            fontSize = 20.sp,
                                            textAlign = TextAlign.Center,
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Divider(Modifier.padding(horizontal = 10.dp))

                                    //Class Field

                                    TextButton(
                                        onClick = { characterClassPopup = true },
                                        modifier = Modifier
                                            .background(
                                                color = primaryContainerLight,
                                                shape = RoundedCornerShape(size = 2.dp)
                                            )
                                            .fillMaxWidth()
                                    ) {
                                        var levelText: String = ""
                                        var count = 0
                                        for (pair in characterSheet.characterClass) {
                                            levelText = levelText + "Level ${pair.classLevel} ${
                                                pair.characterClass.toString().lowercase().capitalize()
                                            }"

                                            count++

                                            if (count < characterSheet.characterClass.size) {
                                                levelText = levelText + ", "
                                            }
                                        }
                                        Text(
                                            text = "Class: ${levelText}",
                                            color = onPrimaryContainerLight,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                                if (characterClassPopup) {
                                    ClassPopupWindow(
                                        characterSheet = characterSheet,
                                        onDismissRequest = {
                                            characterClassPopup = false
                                            characterSheetInitializer.refreshProficiencyBonus()
                                        },
                                        updateFunction = updateFunction)
                                }
                            }



                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            BasicInfoWindow(
                                characterSheet = characterSheet,
                                refresherFlag = refreshFlag,
                                updateFunction = updateFunction
                            )

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            PrimaryStats(characterSheet = characterSheet,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                updateFunction = updateFunction
                            )

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            HealthWindow(
                                characterSheet = characterSheet,
                                refresherFlag = refreshFlag,
                                updateFunction = updateFunction
                            )

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            AttributesWindow(
                                characterSheet = characterSheet,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                refresherFlag = refreshFlag,
                                updateFunction = updateFunction)

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            SavingThrowWindow(
                                characterSheet = characterSheet,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                updateFunction = updateFunction)

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            SkillWindow(
                                characterSheet = characterSheet,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                updateFunction = updateFunction)

                        }
                    }
                }

                composable(route = characterSheetScreens.AbilitiesAndInventory.name) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(state = rememberScrollState())
                    ) {
                        //Background Image
                        Image(
                            painter = painterResource(id = R.drawable.grunge_background),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.matchParentSize()
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            StringWindow(
                                characterSheet = characterSheet,
                                stringList = characterSheet.characterFeaturesAndTraits,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                title = "Features and Traits",
                                updateFunction = updateFunction
                            )

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            StringWindow(
                                characterSheet = characterSheet,
                                stringList = characterSheet.proficenciesAndLanguages,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                title = "Proficiencies and Languages",
                                updateFunction = updateFunction
                            )

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            AttacksWindow(
                                characterSheet = characterSheet,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                updateFunction = updateFunction)

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            StringWindow(
                                characterSheet = characterSheet,
                                stringList = characterSheet.equipment,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                title = "Equipment",
                                updateFunction = updateFunction
                            )

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            StringWindow(
                                characterSheet = characterSheet,
                                stringList = characterSheet.treasure,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                title = "Treasure",
                                updateFunction = updateFunction
                            )

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            var openCopperDialog by remember {
                                mutableStateOf(false)
                            }
                            var openSilverDialog by remember {
                                mutableStateOf(false)
                            }
                            var openElectrumDialog by remember {
                                mutableStateOf(false)
                            }
                            var openGoldDialog by remember {
                                mutableStateOf(false)
                            }
                            var openPlatinumDialog by remember {
                                mutableStateOf(false)
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 5.dp), horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TextButton(
                                    onClick = { openCopperDialog = true },
                                    modifier = Modifier
                                        .size(size = 100.dp)
                                        .border(
                                            width = 2.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(size = 5.dp)
                                        )
                                        .background(
                                            color = primaryContainerLight,
                                            shape = RoundedCornerShape(size = 5.dp)
                                        )
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "Copper Pieces:",
                                            minLines = 2,
                                            textAlign = TextAlign.Center,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = characterSheet.copperPieces.value.toString(),
                                            textAlign = TextAlign.Center,
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            modifier = Modifier.padding(top = 5.dp)
                                        )
                                    }
                                }
                                TextButton(
                                    onClick = { openSilverDialog = true },
                                    modifier = Modifier
                                        .size(size = 100.dp)
                                        .border(
                                            width = 2.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(size = 5.dp)
                                        )
                                        .background(
                                            color = primaryContainerLight,
                                            shape = RoundedCornerShape(size = 5.dp)
                                        )
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "Silver Pieces:",
                                            minLines = 2,
                                            textAlign = TextAlign.Center,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = characterSheet.silverPieces.value.toString(),
                                            textAlign = TextAlign.Center,
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            modifier = Modifier.padding(top = 5.dp)
                                        )
                                    }
                                }
                                TextButton(
                                    onClick = { openElectrumDialog = true },
                                    modifier = Modifier
                                        .size(size = 100.dp)
                                        .border(
                                            width = 2.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(size = 5.dp)
                                        )
                                        .background(
                                            color = primaryContainerLight,
                                            shape = RoundedCornerShape(size = 5.dp)
                                        )
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "Electrum Pieces:",
                                            minLines = 2,
                                            textAlign = TextAlign.Center,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = characterSheet.electrumPieces.value.toString(),
                                            textAlign = TextAlign.Center,
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            modifier = Modifier.padding(top = 5.dp)
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 5.dp),
                                horizontalArrangement = Arrangement.Absolute.SpaceEvenly
                            ) {
                                TextButton(
                                    onClick = { openGoldDialog = true },
                                    modifier = Modifier
                                        .size(size = 100.dp)
                                        .border(
                                            width = 2.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(size = 5.dp)
                                        )
                                        .background(
                                            color = primaryContainerLight,
                                            shape = RoundedCornerShape(size = 5.dp)
                                        )
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "Gold Pieces:",
                                            minLines = 2,
                                            textAlign = TextAlign.Center,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = characterSheet.goldPieces.value.toString(),
                                            textAlign = TextAlign.Center,
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            modifier = Modifier.padding(top = 5.dp)
                                        )
                                    }
                                }
                                TextButton(
                                    onClick = { openPlatinumDialog = true },
                                    modifier = Modifier
                                        .size(size = 100.dp)
                                        .border(
                                            width = 2.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(size = 5.dp)
                                        )
                                        .background(
                                            color = primaryContainerLight,
                                            shape = RoundedCornerShape(size = 5.dp)
                                        )
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "Platinum Pieces:",
                                            minLines = 2,
                                            textAlign = TextAlign.Center,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = characterSheet.platinumPieces.value.toString(),
                                            textAlign = TextAlign.Center,
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            modifier = Modifier.padding(top = 5.dp)
                                        )
                                    }
                                }
                            }
                            if (openCopperDialog) {
                                IntStatEntryPopup(
                                    onDismissRequest = { openCopperDialog = false },
                                    stat = characterSheet.copperPieces,
                                    statName = "Copper Pieces:",
                                    editable = true,
                                    updateFunction = updateFunction
                                )
                            } else if (openSilverDialog) {
                                IntStatEntryPopup(
                                    onDismissRequest = { openSilverDialog = false },
                                    stat = characterSheet.silverPieces,
                                    statName = "Silver Pieces:",
                                    editable = true,
                                    updateFunction = updateFunction
                                )
                            } else if (openElectrumDialog) {
                                IntStatEntryPopup(
                                    onDismissRequest = { openElectrumDialog = false },
                                    stat = characterSheet.electrumPieces,
                                    statName = "Electrum Pieces:",
                                    editable = true,
                                    updateFunction = updateFunction
                                )
                            } else if (openGoldDialog) {
                                IntStatEntryPopup(
                                    onDismissRequest = { openGoldDialog = false },
                                    stat = characterSheet.goldPieces,
                                    statName = "Gold Pieces:",
                                    editable = true,
                                    updateFunction = updateFunction
                                )
                            } else if (openPlatinumDialog) {
                                IntStatEntryPopup(
                                    onDismissRequest = { openPlatinumDialog = false },
                                    stat = characterSheet.platinumPieces,
                                    statName = "Platinum" +
                                            " Pieces:",
                                    editable = true,
                                    updateFunction = updateFunction
                                )
                            }
                        }
                    }
                }

                composable(route = characterSheetScreens.Spells.name) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(state = rememberScrollState())
                    ) {
                        //Background Image
                        Image(
                            painter = painterResource(id = R.drawable.grunge_background),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.matchParentSize()
                        )
                        Column (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SpellInfoWindow(
                                characterSheet = characterSheet,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                initializer = characterSheetInitializer,
                                updateFunction = updateFunction,
                            )
                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )
                            OnlineSpellBookWindow(
                                characterSheet = characterSheet,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                updateFunction = updateFunction,
                                appViewModel = AppViewModel(context = LocalContext.current),
                                viewModel = viewModel
                            )
                        }
                    }
                }
                composable(route = characterSheetScreens.Info.name) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(state = rememberScrollState())
                    ) {
                        //Background Image
                        Image(
                            painter = painterResource(id = R.drawable.grunge_background),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.matchParentSize()
                        )
                        Column (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BasicAppearanceWindow(
                                characterSheet = characterSheet,
                                refresherFlag = refreshFlag,
                                updateFunction = updateFunction
                            )

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            StringWindow(
                                characterSheet = characterSheet,
                                stringList = characterSheet.characterPersonalityTraits,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                title = "Personality Traits",
                                updateFunction = updateFunction
                            )

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            StringWindow(
                                characterSheet = characterSheet,
                                stringList = characterSheet.characterIdeals,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                title = "Ideals",
                                updateFunction = updateFunction
                            )

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            StringWindow(
                                characterSheet = characterSheet,
                                stringList = characterSheet.characterBonds,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                title = "Bonds",
                                updateFunction = updateFunction
                            )

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            StringWindow(
                                characterSheet = characterSheet,
                                stringList = characterSheet.characterFlaws,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                title = "Flaws",
                                updateFunction = updateFunction
                            )

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            StringWindow(
                                characterSheet = characterSheet,
                                stringList = characterSheet.characterBackstory,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                title = "Character Backstory",
                                updateFunction = updateFunction
                            )

                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 15.dp)
                            )

                            StringWindow(
                                characterSheet = characterSheet,
                                stringList = characterSheet.alliesAndOrganizations,
                                refresherFlag = refreshFlag,
                                refreshContent = {
                                    refreshFlag = !refreshFlag
                                    Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                                },
                                title = "Allies & Organizations",
                                updateFunction = updateFunction
                            )

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OnlineSpellBookWindow(
    characterSheet: CharacterSheet,
    refresherFlag: Boolean = false,
    refreshContent: () -> Unit = {},
    updateFunction: () -> Unit = {},
    appViewModel: AppViewModel,
    viewModel: CampaignViewModel
){
    Column (
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()

    ) {
        OnlineSpellCantripWindow(characterSheet = characterSheet,
            spellList = characterSheet.spellBook.cantrips,
            title = "Cantrips",
            appViewModel = appViewModel,
            refreshContent = refreshContent,
            updateFunction = updateFunction,
            viewModel = viewModel)

        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 15.dp)
        )

        OnlineSpellLevelWindow(
            characterSheet = characterSheet,
            spellList = characterSheet.spellBook.levelOneSpells,
            maxSlots = characterSheet.spellBook.levelOneSlotsMax,
            currentSlots = characterSheet.spellBook.levelOneSlots,
            title = "Level One Spells",
            appViewModel = appViewModel,
            refreshContent = refreshContent,
            updateFunction = updateFunction,
            viewModel = viewModel)

        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 15.dp)
        )

        OnlineSpellLevelWindow(
            characterSheet = characterSheet,
            spellList = characterSheet.spellBook.levelTwoSpells,
            maxSlots = characterSheet.spellBook.levelTwoSlotsMax,
            currentSlots = characterSheet.spellBook.levelTwoSlots,
            title = "Level Two Spells",
            appViewModel = appViewModel,
            refreshContent = refreshContent,
            updateFunction = updateFunction,
            viewModel = viewModel)

        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 15.dp)
        )

        OnlineSpellLevelWindow(
            characterSheet = characterSheet,
            spellList = characterSheet.spellBook.levelThreeSpells,
            maxSlots = characterSheet.spellBook.levelThreeSlotsMax,
            currentSlots = characterSheet.spellBook.levelThreeSlots,
            title = "Level Three Spells",
            appViewModel = appViewModel,
            refreshContent = refreshContent,
            updateFunction = updateFunction,
            viewModel = viewModel)

        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 15.dp)
        )

        OnlineSpellLevelWindow(
            characterSheet = characterSheet,
            spellList = characterSheet.spellBook.levelFourSpells,
            maxSlots = characterSheet.spellBook.levelFourSlotsMax,
            currentSlots = characterSheet.spellBook.levelFourSlots,
            title = "Level Four Spells",
            appViewModel = appViewModel,
            refreshContent = refreshContent,
            updateFunction = updateFunction,
            viewModel = viewModel)

        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 15.dp)
        )

        OnlineSpellLevelWindow(
            characterSheet = characterSheet,
            spellList = characterSheet.spellBook.levelFiveSpells,
            maxSlots = characterSheet.spellBook.levelFiveSlotsMax,
            currentSlots = characterSheet.spellBook.levelFiveSlots,
            title = "Level Five Spells",
            appViewModel = appViewModel,
            refreshContent = refreshContent,
            updateFunction = updateFunction,
            viewModel = viewModel)

        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 15.dp)
        )

        OnlineSpellLevelWindow(
            characterSheet = characterSheet,
            spellList = characterSheet.spellBook.levelSixSpells,
            maxSlots = characterSheet.spellBook.levelSixSlotsMax,
            currentSlots = characterSheet.spellBook.levelSixSlots,
            title = "Level Six Spells",
            appViewModel = appViewModel,
            refreshContent = refreshContent,
            updateFunction = updateFunction,
            viewModel = viewModel)

        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 15.dp)
        )

        OnlineSpellLevelWindow(
            characterSheet = characterSheet,
            spellList = characterSheet.spellBook.levelSevenSpells,
            maxSlots = characterSheet.spellBook.levelSevenSlotsMax,
            currentSlots = characterSheet.spellBook.levelSevenSlots,
            title = "Level Seven Spells",
            appViewModel = appViewModel,
            refreshContent = refreshContent,
            updateFunction = updateFunction,
            viewModel =  viewModel)

        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 15.dp)
        )

        OnlineSpellLevelWindow(
            characterSheet = characterSheet,
            spellList = characterSheet.spellBook.levelEightSpells,
            maxSlots = characterSheet.spellBook.levelEightSlotsMax,
            currentSlots = characterSheet.spellBook.levelEightSlots,
            title = "Level Eight Spells",
            appViewModel = appViewModel,
            refreshContent = refreshContent,
            updateFunction = updateFunction,
            viewModel = viewModel)

        Divider(
            color = Color.Black,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 15.dp)
        )

        OnlineSpellLevelWindow(
            characterSheet = characterSheet,
            spellList = characterSheet.spellBook.levelNineSpells,
            maxSlots = characterSheet.spellBook.levelNineSlotsMax,
            currentSlots = characterSheet.spellBook.levelNineSlots,
            title = "Level Nine Spells",
            appViewModel = appViewModel,
            refreshContent = refreshContent,
            updateFunction = updateFunction,
            viewModel = viewModel)
    }

}

@Composable
fun OnlineSpellLevelWindow(
    characterSheet: CharacterSheet,
    refresherFlag: Boolean = false,
    refreshContent: () -> Unit = {},
    spellList: List<Spell>?,
    maxSlots: StatList,
    currentSlots: IntWrapper,
    title: String = "",
    updateFunction: () -> Unit = {},
    appViewModel: AppViewModel,
    viewModel: CampaignViewModel
){
    Card(modifier = Modifier.fillMaxWidth()
        .heightIn(min = 0.dp, max = 500.dp)
        .background(
            color = primaryContainerLight,
            shape = RoundedCornerShape(size = 5.dp)
        )
        .border(
            width = 2.dp,
            color = Color.Black,
            shape = RoundedCornerShape(size = 5.dp)
        )

    ) {
        Column () {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 5.dp)
            )
            Divider(modifier = Modifier.padding(horizontal = 10.dp), color = Color.Black)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val maxSlotsText by remember {
                    mutableStateOf(maxSlots)
                }

                var openMaxSlotsDialog by remember {
                    mutableStateOf(false)
                }

                Text(
                    text = "Max Slots:",
                    textAlign = TextAlign.Start,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(end = 15.dp, start = 5.dp)
                )
                TextButton(
                    onClick = { openMaxSlotsDialog = true },
                    contentPadding = PaddingValues(all = 0.dp),
                    modifier = Modifier.offset(x = -10.dp),
                    colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
                ) {
                    Text(
                        text = maxSlotsText.sumEntries().toString(),
                        color = Color.Black,
                        fontSize = 16.sp,
                        maxLines = 1
                    )
                }
                val currentSlotsText by remember {
                    mutableStateOf(currentSlots)
                }

                var openCurrentSlotsDialog by remember {
                    mutableStateOf(false)
                }
                Text(
                    text = "Current:",
                    textAlign = TextAlign.Start,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(end = 15.dp, start = 5.dp)
                )
                TextButton(
                    onClick = { openCurrentSlotsDialog = true },
                    contentPadding = PaddingValues(all = 0.dp),
                    modifier = Modifier.offset(x = -10.dp),
                    colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
                ) {
                    Text(
                        text = currentSlots.value.toString(),
                        color = Color.Black,
                        fontSize = 16.sp,
                        maxLines = 1
                    )
                }
                if(openMaxSlotsDialog){
                    StatEntryPopup(
                        onDismissRequest = {
                            openMaxSlotsDialog = false
                            refreshContent()
                        },
                        statList = maxSlots,
                        statName = "Current Slots:",
                        baseStatEditable = true,
                        baseStatString = "Level",
                        updateFunction = updateFunction
                    )
                }
                else if (openCurrentSlotsDialog){
                    IntStatEntryPopup(
                        onDismissRequest = {
                            openCurrentSlotsDialog = false
                            refreshContent()
                        },
                        stat = currentSlots,
                        statName = "Current Slots:",
                        editable = true,
                        updateFunction = updateFunction
                    )

                }

            }
            if (spellList != null) {
                Divider(modifier = Modifier.padding(horizontal = 10.dp), color = Color.LightGray)
                OnlineSpellList(spellList = spellList, appViewModel = appViewModel, additionMode = false, updateFunction = {
                    updateFunction()
                    refreshContent()
                }, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun OnlineSpellCantripWindow(
    characterSheet: CharacterSheet = theoChar.characterSheet,
    refresherFlag: Boolean = false,
    refreshContent: () -> Unit = {},
    spellList: List<Spell>?,
    title: String = "",
    updateFunction: () -> Unit = {},
    appViewModel: AppViewModel,
    viewModel: CampaignViewModel){
    Card(modifier = Modifier.fillMaxWidth()
        .heightIn(min = 0.dp, max = 500.dp)
        .background(
            color = primaryContainerLight,
            shape = RoundedCornerShape(size = 5.dp)
        )
        .border(
            width = 2.dp,
            color = Color.Black,
            shape = RoundedCornerShape(size = 5.dp)
        )

    ) {
        Column () {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 5.dp)
            )
            Divider(modifier = Modifier.padding(horizontal = 10.dp), color = Color.Black)
        }
        if (spellList != null) {
            Divider(modifier = Modifier.padding(horizontal = 10.dp), color = Color.LightGray)
            OnlineSpellList(spellList = spellList, appViewModel = appViewModel, additionMode = false, updateFunction = {
                //appViewModel.updateDatabase(characterSheet = characterSheet)
                updateFunction()
                refreshContent()
            },
                viewModel = viewModel)
        }
    }
}