package com.example.dragontome.screens

import android.content.Context
import android.util.Log
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dragontome.R
import com.example.dragontome.data.Attack
import com.example.dragontome.data.CharacterClass
import com.example.dragontome.data.CharacterSheet
import com.example.dragontome.data.CharacterSheetHolder
import com.example.dragontome.data.CharacterSheetInitializer
import com.example.dragontome.data.CharacterStat
import com.example.dragontome.data.ClassLevel
import com.example.dragontome.data.IntWrapper
import com.example.dragontome.data.JSONSheet
import com.example.dragontome.data.Spell
import com.example.dragontome.data.SpellBook
import com.example.dragontome.data.StatEntry
import com.example.dragontome.data.StatList
import com.example.dragontome.data.StatListWithProficiency
import com.example.dragontome.data.StringListWrapper
import com.example.dragontome.data.StringWrapper
import com.example.dragontome.state.AppViewModel
import com.example.dragontome.ui.theme.addColor
import com.example.dragontome.ui.theme.onPrimaryContainerLight
import com.example.dragontome.ui.theme.primaryContainerLight
import com.example.dragontome.ui.theme.primaryLight
import com.example.dragontome.ui.theme.removeColor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.lang.NumberFormatException


var theoChar:CharacterSheetHolder = CharacterSheetHolder(CharacterSheet("Theo",
   "Human",
   "Acolyte",
   "Chaotic Gooddddddddddddddddddd",
   100,
   listOf(ClassLevel(characterClass = CharacterClass.ARTIFICER, 1), ClassLevel(CharacterClass.BARBARIAN, 3), ClassLevel(CharacterClass.BLOODHUNTER, 7)),
   StringListWrapper(),
   StringListWrapper(),
   StringListWrapper(),
   StringListWrapper(),
   StringListWrapper(),
   IntWrapper(0),
   StatList(),
   StatList(),
   StatList(),
   StatList(),
   "4d6",
   IntWrapper(),
   StatList(),
   IntWrapper(),
   StatList(),
   StatList(),
   StatList(),
   StatList(),
   StatList(),
   StatList(),
   StatList(),
   StatList(),
   StatList(),
   StatList(),
   StatList(),
   StatList(),
   listOf(Attack("Shortsword", StatList(),"2d6 S")),
   StringListWrapper(listOf(StringWrapper("Shortsword"), StringWrapper("Picture"))),
   StringListWrapper(),
   IntWrapper(32),
   IntWrapper(1),
   IntWrapper(523),
   IntWrapper(2),
   IntWrapper(421),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),

   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatListWithProficiency(),
   StatList(),
   "23",
   "6'4",
   "210",
   "Blue",
   "Pale",
   "Brown",
   StringListWrapper(),
   StringListWrapper(),
   StringListWrapper(listOf(StringWrapper("Golden Chain"))),
   CharacterClass.ARTIFICER,
   CharacterStat.INTELLIGENCE,
   StatList(),
   StatList(),

   spellBook = SpellBook(
      levelOneSlots = IntWrapper(3),
      levelOneSlotsMax = StatList(listOf(StatEntry(5)), usesBaseStat = false),
      levelOneSpells = listOf(Spell(0, "Magic Missile", "1", "Evocation", "1 Action", "60 ft", "V,S", "Instant", "Xanathar's", "Sample text here!"))

   )
),0)

public enum class characterSheetScreens(){
   Stats,
   AbilitiesAndInventory,
   Spells,
   Info
}

@Composable
fun CharacterNavBar(
   navController: NavHostController
){
   var selectedItem by remember {
      mutableIntStateOf(0)
   }

   TabRow(selectedTabIndex = selectedItem,
      backgroundColor = primaryLight,
      contentColor = Color.Black,
      modifier = Modifier
         .border(width = 2.dp, color = Color.DarkGray)
         .height(50.dp)
      ) {

      Tab(selected = selectedItem == 0, onClick = {
         selectedItem = 0
         navController.navigate(characterSheetScreens.Stats.name)
      },
         modifier = Modifier.border(width = 1.dp, color = Color.LightGray)
      ) {
         Text(text = "Stats")
      }

      Tab(selected = selectedItem == 1, onClick = {
         selectedItem = 1
         navController.navigate(characterSheetScreens.AbilitiesAndInventory.name)
      },
         modifier = Modifier.border(width = 1.dp, color = Color.LightGray)
         ) {
         Text(text = "Abilities/Inventory", textAlign = TextAlign.Center)
      }

      Tab(selected = selectedItem == 2, onClick = {
         selectedItem = 2
         navController.navigate(characterSheetScreens.Spells.name)
      },
         modifier = Modifier.border(width = 1.dp, color = Color.LightGray)
      ) {
         Text(text = "Spells")
      }

      Tab(selected = selectedItem == 3, onClick = {
         selectedItem = 3
         navController.navigate(characterSheetScreens.Info.name)
      },
         modifier = Modifier.border(width = 1.dp, color = Color.LightGray)
      ) {
         Text(text = "Info")
      }

   }


}



@Composable
fun CharacterSheetScreen(
   characterSheetHolder: CharacterSheetHolder = theoChar,
   modifier: Modifier = Modifier,
   navController: NavHostController = rememberNavController(),
   appViewModel: AppViewModel
){
   var characterSheet = characterSheetHolder.characterSheet
   var updateFunction: () -> Unit = { appViewModel.updateDatabase(characterSheet = characterSheet)}
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
      var characterSheetInitializer:CharacterSheetInitializer = CharacterSheetInitializer(characterSheet = characterSheet)

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
                     modifier = modifier.matchParentSize()
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
                           modifier = modifier
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
                              modifier = modifier.fillMaxWidth()
                           )

                           Divider(Modifier.padding(horizontal = 10.dp))

                           //Class Field

                           TextButton(
                              onClick = { characterClassPopup = true },
                              modifier = modifier
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
                     modifier = modifier.matchParentSize()
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
                     modifier = modifier.matchParentSize()
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
                     SpellBookWindow(
                        characterSheet = characterSheet,
                        refresherFlag = refreshFlag,
                        refreshContent = {
                           refreshFlag = !refreshFlag
                           Log.d("debug", "Should have flagged! Current: ${refreshFlag}")
                        },
                        updateFunction = updateFunction,
                        appViewModel = appViewModel
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
                     modifier = modifier.matchParentSize()
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

@Preview
@Composable
fun ClassPopupWindow(
   characterSheet: CharacterSheet = theoChar.characterSheet,
   onDismissRequest: () -> Unit = {},
   updateFunction: () -> Unit = {}
){
   Dialog(onDismissRequest = onDismissRequest) {
      Card (
         modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .verticalScroll(state = rememberScrollState()),
         shape = RoundedCornerShape(16.dp)
      ) {
         Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Character Class:",
               textAlign = TextAlign.Center,
               modifier = Modifier.padding(all = 10.dp))

            Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))



            var characterClassState by remember {
               mutableStateOf(characterSheet.characterClass)
            }

            var deletionMode by remember {
               mutableStateOf(false)
            }

            for(classPair in characterClassState){
               Row (
                  horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier
                     .fillMaxWidth()
                     .padding(horizontal = 20.dp)) {
                  //Dropdown Menu for each

                  var expanded by remember {
                     mutableStateOf(false)
                  }

                  var classText by remember {
                     mutableStateOf(characterSheet.characterClass[characterSheet.characterClass.indexOf(classPair)].characterClass)
                  }

                  if(deletionMode){
                     IconButton(
                        onClick = {
                           characterClassState = characterClassState.minus(classPair)
                           characterSheet.characterClass = characterClassState
                           updateFunction()
                           Log.d("debug", "CharacterLevels: ${characterSheet.characterClass.toString()}")
                                  },
                        modifier = Modifier.size(size = 16.dp)
                        ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete Class")
                     }
                  }

                  IconButton(onClick = { expanded = true }, modifier = Modifier.size(size = 20.dp)) {
                     Icon(
                        Icons.Filled.Menu,
                        contentDescription = "List Classes",
                        modifier = Modifier.size(size = 20.dp)
                        )
                  }

                  Text(text = classText.toString(), fontSize = 13.sp)
                  
                  DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                     //For loop creates a menu item in the dropdown for each class value
                     for (charClass in CharacterClass.entries){
                        DropdownMenuItem(text = { Text(text = charClass.name) }, onClick = {
                           classText = charClass
                           classPair.characterClass = classText
                           updateFunction()
                           expanded = false
                        })
                     }
                  }


                  var levelText by remember {
                     mutableStateOf(characterSheet.characterClass[characterSheet.characterClass.indexOf(classPair)].classLevel)
                  }
                  BasicTextField(
                     keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                     value = levelText.toString(),
                     onValueChange = {
                        try {
                           levelText = it.toInt()
                        }
                        catch (e:NumberFormatException) {
                           levelText = 0
                        }
                        characterSheet.characterClass[characterSheet.characterClass.indexOf(classPair)].classLevel = levelText
                        updateFunction()
                        Log.d("debug", "Current pair: ${characterSheet.characterClass[characterSheet.characterClass.indexOf(classPair)]}")
                                     },
                     modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .sizeIn(minWidth = 30.dp, maxWidth = 30.dp)
                        .border(
                           width = 1.dp,
                           color = Color.Gray,
                           shape = RoundedCornerShape(size = 5.dp)
                        ),
                     textStyle = TextStyle(textAlign = TextAlign.Center),
                     singleLine = true)
               }
               Divider(modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp))
            }

            Row (
               modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 5.dp),
               horizontalArrangement = Arrangement.Absolute.SpaceEvenly
            ) {
               //Add class button
               IconButton(
                  onClick = {
                     characterClassState =
                        characterClassState.plus(ClassLevel(CharacterClass.ARTIFICER, 1))
                     characterSheet.characterClass = characterClassState
                     updateFunction()
                     Log.d("debug", "CharacterLevels: ${characterSheet.characterClass.toString()}")
                  },
                  modifier = Modifier
                     .size(size = 20.dp)
               ) {
                  Icon(
                     Icons.Filled.Add,
                     contentDescription = "Add Class",
                     tint = Color.DarkGray,
                     modifier = Modifier
                        .border(
                           width = 1.dp,
                           color = Color.DarkGray,
                           shape = RoundedCornerShape(size = 5.dp)
                        )
                        .background(color = addColor, shape = RoundedCornerShape(size = 5.dp))
                  )
               }
               //Remove class button
               IconButton(
                  onClick = {
                     deletionMode = !deletionMode
                  },
                  modifier = Modifier
                     .size(size = 20.dp)
               ) {
                  Icon(
                     Icons.Filled.Clear,
                     contentDescription = "Remove Class",
                     tint = Color.DarkGray,
                     modifier = Modifier
                        .border(
                           width = 1.dp,
                           color = Color.DarkGray,
                           shape = RoundedCornerShape(size = 5.dp)
                        )
                        .background(color = removeColor, shape = RoundedCornerShape(size = 5.dp))
                  )
               }

            }
            
         }



      }
   }
}

@Preview
@Composable
fun BasicInfoWindow(
   characterSheet: CharacterSheet = theoChar.characterSheet,
   modifier: Modifier = Modifier,
   refresherFlag: Boolean = false,
   updateFunction: () -> Unit = {}
){
   Column (
      modifier = modifier
         .background(
            color = primaryContainerLight,
            shape = RoundedCornerShape(size = 2.dp)
         )
         .fillMaxWidth()
         .border(
            width = 2.dp,
            color = Color.Black,
            shape = RoundedCornerShape(size = 2.dp)
         )
   ) {
      Text(
         text = "Basic Info", textAlign = TextAlign.Center,
         modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
      )
      Divider(modifier = modifier.padding(horizontal = 10.dp))
      Row  {
         Column(verticalArrangement = Arrangement.SpaceEvenly) {
            Row(modifier = modifier
               .padding(vertical = 5.dp, horizontal = 5.dp)
               .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
               Text(
                  text = "Background: ",
                  fontWeight = FontWeight.Bold
               )

               var backgroundText by remember {
                  mutableStateOf(characterSheet.background)
               }
               BasicTextField(
                  value = backgroundText,
                  onValueChange = {
                     backgroundText = it
                     characterSheet.background = backgroundText
                     updateFunction()
                  },
                  singleLine = true,
                  modifier = modifier
                     .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(size = 5.dp)
                     )
                     .padding(horizontal = 3.dp)
                     .sizeIn(maxWidth = 100.dp)
               )
               Text(
                  text = "Race: ",
                  fontWeight = FontWeight.Bold
               )
               var raceText by remember {
                  mutableStateOf(characterSheet.race)
               }
               BasicTextField(
                  value = raceText,
                  onValueChange = {
                     raceText = it
                     characterSheet.race = raceText
                     updateFunction()
                  },
                  singleLine = true,
                  modifier = modifier
                     .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(size = 5.dp)
                     )
                     .padding(horizontal = 3.dp)
                     .sizeIn(maxWidth = 100.dp)
               )
            }
            Row(modifier = modifier
               .padding(vertical = 5.dp, horizontal = 5.dp)
               .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
               Text(
                  text = "Alignment: ",
                  fontWeight = FontWeight.Bold
               )
               var alignmentText by remember {
                  mutableStateOf(characterSheet.characterAlignment)
               }
               BasicTextField(
                  value = alignmentText,
                  onValueChange = {
                     alignmentText = it
                     characterSheet.characterAlignment = alignmentText
                     updateFunction()
                  },
                  singleLine = true,
                  modifier = modifier
                     .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(size = 5.dp)
                     )
                     .padding(horizontal = 3.dp)
                     .sizeIn(maxWidth = 100.dp)
               )
               Text(
                  text = "EXP: ",
                  fontWeight = FontWeight.Bold
               )

               var expText by remember {
                  mutableStateOf(characterSheet.experiencePoints)
               }
               BasicTextField(
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                  value = expText.toString(),
                  onValueChange = {
                     try {
                        expText = it.toInt()
                     }
                     catch (e:NumberFormatException) {
                        expText = 0
                     }

                     characterSheet.experiencePoints = expText
                     updateFunction()
                  },
                  singleLine = true,
                  modifier = modifier
                     .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(size = 5.dp)
                     )
                     .padding(horizontal = 3.dp)
               )
            }
         }
      }
   }
}

@Preview
@Composable
fun PrimaryStats(
    characterSheet: CharacterSheet = theoChar.characterSheet,
    refresherFlag: Boolean = false,
    initializer: CharacterSheetInitializer = CharacterSheetInitializer(characterSheet),
    refreshContent: () -> Unit = {},
    updateFunction: () -> Unit = {}
){

   var openProficiencyDialog by remember {
      mutableStateOf(false)
   }
   var openArmorClassDialog by remember {
      mutableStateOf(false)
   }
   var openInitiativeDialog by remember {
      mutableStateOf(false)
   }
   var openSpeedDialog by remember {
      mutableStateOf(false)
   }
   var openInspirationDialog  by remember {
      mutableStateOf(false)
   }

   Column (modifier = Modifier
      .fillMaxWidth()
   ) {
      Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
         TextButton(
            onClick = {openProficiencyDialog = true},
            modifier = Modifier
               .size(size = 100.dp)
               .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(size = 5.dp))
               .background(color = primaryContainerLight, shape = RoundedCornerShape(size = 5.dp))
            ) {

            Column (horizontalAlignment = Alignment.CenterHorizontally) {
               Text(
                  text = "Proficiency Bonus:",
                  minLines = 2,
                  textAlign = TextAlign.Center,
                  color = Color.Black
               )
               Text(
                  text = characterSheet.proficiencyBonus.sumEntries().toString(),
                  textAlign = TextAlign.Center,
                  color = Color.Black,
                  fontSize = 20.sp,
                  modifier = Modifier.padding(top = 5.dp)
               )
            }
         }
         TextButton(
            onClick = {openArmorClassDialog = true},
            modifier = Modifier
               .size(size = 100.dp)
               .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(size = 5.dp))
               .background(color = primaryContainerLight, shape = RoundedCornerShape(size = 5.dp))
         ) {

            Column (horizontalAlignment = Alignment.CenterHorizontally) {
               Text(
                  text = "Armor Class:",
                  minLines = 2,
                  textAlign = TextAlign.Center,
                  color = Color.Black
               )
               Text(
                  text = characterSheet.armorClass.sumEntries().toString(),
                  textAlign = TextAlign.Center,
                  color = Color.Black,
                  fontSize = 20.sp,
                  modifier = Modifier.padding(top = 5.dp)
               )
            }
         }

         TextButton(
            onClick = {openInitiativeDialog = true},
            modifier = Modifier
               .size(size = 100.dp)
               .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(size = 5.dp))
               .background(color = primaryContainerLight, shape = RoundedCornerShape(size = 5.dp))
         ) {

            Column (horizontalAlignment = Alignment.CenterHorizontally) {
               Text(
                  text = "Initiative:",
                  minLines = 2,
                  textAlign = TextAlign.Center,
                  color = Color.Black
               )
               Text(
                  text = characterSheet.initiative.sumEntries().toString(),
                  textAlign = TextAlign.Center,
                  color = Color.Black,
                  fontSize = 20.sp,
                  modifier = Modifier.padding(top = 5.dp)
               )
            }
         }
      }
      Row (
         horizontalArrangement = Arrangement.SpaceEvenly,
         modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
      ) {
         TextButton(
            onClick = {openSpeedDialog = true},
            modifier = Modifier
               .size(size = 100.dp)
               .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(size = 5.dp))
               .background(color = primaryContainerLight, shape = RoundedCornerShape(size = 5.dp))
         ) {

            Column (horizontalAlignment = Alignment.CenterHorizontally) {
               Text(
                  text = "Speed:",
                  minLines = 2,
                  textAlign = TextAlign.Center,
                  color = Color.Black
               )
               Text(
                  text = characterSheet.speed.sumEntries().toString(),
                  textAlign = TextAlign.Center,
                  color = Color.Black,
                  fontSize = 20.sp,
                  modifier = Modifier.padding(top = 5.dp)
               )
            }
         }
         TextButton(
            onClick = {openInspirationDialog = true},
            modifier = Modifier
               .size(size = 100.dp)
               .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(size = 5.dp))
               .background(color = primaryContainerLight, shape = RoundedCornerShape(size = 5.dp))
         ) {

            Column (horizontalAlignment = Alignment.CenterHorizontally) {
               Text(
                  text = "Inspiration Points",
                  minLines = 2,
                  textAlign = TextAlign.Center,
                  color = Color.Black
               )
               Text(
                  text = characterSheet.inspirationPoints.value.toString(),
                  textAlign = TextAlign.Center,
                  color = Color.Black,
                  fontSize = 20.sp,
                  modifier = Modifier.padding(top = 5.dp)
               )
            }
         }
      }
   }
   if(openProficiencyDialog){
      StatEntryPopup(
         onDismissRequest = {
            openProficiencyDialog = false
            initializer.refreshSpellAttackBonus()
            refreshContent()
                            },
         statList = characterSheet.proficiencyBonus,
         statName = "Proficiency Bonus:",
         updateFunction = updateFunction
      )
   }
   else if(openArmorClassDialog){
      StatEntryPopup(
         onDismissRequest = {openArmorClassDialog = false},
         statList = characterSheet.armorClass,
         statName = "Armor Class:",
         updateFunction = updateFunction
         )
   }
   else if (openInitiativeDialog){
      StatEntryPopup(
         onDismissRequest = {openInitiativeDialog = false},
         statList = characterSheet.initiative,
         statName = "Initiative:",
         updateFunction = updateFunction
      )
   }
   else if (openSpeedDialog){
      StatEntryPopup(
         onDismissRequest = {openSpeedDialog = false},
         statList = characterSheet.speed,
         statName = "Speed",
         updateFunction = updateFunction
      )
   }
   else if (openInspirationDialog){
      IntStatEntryPopup(
         onDismissRequest = {openInspirationDialog = false},
         stat = characterSheet.inspirationPoints,
         statName = "Inspiration Points:",
         updateFunction = updateFunction
      )
   }
}

@Preview
@Composable
fun AttributesWindow(
   characterSheet: CharacterSheet = theoChar.characterSheet,
   initializer: CharacterSheetInitializer = CharacterSheetInitializer(characterSheet),
   refreshContent: () -> Unit = {},
   refresherFlag:Boolean = false,
   updateFunction: () -> Unit = {}
){

   remember {
      mutableStateOf(characterSheet)
   }

   var openStrengthDialog by remember {
      mutableStateOf(false)
   }
   var openDexterityDialog by remember {
      mutableStateOf(false)
   }
   var openConstitutionDialog by remember {
      mutableStateOf(false)
   }
   var openIntelligenceDialog by remember {
      mutableStateOf(false)
   }
   var openWisdomDialog by remember {
      mutableStateOf(false)
   }
   var openCharismaDialog by remember {
      mutableStateOf(false)
   }

   var openStrengthModifierDialog by remember {
      mutableStateOf(false)
   }
   var openDexterityModifierDialog by remember {
      mutableStateOf(false)
   }
   var openConstitutionModifierDialog by remember {
      mutableStateOf(false)
   }
   var openIntelligenceModifierDialog by remember {
      mutableStateOf(false)
   }
   var openWisdomModifierDialog by remember {
      mutableStateOf(false)
   }
   var openCharismaModifierDialog by remember {
      mutableStateOf(false)
   }

   Column (modifier = Modifier.fillMaxWidth()) {
      Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
         .fillMaxWidth()
         .padding(bottom = 10.dp) ) {
         Column {
            TextButton(
               onClick = { openStrengthDialog = true },
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
                     text = "Strength:",
                     minLines = 2,
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                  )
                  Text(
                     text = characterSheet.strength.sumEntries().toString(),
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                     fontSize = 20.sp,
                     modifier = Modifier.padding(top = 5.dp)
                  )
               }
            }
            TextButton(
               modifier = Modifier
                  .size(width = 100.dp, height = 65.dp)
                  .border(
                     width = 2.dp,
                     color = Color.Black,
                     shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                     )
                  )
                  .background(
                     color = primaryContainerLight,
                     shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                     )
                  ),
               onClick = {openStrengthModifierDialog = true}) {
               Column (horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(
                     text = "Modifier:",
                     minLines = 2,
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                     fontSize = 12.sp
                  )
                  Text(
                     text = characterSheet.strengthBonus.sumEntries().toString(),
                     textAlign = TextAlign.End,
                     color = Color.Black,
                     fontSize = 14.sp,
                     modifier = Modifier.offset(y = -10.dp)
                  )
               }
            }
         }
         Column {
            TextButton(
               onClick = { openDexterityDialog = true },
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
                     text = "Dexterity:",
                     minLines = 2,
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                  )
                  Text(
                     text = characterSheet.dexterity.sumEntries().toString(),
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                     fontSize = 20.sp,
                     modifier = Modifier.padding(top = 5.dp)
                  )
               }
            }
            TextButton(
               modifier = Modifier
                  .size(width = 100.dp, height = 65.dp)
                  .border(
                     width = 2.dp,
                     color = Color.Black,
                     shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                     )
                  )
                  .background(
                     color = primaryContainerLight,
                     shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                     )
                  ),
               onClick = {openDexterityModifierDialog = true}) {
               Column (horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(
                     text = "Modifier:",
                     minLines = 2,
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                     fontSize = 12.sp
                  )
                  Text(
                     text = characterSheet.dexterityBonus.sumEntries().toString(),
                     textAlign = TextAlign.End,
                     color = Color.Black,
                     fontSize = 14.sp,
                     modifier = Modifier.offset(y = -10.dp)
                  )
               }
            }
         }
         Column {
            TextButton(
               onClick = { openConstitutionDialog = true },
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
                     text = "Constitution:",
                     minLines = 2,
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                     fontSize = 11.sp
                  )
                  Text(
                     text = characterSheet.constitution.sumEntries().toString(),
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                     fontSize = 20.sp,
                     modifier = Modifier.padding(top = 5.dp)
                  )
               }
            }
            TextButton(
               modifier = Modifier
                  .size(width = 100.dp, height = 65.dp)
                  .border(
                     width = 2.dp,
                     color = Color.Black,
                     shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                     )
                  )
                  .background(
                     color = primaryContainerLight,
                     shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                     )
                  ),
               onClick = {openConstitutionModifierDialog = true}) {
               Column (horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(
                     text = "Modifier:",
                     minLines = 2,
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                     fontSize = 12.sp
                  )
                  Text(
                     text = characterSheet.constitutionBonus.sumEntries().toString(),
                     textAlign = TextAlign.End,
                     color = Color.Black,
                     fontSize = 14.sp,
                     modifier = Modifier.offset(y = -10.dp)
                  )
               }
            }
         }
      }
      Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth() ) {
         Column {
            TextButton(
               onClick = { openIntelligenceDialog = true },
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
                     text = "Intelligence:",
                     minLines = 2,
                     fontSize = 12.sp,
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                  )
                  Text(
                     text = characterSheet.intelligence.sumEntries().toString(),
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                     fontSize = 20.sp,
                     modifier = Modifier.padding(top = 5.dp)
                  )
               }
            }
            TextButton(
               modifier = Modifier
                  .size(width = 100.dp, height = 65.dp)
                  .border(
                     width = 2.dp,
                     color = Color.Black,
                     shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                     )
                  )
                  .background(
                     color = primaryContainerLight,
                     shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                     )
                  ),
               onClick = {openIntelligenceModifierDialog = true}) {
               Column (horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(
                     text = "Modifier:",
                     minLines = 2,
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                     fontSize = 12.sp
                  )
                  Text(
                     text = characterSheet.intelligenceBonus.sumEntries().toString(),
                     textAlign = TextAlign.End,
                     color = Color.Black,
                     fontSize = 14.sp,
                     modifier = Modifier.offset(y = -10.dp)
                  )
               }
            }
         }
         Column {
            TextButton(
               onClick = { openWisdomDialog = true },
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
                     text = "Wisdom:",
                     minLines = 2,
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                  )
                  Text(
                     text = characterSheet.wisdom.sumEntries().toString(),
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                     fontSize = 20.sp,
                     modifier = Modifier.padding(top = 5.dp)
                  )
               }
            }
            TextButton(
               modifier = Modifier
                  .size(width = 100.dp, height = 65.dp)
                  .border(
                     width = 2.dp,
                     color = Color.Black,
                     shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                     )
                  )
                  .background(
                     color = primaryContainerLight,
                     shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                     )
                  ),
               onClick = {openWisdomModifierDialog = true}) {
               Column (horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(
                     text = "Modifier:",
                     minLines = 2,
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                     fontSize = 12.sp
                  )
                  Text(
                     text = characterSheet.wisdomBonus.sumEntries().toString(),
                     textAlign = TextAlign.End,
                     color = Color.Black,
                     fontSize = 14.sp,
                     modifier = Modifier.offset(y = -10.dp)
                  )
               }
            }
         }
         Column {
            TextButton(
               onClick = { openCharismaDialog = true },
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
                     text = "Charisma:",
                     minLines = 2,
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                  )
                  Text(
                     text = characterSheet.charisma.sumEntries().toString(),
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                     fontSize = 20.sp,
                     modifier = Modifier.padding(top = 5.dp)
                  )
               }
            }
            TextButton(
               modifier = Modifier
                  .size(width = 100.dp, height = 65.dp)
                  .border(
                     width = 2.dp,
                     color = Color.Black,
                     shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                     )
                  )
                  .background(
                     color = primaryContainerLight,
                     shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                     )
                  ),
               onClick = {openCharismaModifierDialog = true}) {
               Column (horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(
                     text = "Modifier:",
                     minLines = 2,
                     textAlign = TextAlign.Center,
                     color = Color.Black,
                     fontSize = 12.sp
                  )
                  Text(
                     text = characterSheet.charismaBonus.sumEntries().toString(),
                     textAlign = TextAlign.End,
                     color = Color.Black,
                     fontSize = 14.sp,
                     modifier = Modifier.offset(y = -10.dp)
                  )
               }
            }
         }
      }
   }

   if(openStrengthDialog){
      StatEntryPopup(
         onDismissRequest = {
            openStrengthDialog = false
            Log.d("debug", "Strength: ${characterSheet.strength.sumEntries()}")
            Log.d("debug", "Bonus: ${characterSheet.strengthBonus.sumEntries()}")
            initializer.refreshStrength()
            initializer.refreshSpellSaveDC()
            initializer.refreshSpellAttackBonus()
            initializer.refreshStrengthSave()
            initializer.refreshAthletics()
            refreshContent()
                            },
         statList = characterSheet.strength,
         statName = "Strength:",
         updateFunction = updateFunction

      )
   }
   else if (openStrengthModifierDialog){
      StatEntryPopup(
         onDismissRequest = {
            openStrengthModifierDialog = false
            initializer.refreshStrengthSave()
            initializer.refreshAthletics()
            initializer.refreshSpellAttackBonus()
            refreshContent()
                            },
         statList = characterSheet.strengthBonus,
         statName = "Strength Bonus:",
         updateFunction = updateFunction
      )
   }
   else if(openDexterityDialog){
      StatEntryPopup(
         onDismissRequest = {
            openDexterityDialog = false
            Log.d("debug", "Dexterity: ${characterSheet.dexterity.sumEntries()}")
            Log.d("debug", "Bonus: ${characterSheet.dexterityBonus.sumEntries()}")
            initializer.refreshDexterity()
            initializer.refreshInitiative()
            initializer.refreshSpellSaveDC()
            initializer.refreshSpellAttackBonus()
            initializer.refreshAcrobatics()
            initializer.refreshSlightOfHand()
            initializer.refreshStealth()
            initializer.refreshDexteritySave()
            refreshContent()
         },
         statList = characterSheet.dexterity,
         statName = "Dexterity:",
         updateFunction = updateFunction

         )
   }
   else if (openDexterityModifierDialog){
      StatEntryPopup(
         onDismissRequest = {
            openDexterityModifierDialog = false
            initializer.refreshAcrobatics()
            initializer.refreshSlightOfHand()
            initializer.refreshStealth()
            initializer.refreshDexteritySave()
            initializer.refreshSpellAttackBonus()
            refreshContent()
                            },
         statList = characterSheet.dexterityBonus,
         statName = "Dexterity Bonus:",
         updateFunction = updateFunction
      )
   }
   else if(openConstitutionDialog){
      StatEntryPopup(
         onDismissRequest = {
            openConstitutionDialog = false
            Log.d("debug", "Constitution: ${characterSheet.constitution.sumEntries()}")
            Log.d("debug", "Bonus: ${characterSheet.constitutionBonus.sumEntries()}")
            initializer.refreshConstitution()
            initializer.refreshHitPointMax()
            initializer.refreshSpellSaveDC()
            initializer.refreshSpellAttackBonus()
            initializer.refreshConstitutionSave()
            refreshContent()
         },
         statList = characterSheet.constitution,
         statName = "Constitution:",
         updateFunction = updateFunction

         )
   }
   else if (openConstitutionModifierDialog){
      StatEntryPopup(
         onDismissRequest = {
            openConstitutionModifierDialog = false
            initializer.refreshConstitutionSave()
            initializer.refreshSpellAttackBonus()
            refreshContent()
                            },
         statList = characterSheet.constitutionBonus,
         statName = "Constitution Bonus:",
         updateFunction = updateFunction
      )
   }
   else if(openIntelligenceDialog){
      StatEntryPopup(
         onDismissRequest = {
            openIntelligenceDialog = false
            Log.d("debug", "Intelligence: ${characterSheet.intelligence.sumEntries()}")
            Log.d("debug", "Bonus: ${characterSheet.intelligenceBonus.sumEntries()}")
            initializer.refreshIntelligence()
            initializer.refreshSpellSaveDC()
            initializer.refreshSpellAttackBonus()
            initializer.refreshIntelligenceSave()
            initializer.refreshArcana()
            initializer.refreshHistory()
            initializer.refreshInvestigation()
            initializer.refreshNature()
            initializer.refreshReligion()
            refreshContent()
         },
         statList = characterSheet.intelligence,
         statName = "Intelligence:",
         updateFunction = updateFunction

         )
   }
   else if (openIntelligenceModifierDialog){
      StatEntryPopup(
         onDismissRequest = {
            openIntelligenceModifierDialog = false
            initializer.refreshIntelligenceSave()
            initializer.refreshArcana()
            initializer.refreshHistory()
            initializer.refreshInvestigation()
            initializer.refreshNature()
            initializer.refreshReligion()
            initializer.refreshSpellAttackBonus()
            refreshContent()
                            },
         statList = characterSheet.intelligenceBonus,
         statName = "Intelligence Bonus:",
         updateFunction = updateFunction
      )
   }
   else if(openWisdomDialog){
      StatEntryPopup(
         onDismissRequest = {
            openWisdomDialog = false
            Log.d("debug", "Wisdom: ${characterSheet.wisdom.sumEntries()}")
            Log.d("debug", "Bonus: ${characterSheet.wisdomBonus.sumEntries()}")
            initializer.refreshWisdom()
            initializer.refreshSpellSaveDC()
            initializer.refreshSpellAttackBonus()
            initializer.refreshWisdomSave()
            initializer.refreshAnimalHandling()
            initializer.refreshInsight()
            initializer.refreshMedicine()
            initializer.refreshPerception()
            initializer.refreshSurvival()
            refreshContent()
         },
         statList = characterSheet.wisdom,
         statName = "Wisdom:",
         updateFunction = updateFunction

         )
   }
   else if (openWisdomModifierDialog){
      StatEntryPopup(
         onDismissRequest = {
            openWisdomModifierDialog = false
            initializer.refreshWisdomSave()
            initializer.refreshAnimalHandling()
            initializer.refreshInsight()
            initializer.refreshMedicine()
            initializer.refreshPerception()
            initializer.refreshSurvival()
            initializer.refreshSpellAttackBonus()
            refreshContent()
                            },
         statList = characterSheet.wisdomBonus,
         statName = "Wisdom Bonus:",
         updateFunction = updateFunction
      )
   }
   else if(openCharismaDialog){
      StatEntryPopup(
         onDismissRequest = {
            openCharismaDialog = false
            Log.d("debug", "Charisma: ${characterSheet.charisma.sumEntries()}")
            Log.d("debug", "Bonus: ${characterSheet.charismaBonus.sumEntries()}")
            initializer.refreshCharisma()
            initializer.refreshSpellSaveDC()
            initializer.refreshSpellAttackBonus()
            initializer.refreshCharismaSave()
            initializer.refreshDeception()
            initializer.refreshIntimidation()
            initializer.refreshPerformance()
            initializer.refreshPersuasion()
            refreshContent()
         },
         statList = characterSheet.charisma,
         statName = "Charisma:",
         updateFunction = updateFunction
         )
   }
   else if (openCharismaModifierDialog){
      StatEntryPopup(
         onDismissRequest = {
            openCharismaModifierDialog = false
            initializer.refreshCharismaSave()
            initializer.refreshDeception()
            initializer.refreshIntimidation()
            initializer.refreshPerformance()
            initializer.refreshPersuasion()
            initializer.refreshSpellAttackBonus()
            refreshContent()
                            },
         statList = characterSheet.charismaBonus,
         statName = "Charisma Bonus:",
         updateFunction = updateFunction
      )
   }
}

@Preview
@Composable
   fun StatEntryPopup(
      statList: StatList = theoChar.characterSheet.proficiencyBonus, //Statlist from the character sheet
      //rememberedStatList: StatList, //Remembered statlist, cause that's apparently the only easy way to change the character sheet one
      statName: String = "Proficiency Bonus:", //Text value for the popup
      delegateStat: StatList = theoChar.characterSheet.strength, //Statlist to get the summed total
      onDismissRequest: () -> Unit = {},
      baseStatEditable:Boolean = false,
      baseStatString:String = "Base",
      updateFunction: () -> Unit = {}
   ) {
      Dialog(onDismissRequest = onDismissRequest) {
         Card (modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .verticalScroll(state = rememberScrollState()),
            shape = RoundedCornerShape(16.dp)
         ) {
            var rememberedStats by remember {
               mutableStateOf(statList.statList)
            }
            var deletionMode by remember {
               mutableStateOf(false)
            }


            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
               Text(text = statName,
                  textAlign = TextAlign.Center,
                  modifier = Modifier.padding(all = 10.dp))
               Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
               //Display the base stat entry
               if(statList.usesBaseStat){
                  Row(
                     horizontalArrangement = Arrangement.SpaceBetween,
                     verticalAlignment = Alignment.CenterVertically,
                     modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp)
                  ) {

                     var baseStatText by remember {
                        mutableStateOf(statList.baseValue)
                     }


                     BasicTextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = baseStatText.toString(),
                        //Change the readOnly variable based on the property. Some stats are primarily
                        //derived from others (Saving throws, prof. bonus), while others have standalone
                        //base values that can differ on characters (Speed)
                        readOnly = !baseStatEditable,
                        onValueChange = {
                           try {
                              baseStatText = it.toInt()
                           } catch (e: NumberFormatException) {
                              baseStatText = 0
                           }
                           statList.baseValue = baseStatText
                           updateFunction()
                           Log.d("debug", "Current value: ${statList.baseValue}")
                        },
                        modifier = Modifier
                           .padding(horizontal = 5.dp)
                           .sizeIn(minWidth = 30.dp, maxWidth = 30.dp)
                           .border(
                              width = 1.dp,
                              color = Color.Gray,
                              shape = RoundedCornerShape(size = 5.dp)
                           ),
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        singleLine = true
                     )
                     Text(text = baseStatString)
                  }
               Divider(modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp))
               }
               //Display each non-base stat entry
               for (statEntry in rememberedStats){
                  Row (
                     horizontalArrangement = Arrangement.SpaceBetween,
                     verticalAlignment = Alignment.CenterVertically,
                     modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 50.dp, end = 40.dp)
                  ) {

                     var statValue by remember {
                        mutableStateOf(statList.statList[statList.statList.indexOf(statEntry)].stat)
                     }

                     if(deletionMode){
                        IconButton(
                           onClick = {
                              rememberedStats = rememberedStats.minus(statEntry)
                              statList.statList = rememberedStats
                              updateFunction()
                              Log.d("debug", "Statlist: ${statList.statList}")
                           },
                           modifier = Modifier
                              .size(size = 16.dp)
                              .offset(x = -10.dp)
                        ) {
                           Icon(Icons.Filled.Delete, contentDescription = "Delete Class")
                        }
                     }

                     BasicTextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = statValue.toString(),
                        onValueChange = {
                           try {
                              statValue = it.toInt()
                           }
                           catch (e:NumberFormatException) {
                              statValue = 0
                           }
                           statList.statList[statList.statList.indexOf(statEntry)].stat = statValue
                           updateFunction()
                           Log.d("debug", "Current value: ${statList.statList[statList.statList.indexOf(statEntry)]}")
                        },
                        modifier = Modifier
                           .padding(horizontal = 5.dp)
                           .sizeIn(minWidth = 30.dp, maxWidth = 30.dp)
                           .border(
                              width = 1.dp,
                              color = Color.Gray,
                              shape = RoundedCornerShape(size = 5.dp)
                           ),
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        singleLine = true)

                        var sourceValue by remember {
                           mutableStateOf(statList.statList[statList.statList.indexOf(statEntry)].source)
                        }

                     BasicTextField(
                        value = sourceValue.toString(),
                        onValueChange = {
                           sourceValue = it
                           statList.statList[statList.statList.indexOf(statEntry)].source = sourceValue
                           updateFunction()
                           Log.d("debug", "Current value: ${statList.statList[statList.statList.indexOf(statEntry)]}")
                        },
                        modifier = Modifier
                           .padding(horizontal = 5.dp)
                           .sizeIn(minWidth = 30.dp, maxWidth = 100.dp)
                           .border(
                              width = 1.dp,
                              color = Color.Gray,
                              shape = RoundedCornerShape(size = 5.dp)
                           ),
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        singleLine = true)

                  }

                  Divider(modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp))
               }


               Row (
                  modifier = Modifier.fillMaxWidth(0.75f),
                  horizontalArrangement = Arrangement.SpaceAround,
                  verticalAlignment = Alignment.CenterVertically
               ) {
                  //Add and remove buttons, with stat total in center
                  IconButton(
                     onClick = {
                        rememberedStats =
                           rememberedStats.plus(StatEntry())
                        statList.statList = rememberedStats
                        updateFunction()
                        Log.d("debug", "Statlist: ${statList}")
                     },
                     modifier = Modifier
                        .size(size = 20.dp)
                  ) {
                     Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add Class",
                        tint = Color.DarkGray,
                        modifier = Modifier
                           .border(
                              width = 1.dp,
                              color = Color.DarkGray,
                              shape = RoundedCornerShape(size = 5.dp)
                           )
                           .background(color = addColor, shape = RoundedCornerShape(size = 5.dp))
                     )
                  }
                  Column(
                     modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                        .border(
                           width = 2.dp,
                           color = Color.Black,
                           shape = RoundedCornerShape(size = 5.dp)
                        )
                        .padding(top = 5.dp, bottom = 5.dp)
                        .size(width = 100.dp, height = 50.dp),

                     horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                     Text(
                        text = "Total:",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 5.dp)
                     )
                     BasicTextField(
                        value = statList.sumEntries().toString(),
                        onValueChange = { },
                        readOnly = true,
                        modifier = Modifier,
                        textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 20.sp)

                     )

                  }
                  IconButton(
                     onClick = {
                        deletionMode = !deletionMode
                     },
                     modifier = Modifier
                        .size(size = 20.dp)
                  ) {
                     Icon(
                        Icons.Filled.Clear,
                        contentDescription = "Remove Class",
                        tint = Color.DarkGray,
                        modifier = Modifier
                           .border(
                              width = 1.dp,
                              color = Color.DarkGray,
                              shape = RoundedCornerShape(size = 5.dp)
                           )
                           .background(
                              color = removeColor,
                              shape = RoundedCornerShape(size = 5.dp)
                           )
                     )
                  }
               }

            }
         }
      }
   }

@Preview
@Composable
   fun IntStatEntryPopup(
   stat: IntWrapper = theoChar.characterSheet.inspirationPoints,
   statName: String = "Inspiration Points:",
   onDismissRequest: () -> Unit = {},
   editable: Boolean = false,
   updateFunction: () -> Unit = {}
){
   Dialog(onDismissRequest = onDismissRequest) {
      Card (modifier = Modifier
         .fillMaxWidth()
         .height(180.dp)
         .padding(14.dp)
      ) {
         var rememberedInt by remember {
            mutableStateOf(stat.value)
         }

         Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
         ) {
            Text(
               text = statName,
               textAlign = TextAlign.Center,
               modifier = Modifier.padding(all = 10.dp)
            )
            Row(
               modifier = Modifier
                  .fillMaxWidth(0.63f)
                  .border(
                     width = 1.dp,
                     color = Color.Black,
                     shape = RoundedCornerShape(size = 5.dp)
                  ),
               horizontalArrangement = Arrangement.Center,
               verticalAlignment = Alignment.CenterVertically
            ) {
               //Stat display
               BasicTextField(
                  value = rememberedInt.toString(),
                  onValueChange = {
                     try {
                        rememberedInt = it.toInt()
                     } catch (e: NumberFormatException) {
                        rememberedInt = 0
                     }
                     stat.value = rememberedInt
                     updateFunction()
                                  },
                  readOnly = !editable,
                  singleLine = true,
                  textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 20.sp),
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
               )
               Column () {
                  //+ and - signs to increment
                  IconButton(onClick = {
                     rememberedInt++
                     stat.value = rememberedInt
                     updateFunction()
                  },
                     modifier = Modifier
                        .border(
                           width = 1.dp,
                           color = Color.Black,
                           shape = RoundedCornerShape(size = 2.dp)
                        )
                        .size(size = 40.dp)


                  ) {
                     Icon(Icons.Filled.Add, contentDescription = "Increment One")
                  }
                  TextButton(onClick = {
                     rememberedInt--
                     stat.value = rememberedInt
                     updateFunction()
                  },
                     modifier = Modifier
                        .border(
                           width = 1.dp,
                           color = Color.Black,
                           shape = RoundedCornerShape(2.dp)
                        )
                        .size(size = 40.dp)
                  ) {
                     Text(
                        text = "-",
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        modifier = Modifier.offset(y = -6.dp)
                     )
                  }
               }
            }
         }
      }
   }
   }

@Preview
@Composable
fun HealthWindow(
   characterSheet: CharacterSheet = theoChar.characterSheet,
   refresherFlag: Boolean = false,
   updateFunction: () -> Unit = {}
){

   var openHitPointMaxDialog by remember {
      mutableStateOf(false)
   }
   var openHitPointDialog by remember {
      mutableStateOf(false)
   }
   var openTempHitPointDialog by remember {
      mutableStateOf(false)
   }


      Row(
         modifier = Modifier
            .fillMaxWidth()
            .background(
               color = primaryContainerLight,
               shape = RoundedCornerShape(size = 2.dp)
            )
            .border(
               width = 2.dp,
               color = Color.Black,
               shape = RoundedCornerShape(size = 2.dp)
            )
      ) {
         TextButton(
            onClick = { openHitPointMaxDialog = true },
            modifier = Modifier
               .width(100.dp)
               .height(150.dp)
               .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(size = 5.dp))
               .background(color = primaryContainerLight, shape = RoundedCornerShape(size = 5.dp))
         ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
               Text(
                  text = "Maximum Hit Points:",
                  minLines = 2,
                  textAlign = TextAlign.Center,
                  color = Color.Black
               )
               Text(
                  text = characterSheet.hitpointMax.sumEntries().toString(),
                  textAlign = TextAlign.Center,
                  color = Color.Black,
                  fontSize = 20.sp,
                  modifier = Modifier.padding(top = 5.dp)
               )
            }
         }
         Column(modifier = Modifier
            .fillMaxWidth()
            .background(
               color = primaryContainerLight,
               shape = RoundedCornerShape(size = 5.dp)
            )) {
               Row(verticalAlignment = Alignment.CenterVertically) {
                  TextButton(
                     onClick = { openHitPointDialog = true },
                     modifier = Modifier
                        .width(120.dp)
                        .height(95.dp)
                     //.size(size = 100.dp)

                  ) {

                     Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                           text = "Current Hit Points:",
                           minLines = 2,
                           textAlign = TextAlign.Center,
                           color = Color.Black
                        )
                        Text(
                           text = characterSheet.hitPointCurrent.value.toString(),
                           textAlign = TextAlign.Center,
                           color = Color.Black,
                           fontSize = 20.sp,
                           modifier = Modifier.padding(top = 5.dp)
                        )
                     }
                  }
                  Icon(Icons.Filled.Add, null, tint = Color.Gray)
                  TextButton(
                     onClick = { openTempHitPointDialog = true },
                     modifier = Modifier

                  ) {

                     Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                           text = "Temporary Hit Points:",
                           minLines = 2,
                           textAlign = TextAlign.Center,
                           color = Color.Black
                        )
                        Text(
                           text = characterSheet.tempHitPoints.value.toString(),
                           textAlign = TextAlign.Center,
                           color = Color.Black,
                           fontSize = 20.sp,
                           modifier = Modifier.padding(top = 5.dp)
                        )
                     }
                  }
               }
            Divider(modifier = Modifier.padding(horizontal = 15.dp, vertical = 4.dp))
            Row(modifier = Modifier.padding(top = 10.dp)) {
               Text(
                  text = "Hit Dice:",
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(start = 20.dp, end = 10.dp)
               )
               //TextField(value = characterSheet.background, onValueChange = {characterSheet.background = it})
               var hitDiceText by remember {
                  mutableStateOf(characterSheet.hitDice)
               }
               BasicTextField(
                  value = hitDiceText,
                  onValueChange = {
                     hitDiceText = it
                     characterSheet.hitDice = hitDiceText
                     updateFunction()
                     Log.d("debug", "Current Value of Hit Dice: ${characterSheet.hitDice}")
                  },
                  singleLine = true,
                  modifier = Modifier
                     .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(size = 5.dp)
                     )
                     .padding(horizontal = 3.dp)
                     .sizeIn(maxWidth = 75.dp)
               )
            }
         }

   }

   if(openHitPointMaxDialog){
      StatEntryPopup(
         onDismissRequest = {openHitPointMaxDialog = false},
         statList = characterSheet.hitpointMax,
         statName = "Hit Point Maximum:",
         baseStatString = "Constitution Bonus",
         updateFunction = updateFunction
      )
   }
   else if(openHitPointDialog){
      IntStatEntryPopup(
         onDismissRequest = {openHitPointDialog = false},
         stat = characterSheet.hitPointCurrent,
         statName = "Current Hit Points:",
         updateFunction = updateFunction,
         editable = true
      )
   }
   else if(openTempHitPointDialog){
      IntStatEntryPopup(
         onDismissRequest = {openTempHitPointDialog = false},
         stat = characterSheet.tempHitPoints,
         statName = "Temporary Hit Points:",
         editable = true,
         updateFunction = updateFunction
      )
   }

}

@Preview
@Composable
fun SavingThrowWindow(
   characterSheet: CharacterSheet = theoChar.characterSheet,
   initializer: CharacterSheetInitializer = CharacterSheetInitializer(characterSheet),
   refresherFlag: Boolean = false,
   refreshContent: () -> Unit = {},
   updateFunction: () -> Unit = {}
){
   var openStrengthSaveDialog by remember {
      mutableStateOf(false)
   }
   var openDexteritySaveDialog by remember {
      mutableStateOf(false)
   }
   var openConstitutionSaveDialog by remember {
      mutableStateOf(false)
   }
   var openIntelligenceSaveDialog by remember {
      mutableStateOf(false)
   }
   var openWisdomSaveDialog by remember {
      mutableStateOf(false)
   }
   var openCharismaSaveDialog by remember {
      mutableStateOf(false)
   }

   var strengthProficiency by remember {
      mutableStateOf(characterSheet.strengthSavingThrow.proficiency)
   }
   var strengthSave by remember {
      mutableStateOf(characterSheet.strengthSavingThrow.statList)
   }
   var dexterityProficiency by remember {
      mutableStateOf(characterSheet.dexteritySavingThrow.proficiency)
   }
   var dexteritySave by remember {
      mutableStateOf(characterSheet.dexteritySavingThrow.statList)
   }
   var constitutionProficiency by remember {
      mutableStateOf(characterSheet.constitutionSavingThrow.proficiency)
   }
   var constitutionSave by remember {
      mutableStateOf(characterSheet.constitutionSavingThrow.statList)
   }
   var intelligenceProficiency by remember {
      mutableStateOf(characterSheet.intelligenceSavingThrow.proficiency)
   }
   var intelligenceSave by remember {
      mutableStateOf(characterSheet.intelligenceSavingThrow.statList)
   }
   var wisdomProficiency by remember {
      mutableStateOf(characterSheet.wisdomSavingThrow.proficiency)
   }
   var wisdomSave by remember {
      mutableStateOf(characterSheet.wisdomSavingThrow.statList)
   }
   var charismaProficiency by remember {
      mutableStateOf(characterSheet.charismaSavingThrow.proficiency)
   }
   var charismaSave by remember {
      mutableStateOf(characterSheet.charismaSavingThrow.statList)
   }

   Card (
      modifier = Modifier
         .fillMaxWidth()
         .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(size = 5.dp))
   ) {
      Column (horizontalAlignment = Alignment.CenterHorizontally ) {
         Text(
            text = "Saving Throws",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 5.dp)
         )
         Row {

            Row (
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth(0.5f)
            ) {
               IconButton(onClick = {
                  strengthProficiency = !strengthProficiency
                  characterSheet.strengthSavingThrow.proficiency = strengthProficiency
                  Log.d("debug", "Should have toggled. Proficiency: ${strengthProficiency}")
                  initializer.refreshStrengthSave()
                  refreshContent()
                  updateFunction()
               },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (strengthProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)) {
               }
               Text(text = "Strength:",
                  textAlign = TextAlign.Start,
                  fontSize = 18.sp,
                  modifier = Modifier.padding(end = 15.dp)
                  )
               TextButton(
                  onClick = { openStrengthSaveDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = strengthSave.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
            Row (
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth()
            ) {
               IconButton(onClick = {
                  dexterityProficiency = !dexterityProficiency
                  characterSheet.dexteritySavingThrow.proficiency = dexterityProficiency
                  Log.d("debug", "Should have toggled. Proficiency: ${dexterityProficiency}")
                  initializer.refreshDexteritySave()
                  refreshContent()
                  updateFunction()
               },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (dexterityProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) { }
               Text(text = "Dexterity:",
                  textAlign = TextAlign.Start,
                  fontSize = 18.sp,
                  modifier = Modifier.padding(end = 15.dp)
               )
               TextButton(
                  onClick = { openDexteritySaveDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = dexteritySave.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
         }
         Row {
            Row (
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth(0.5f)
            ) {
               IconButton(onClick = {
                  constitutionProficiency = !constitutionProficiency
                  characterSheet.constitutionSavingThrow.proficiency = constitutionProficiency
                  Log.d("debug", "Should have toggled. Proficiency: ${constitutionProficiency}")
                  initializer.refreshConstitutionSave()
                  refreshContent()
                  updateFunction()
               },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (constitutionProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)) {
               }
               Text(text = "Constitution:",
                  textAlign = TextAlign.Start,
                  fontSize = 13.sp,
                  modifier = Modifier.padding(end = 15.dp)
               )
               TextButton(
                  onClick = { openConstitutionSaveDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = constitutionSave.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
            Row (
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth()
            ) {
               IconButton(onClick = {
                  intelligenceProficiency = !intelligenceProficiency
                  characterSheet.intelligenceSavingThrow.proficiency = intelligenceProficiency
                  Log.d("debug", "Should have toggled. Proficiency: ${intelligenceProficiency}")
                  initializer.refreshIntelligenceSave()
                  refreshContent()
                  updateFunction()
               },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (intelligenceProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) { }
               Text(text = "Intelligence:",
                  textAlign = TextAlign.Start,
                  fontSize = 14.sp,
                  modifier = Modifier.padding(end = 15.dp)
               )
               TextButton(
                  onClick = { openIntelligenceSaveDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = intelligenceSave.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
         }
         Row {

            Row (
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth(0.5f)
            ) {
               IconButton(onClick = {
                  wisdomProficiency = !wisdomProficiency
                  characterSheet.wisdomSavingThrow.proficiency = wisdomProficiency
                  Log.d("debug", "Should have toggled. Proficiency: ${wisdomProficiency}")
                  initializer.refreshWisdomSave()
                  refreshContent()
                  updateFunction()
               },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (wisdomProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)) {
               }
               Text(text = "Wisdom:",
                  textAlign = TextAlign.Start,
                  fontSize = 18.sp,
                  modifier = Modifier.padding(end = 15.dp)
               )
               TextButton(
                  onClick = { openWisdomSaveDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = wisdomSave.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
            Row (
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth()
            ) {
               IconButton(onClick = {
                  charismaProficiency = !charismaProficiency
                  characterSheet.charismaSavingThrow.proficiency = charismaProficiency
                  Log.d("debug", "Should have toggled. Proficiency: ${charismaProficiency}")
                  initializer.refreshCharismaSave()
                  refreshContent()
                  updateFunction()
               },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (charismaProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) { }
               Text(text = "Charisma:",
                  textAlign = TextAlign.Start,
                  fontSize = 16.sp,
                  modifier = Modifier.padding(end = 15.dp)
               )
               TextButton(
                  onClick = { openCharismaSaveDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = charismaSave.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
         }
      }
   }
   if(openStrengthSaveDialog){
      StatEntryPopup(
         onDismissRequest = {
            openStrengthSaveDialog = false
            Log.d("debug", "Strength Throw: ${characterSheet.strengthSavingThrow.statList.sumEntries()}")
            initializer.refreshStrengthSave()
         },
         statList = characterSheet.strengthSavingThrow.statList,
         statName = "Strength Saving Throw:",
         updateFunction = updateFunction
         )
   }
   else if(openDexteritySaveDialog){
      StatEntryPopup(
         onDismissRequest = {
            openDexteritySaveDialog = false
            Log.d("debug", "Dex Throw: ${characterSheet.dexteritySavingThrow.statList.sumEntries()}")
            initializer.refreshDexteritySave()
         },
         statList = characterSheet.dexteritySavingThrow.statList,
         statName = "Dexterity Saving Throw:",
         updateFunction = updateFunction
      )
   }
   else if(openConstitutionSaveDialog){
      StatEntryPopup(
         onDismissRequest = {
            openConstitutionSaveDialog = false
            Log.d("debug", "Con Throw: ${characterSheet.constitutionSavingThrow.statList.sumEntries()}")
            initializer.refreshConstitutionSave()
         },
         statList = characterSheet.constitutionSavingThrow.statList,
         statName = "Constitution Saving Throw:",
         updateFunction = updateFunction

      )
   }
   else if(openIntelligenceSaveDialog){
      StatEntryPopup(
         onDismissRequest = {
            openIntelligenceSaveDialog = false
            Log.d("debug", "Int Throw: ${characterSheet.intelligenceSavingThrow.statList.sumEntries()}")
            initializer.refreshIntelligenceSave()
         },
         statList = characterSheet.intelligenceSavingThrow.statList,
         statName = "Intelligence Saving Throw:",
         updateFunction = updateFunction
      )
   }
   else if(openWisdomSaveDialog){
      StatEntryPopup(
         onDismissRequest = {
            openWisdomSaveDialog = false
            Log.d("debug", "Wis Throw: ${characterSheet.wisdomSavingThrow.statList.sumEntries()}")
            initializer.refreshWisdomSave()
         },
         statList = characterSheet.wisdomSavingThrow.statList,
         statName = "Wisdom Saving Throw:",
         updateFunction = updateFunction
      )
   }
   else if(openCharismaSaveDialog){
      StatEntryPopup(
         onDismissRequest = {
            openCharismaSaveDialog = false
            Log.d("debug", "Strength Throw: ${characterSheet.charismaSavingThrow.statList.sumEntries()}")
            initializer.refreshCharismaSave()
         },
         statList = characterSheet.charismaSavingThrow.statList,
         statName = "Charisma Saving Throw:",
         updateFunction = updateFunction
      )
   }
}

@Preview
@Composable
fun SkillWindow(
   characterSheet: CharacterSheet = theoChar.characterSheet,
   initializer: CharacterSheetInitializer = CharacterSheetInitializer(characterSheet),
   refresherFlag: Boolean = false,
   refreshContent: () -> Unit = {},
   updateFunction: () -> Unit = {}
){
   var acrobaticsProficiency by remember {
     mutableStateOf(characterSheet.acrobatics.proficiency)
  }
   var animalHandlingProficiency by remember {
      mutableStateOf(characterSheet.animalhandling.proficiency)
   }
   var arcanaProficiency by remember {
      mutableStateOf(characterSheet.arcana.proficiency)
   }
   var athleticsProficiency by remember {
      mutableStateOf(characterSheet.athletics.proficiency)
   }
   var deceptionProficiency by remember {
      mutableStateOf(characterSheet.deception.proficiency)
   }
   var historyProficiency by remember {
      mutableStateOf(characterSheet.history.proficiency)
   }
   var insightProficiency by remember {
      mutableStateOf(characterSheet.insight.proficiency)
   }
   var investigationProficiency by remember {
      mutableStateOf(characterSheet.investigation.proficiency)
   }
   var intimidationProficiency by remember {
      mutableStateOf(characterSheet.intimidation.proficiency)
   }
   var medicineProficiency by remember {
      mutableStateOf(characterSheet.medicine.proficiency)
   }
   var natureProficiency by remember {
      mutableStateOf(characterSheet.nature.proficiency)
   }
   var perceptionProficiency by remember {
      mutableStateOf(characterSheet.perception.proficiency)
   }
   var performanceProficiency by remember {
      mutableStateOf(characterSheet.performance.proficiency)
   }
   var persuasionProficiency by remember {
      mutableStateOf(characterSheet.persuasion.proficiency)
   }
   var religionProficiency by remember {
      mutableStateOf(characterSheet.religion.proficiency)
   }
   var slightOfHandProficiency by remember {
      mutableStateOf(characterSheet.slightOfHand.proficiency)
   }
   var stealthProficiency by remember {
      mutableStateOf(characterSheet.stealth.proficiency)
   }
   var survivalProficiency by remember {
      mutableStateOf(characterSheet.survival.proficiency)
   }

   var acrobaticsCheck by remember {
      mutableStateOf(characterSheet.acrobatics.statList)
   }
   var animalHandlingCheck by remember {
      mutableStateOf(characterSheet.animalhandling.statList)
   }
   var arcanaCheck by remember {
      mutableStateOf(characterSheet.arcana.statList)
   }
   var athleticsCheck by remember {
      mutableStateOf(characterSheet.athletics.statList)
   }
   var deceptionCheck by remember {
      mutableStateOf(characterSheet.deception.statList)
   }
   var historyCheck by remember {
      mutableStateOf(characterSheet.history.statList)
   }
   var insightCheck by remember {
      mutableStateOf(characterSheet.insight.statList)
   }
   var investigationCheck by remember {
      mutableStateOf(characterSheet.investigation.statList)
   }
   var intimidationCheck by remember {
      mutableStateOf(characterSheet.intimidation.statList)
   }
   var medicineCheck by remember {
      mutableStateOf(characterSheet.medicine.statList)
   }
   var natureCheck by remember {
      mutableStateOf(characterSheet.nature.statList)
   }
   var perceptionCheck by remember {
      mutableStateOf(characterSheet.perception.statList)
   }
   var performanceCheck by remember {
      mutableStateOf(characterSheet.performance.statList)
   }
   var persuasionCheck by remember {
      mutableStateOf(characterSheet.persuasion.statList)
   }
   var religionCheck by remember {
      mutableStateOf(characterSheet.religion.statList)
   }
   var slightOfHandCheck by remember {
      mutableStateOf(characterSheet.slightOfHand.statList)
   }
   var stealthCheck by remember {
      mutableStateOf(characterSheet.stealth.statList)
   }
   var survivalCheck by remember {
      mutableStateOf(characterSheet.survival.statList)
   }
   var passivePerceptionCheck by remember {
      mutableStateOf(characterSheet.passivePerception)
   }

   var openAcrobaticsDialog by remember {
      mutableStateOf(false)
   }
   var openAnimalHandlingDialog by remember {
      mutableStateOf(false)
   }
   var openArcanaDialog by remember {
      mutableStateOf(false)
   }
   var openAthleticsDialog by remember {
      mutableStateOf(false)
   }
   var openDeceptionDialog by remember {
      mutableStateOf(false)
   }
   var openHistoryDialog by remember {
      mutableStateOf(false)
   }
   var openInsightDialog by remember {
      mutableStateOf(false)
   }
   var openIntimidationDialog by remember {
      mutableStateOf(false)
   }
   var openInvestigationDialog by remember {
      mutableStateOf(false)
   }
   var openMedicineDialog by remember {
      mutableStateOf(false)
   }
   var openNatureDialog by remember {
      mutableStateOf(false)
   }
   var openPerceptionDialog by remember {
      mutableStateOf(false)
   }
   var openPerformanceDialog by remember {
      mutableStateOf(false)
   }
   var openPersuasionDialog by remember {
      mutableStateOf(false)
   }
   var openReligionDialog by remember {
      mutableStateOf(false)
   }
   var openSlightOfHandDialog by remember {
      mutableStateOf(false)
   }
   var openStealthDialog by remember {
      mutableStateOf(false)
   }
   var openSurvivalDialog by remember {
      mutableStateOf(false)
   }
   var openPassivePerceptionDialog by remember {
      mutableStateOf(false)
   }

   Card (
      modifier = Modifier
         .fillMaxWidth()
         .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(size = 5.dp))
   ){
      Column (horizontalAlignment = Alignment.CenterHorizontally ) {
         Text(
            text = "Skills",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 5.dp)
         )
         Row {
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth(0.5f)
            ) {
               IconButton(
                  onClick = {
                     acrobaticsProficiency = !acrobaticsProficiency
                     characterSheet.acrobatics.proficiency = acrobaticsProficiency
                     Log.d("debug", "Should have toggled. Proficiency: ${acrobaticsProficiency}")
                     initializer.refreshAcrobatics()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (acrobaticsProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) {
               }
               Text(
                  text = "Acrobatics:",
                  textAlign = TextAlign.Start,
                  fontSize = 18.sp,
                  modifier = Modifier.padding(end = 15.dp)
               )
               TextButton(
                  onClick = { openAcrobaticsDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(acrobaticsCheck.sumEntries() > 0) "+" + acrobaticsCheck.sumEntries().toString()
                             else acrobaticsCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth()
            ) {
               IconButton(
                  onClick = {
                     animalHandlingProficiency = !animalHandlingProficiency
                     characterSheet.animalhandling.proficiency = animalHandlingProficiency
                     Log.d(
                        "debug",
                        "Should have toggled. Proficiency: ${animalHandlingProficiency}"
                     )
                     initializer.refreshAnimalHandling()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (animalHandlingProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) { }
               Text(
                  text = "Animal Handling:",
                  textAlign = TextAlign.Start,
                  fontSize = 12.sp,
                  modifier = Modifier.padding(end = 15.dp)
               )
               TextButton(
                  onClick = { openAnimalHandlingDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(animalHandlingCheck.sumEntries() > 0) "+" + animalHandlingCheck.sumEntries().toString()
                     else animalHandlingCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
         }
         Row {
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth(0.5f)
            ) {
               IconButton(
                  onClick = {
                     arcanaProficiency = !arcanaProficiency
                     characterSheet.arcana.proficiency = arcanaProficiency
                     Log.d("debug", "Should have toggled. Proficiency: ${arcanaProficiency}")
                     initializer.refreshArcana()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (arcanaProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) {
               }
               Text(
                  text = "Arcana:",
                  textAlign = TextAlign.Start,
                  fontSize = 18.sp,
                  modifier = Modifier.padding(end = 44.dp)
               )
               TextButton(
                  onClick = { openArcanaDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(arcanaCheck.sumEntries() > 0) "+" + arcanaCheck.sumEntries().toString()
                     else arcanaCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth()
            ) {
               IconButton(
                  onClick = {
                     athleticsProficiency = !athleticsProficiency
                     characterSheet.athletics.proficiency = athleticsProficiency
                     Log.d(
                        "debug",
                        "Should have toggled. Proficiency: ${athleticsProficiency}"
                     )
                     initializer.refreshAthletics()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (athleticsProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) { }
               Text(
                  text = "Athletics:",
                  textAlign = TextAlign.Start,
                  fontSize = 18.sp,
                  modifier = Modifier.padding(end = 32.dp)
               )
               TextButton(
                  onClick = { openAthleticsDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(athleticsCheck.sumEntries() > 0) "+" + athleticsCheck.sumEntries().toString()
                     else athleticsCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
         }
         Row {
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth(0.5f)
            ) {
               IconButton(
                  onClick = {
                     deceptionProficiency = !deceptionProficiency
                     characterSheet.deception.proficiency = deceptionProficiency
                     Log.d("debug", "Should have toggled. Proficiency: ${deceptionProficiency}")
                     initializer.refreshDeception()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (deceptionProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) {
               }
               Text(
                  text = "Deception:",
                  textAlign = TextAlign.Start,
                  fontSize = 18.sp,
                  modifier = Modifier.padding(end = 20.dp)
               )
               TextButton(
                  onClick = { openDeceptionDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(deceptionCheck.sumEntries() > 0) "+" + deceptionCheck.sumEntries().toString()
                     else deceptionCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth()
            ) {
               IconButton(
                  onClick = {
                     historyProficiency = !historyProficiency
                     characterSheet.history.proficiency = historyProficiency
                     Log.d(
                        "debug",
                        "Should have toggled. Proficiency: ${historyProficiency}"
                     )
                     initializer.refreshHistory()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (historyProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) { }
               Text(
                  text = "History:",
                  textAlign = TextAlign.Start,
                  fontSize = 18.sp,
                  modifier = Modifier.padding(end = 44.dp)
               )
               TextButton(
                  onClick = { openHistoryDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(historyCheck.sumEntries() > 0) "+" + historyCheck.sumEntries().toString()
                     else historyCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
         }
         Row {
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth(0.5f)
            ) {
               IconButton(
                  onClick = {
                     insightProficiency = !insightProficiency
                     characterSheet.insight.proficiency = insightProficiency
                     Log.d("debug", "Should have toggled. Proficiency: ${insightProficiency}")
                     initializer.refreshInsight()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (insightProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) {
               }
               Text(
                  text = "Insight:",
                  textAlign = TextAlign.Start,
                  fontSize = 18.sp,
                  modifier = Modifier.padding(end = 45.dp)
               )
               TextButton(
                  onClick = { openInsightDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(insightCheck.sumEntries() > 0) "+" + insightCheck.sumEntries().toString()
                     else insightCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth()
            ) {
               IconButton(
                  onClick = {
                     intimidationProficiency = !intimidationProficiency
                     characterSheet.intimidation.proficiency = intimidationProficiency
                     Log.d(
                        "debug",
                        "Should have toggled. Proficiency: ${intimidationProficiency}"
                     )
                     initializer.refreshIntimidation()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (intimidationProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) { }
               Text(
                  text = "Intimidation:",
                  textAlign = TextAlign.Start,
                  fontSize = 17.sp,
                  modifier = Modifier.padding(end = 13.dp)
               )
               TextButton(
                  onClick = { openIntimidationDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(intimidationCheck.sumEntries() > 0) "+" + intimidationCheck.sumEntries().toString()
                     else intimidationCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
         }
         Row {
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth(0.5f)
            ) {
               IconButton(
                  onClick = {
                     investigationProficiency = !investigationProficiency
                     characterSheet.investigation.proficiency = investigationProficiency
                     Log.d("debug", "Should have toggled. Proficiency: ${investigationProficiency}")
                     initializer.refreshInvestigation()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (investigationProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) {
               }
               Text(
                  text = "Investigation:",
                  textAlign = TextAlign.Start,
                  fontSize = 15.sp,
                  modifier = Modifier.padding(end = 14.dp)
               )
               TextButton(
                  onClick = { openInvestigationDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(investigationCheck.sumEntries() > 0) "+" + investigationCheck.sumEntries().toString()
                     else investigationCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth()
            ) {
               IconButton(
                  onClick = {
                     medicineProficiency = !medicineProficiency
                     characterSheet.medicine.proficiency = medicineProficiency
                     Log.d(
                        "debug",
                        "Should have toggled. Proficiency: ${medicineProficiency}"
                     )
                     initializer.refreshMedicine()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (medicineProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) { }
               Text(
                  text = "Medicine:",
                  textAlign = TextAlign.Start,
                  fontSize = 18.sp,
                  modifier = Modifier.padding(end = 27.dp)
               )
               TextButton(
                  onClick = { openMedicineDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(medicineCheck.sumEntries() > 0) "+" + medicineCheck.sumEntries().toString()
                     else medicineCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
         }
         Row {
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth(0.5f)
            ) {
               IconButton(
                  onClick = {
                     natureProficiency = !natureProficiency
                     characterSheet.nature.proficiency = natureProficiency
                     Log.d("debug", "Should have toggled. Proficiency: ${natureProficiency}")
                     initializer.refreshNature()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (natureProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) {
               }
               Text(
                  text = "Nature:",
                  textAlign = TextAlign.Start,
                  fontSize = 18.sp,
                  modifier = Modifier.padding(end = 44.dp)
               )
               TextButton(
                  onClick = { openNatureDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(natureCheck.sumEntries() > 0) "+" + natureCheck.sumEntries().toString()
                     else natureCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth()
            ) {
               IconButton(
                  onClick = {
                     perceptionProficiency = !perceptionProficiency
                     characterSheet.perception.proficiency = perceptionProficiency
                     Log.d(
                        "debug",
                        "Should have toggled. Proficiency: ${perceptionProficiency}"
                     )
                     initializer.refreshPerception()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (perceptionProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) { }
               Text(
                  text = "Perception:",
                  textAlign = TextAlign.Start,
                  fontSize = 17.sp,
                  modifier = Modifier.padding(end = 20.dp)
               )
               TextButton(
                  onClick = { openPerceptionDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(perceptionCheck.sumEntries() > 0) "+" + perceptionCheck.sumEntries().toString()
                     else perceptionCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
         }
         Row {
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth(0.5f)
            ) {
               IconButton(
                  onClick = {
                     performanceProficiency = !performanceProficiency
                     characterSheet.performance.proficiency = performanceProficiency
                     Log.d("debug", "Should have toggled. Proficiency: ${performanceProficiency}")
                     initializer.refreshPerformance()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (performanceProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) {
               }
               Text(
                  text = "Performance:",
                  textAlign = TextAlign.Start,
                  fontSize = 15.sp,
                  modifier = Modifier.padding(end = 12.dp)
               )
               TextButton(
                  onClick = { openPerformanceDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(performanceCheck.sumEntries() > 0) "+" + performanceCheck.sumEntries().toString()
                     else performanceCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth()
            ) {
               IconButton(
                  onClick = {
                     persuasionProficiency = !persuasionProficiency
                     characterSheet.persuasion.proficiency = persuasionProficiency
                     Log.d("debug", "Should have toggled. Proficiency: ${persuasionProficiency}")
                     initializer.refreshPersuasion()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (persuasionProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) {
               }
               Text(
                  text = "Persuasion:",
                  textAlign = TextAlign.Start,
                  fontSize = 16.sp,
                  modifier = Modifier.padding(end = 20.dp)
               )
               TextButton(
                  onClick = { openPersuasionDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(persuasionCheck.sumEntries() > 0) "+" + persuasionCheck.sumEntries().toString()
                     else persuasionCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
         }
         Row {
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth(0.5f)
            ) {
               IconButton(
                  onClick = {
                     religionProficiency = !religionProficiency
                     characterSheet.religion.proficiency = religionProficiency
                     Log.d("debug", "Should have toggled. Proficiency: ${religionProficiency}")
                     initializer.refreshReligion()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (religionProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) {
               }
               Text(
                  text = "Religion:",
                  textAlign = TextAlign.Start,
                  fontSize = 18.sp,
                  modifier = Modifier.padding(end = 34.dp)
               )
               TextButton(
                  onClick = { openReligionDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(religionCheck.sumEntries() > 0) "+" + religionCheck.sumEntries().toString()
                     else religionCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth()
            ) {
               IconButton(
                  onClick = {
                     slightOfHandProficiency = !slightOfHandProficiency
                     characterSheet.slightOfHand.proficiency = slightOfHandProficiency
                     Log.d("debug", "Should have toggled. Proficiency: ${slightOfHandProficiency}")
                     initializer.refreshSlightOfHand()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (slightOfHandProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) {
               }
               Text(
                  text = "Slight of Hand:",
                  textAlign = TextAlign.Start,
                  fontSize = 13.sp,
                  modifier = Modifier.padding(end = 20.dp)
               )
               TextButton(
                  onClick = { openSlightOfHandDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(slightOfHandCheck.sumEntries() > 0) "+" + slightOfHandCheck.sumEntries().toString()
                     else slightOfHandCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
         }
         Row {
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth(0.5f)
            ) {
               IconButton(
                  onClick = {
                     stealthProficiency = !stealthProficiency
                     characterSheet.stealth.proficiency = stealthProficiency
                     Log.d("debug", "Should have toggled. Proficiency: ${stealthProficiency}")
                     initializer.refreshStealth()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (stealthProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) {
               }
               Text(
                  text = "Stealth:",
                  textAlign = TextAlign.Start,
                  fontSize = 18.sp,
                  modifier = Modifier.padding(end = 42.dp)
               )
               TextButton(
                  onClick = { openStealthDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(stealthCheck.sumEntries() > 0) "+" + stealthCheck.sumEntries().toString()
                     else stealthCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier
                  .border(width = 1.dp, color = Color.LightGray)
                  .fillMaxWidth()
            ) {
               IconButton(
                  onClick = {
                     survivalProficiency = !survivalProficiency
                     characterSheet.survival.proficiency = survivalProficiency
                     Log.d("debug", "Should have toggled. Proficiency: ${survivalProficiency}")
                     initializer.refreshSurvival()
                     updateFunction()
                     refreshContent()
                  },
                  modifier = Modifier
                     .padding(horizontal = 7.dp)
                     .background(
                        color = if (survivalProficiency) Color.LightGray else Color.White,
                        shape = CircleShape
                     )
                     .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                     .size(size = 20.dp)
               ) {
               }
               Text(
                  text = "Survival:",
                  textAlign = TextAlign.Start,
                  fontSize = 18.sp,
                  modifier = Modifier.padding(end = 36.dp)
               )
               TextButton(
                  onClick = { openSurvivalDialog = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier.offset(x = -10.dp),
                  colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
               ) {
                  Text(
                     text = if(survivalCheck.sumEntries() > 0) "+" + survivalCheck.sumEntries().toString()
                     else survivalCheck.sumEntries().toString(),
                     color = Color.Black,
                     fontSize = 16.sp,
                     maxLines = 1
                  )
               }
            }
         }
         Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
            modifier = Modifier
               .border(width = 1.dp, color = Color.LightGray)
               .fillMaxWidth()
         ) {
            Text(
               text = "Passive Perception:",
               textAlign = TextAlign.Start,
               fontSize = 18.sp,
               modifier = Modifier.padding(end = 36.dp)
            )
            TextButton(
               onClick = { openPassivePerceptionDialog = true },
               contentPadding = PaddingValues(all = 0.dp),
               modifier = Modifier.offset(x = -10.dp),
               colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
            ) {
               Text(
                  text = passivePerceptionCheck.sumEntries().toString(),
                  color = Color.Black,
                  fontSize = 16.sp,
                  maxLines = 1
               )
            }
         }
      }
   }
   if(openAcrobaticsDialog){
      StatEntryPopup(
         onDismissRequest = {
            openAcrobaticsDialog = false
            Log.d("debug", "Acrobatics: ${characterSheet.acrobatics.statList.sumEntries()}")
            initializer.refreshAcrobatics()
         },
         statList = characterSheet.acrobatics.statList,
         statName = "Acrobatics:",
         updateFunction = updateFunction
      )
   }
   else if(openAnimalHandlingDialog){
      StatEntryPopup(
         onDismissRequest = {
            openAnimalHandlingDialog = false
            Log.d("debug", "animal handling: ${characterSheet.animalhandling.statList.sumEntries()}")
            initializer.refreshAnimalHandling()
         },
         statList = characterSheet.animalhandling.statList,
         statName = "Animal Handling:",
         updateFunction = updateFunction
      )
   }
   else if(openArcanaDialog){
      StatEntryPopup(
         onDismissRequest = {
            openArcanaDialog = false
            Log.d("debug", "arcana: ${characterSheet.arcana.statList.sumEntries()}")
            initializer.refreshArcana()
         },
         statList = characterSheet.arcana.statList,
         statName = "Arcana:",
         updateFunction = updateFunction
      )
   }
   else if(openAthleticsDialog){
      StatEntryPopup(
         onDismissRequest = {
            openAthleticsDialog = false
            Log.d("debug", "Athletics: ${characterSheet.athletics.statList.sumEntries()}")
            initializer.refreshAthletics()
         },
         statList = characterSheet.athletics.statList,
         statName = "Athletics:",
         updateFunction = updateFunction
      )
   }
   else if(openDeceptionDialog){
      StatEntryPopup(
         onDismissRequest = {
            openDeceptionDialog = false
            Log.d("debug", "Deception: ${characterSheet.deception.statList.sumEntries()}")
            initializer.refreshDeception()
         },
         statList = characterSheet.deception.statList,
         statName = "Deception:",
         updateFunction = updateFunction
      )
   }
   else if(openHistoryDialog){
      StatEntryPopup(
         onDismissRequest = {
            openHistoryDialog = false
            Log.d("debug", "History: ${characterSheet.history.statList.sumEntries()}")
            initializer.refreshHistory()
         },
         statList = characterSheet.history.statList,
         statName = "History:",
         updateFunction = updateFunction
      )
   }
   else if(openInsightDialog){
      StatEntryPopup(
         onDismissRequest = {
            openInsightDialog = false
            Log.d("debug", "Insight: ${characterSheet.insight.statList.sumEntries()}")
            initializer.refreshInsight()
         },
         statList = characterSheet.insight.statList,
         statName = "Insight:",
         updateFunction = updateFunction
      )
   }
   else if(openIntimidationDialog){
      StatEntryPopup(
         onDismissRequest = {
            openIntimidationDialog = false
            Log.d("debug", "Intimidation: ${characterSheet.intimidation.statList.sumEntries()}")
            initializer.refreshIntimidation()
         },
         statList = characterSheet.intimidation.statList,
         statName = "Intimidation:",
         updateFunction = updateFunction
      )
   }
   else if(openInvestigationDialog){
      StatEntryPopup(
         onDismissRequest = {
            openInvestigationDialog = false
            Log.d("debug", "investigation: ${characterSheet.investigation.statList.sumEntries()}")
            initializer.refreshInvestigation()
         },
         statList = characterSheet.investigation.statList,
         statName = "Investigation:",
         updateFunction = updateFunction
      )
   }
   else if(openMedicineDialog) {
      StatEntryPopup(
         onDismissRequest = {
            openMedicineDialog = false
            Log.d("debug", "animal handling: ${characterSheet.medicine.statList.sumEntries()}")
            initializer.refreshMedicine()
         },
         statList = characterSheet.medicine.statList,
         statName = "Medicine:",
         updateFunction = updateFunction
      )
   }
   else if(openNatureDialog){
         StatEntryPopup(
            onDismissRequest = {
               openNatureDialog = false
               Log.d("debug", "Nature: ${characterSheet.nature.statList.sumEntries()}")
               initializer.refreshNature()
            },
            statList = characterSheet.nature.statList,
            statName = "Nature:",
            updateFunction = updateFunction
         )
      }
   else if(openPerceptionDialog){
      StatEntryPopup(
         onDismissRequest = {
            openPerceptionDialog = false
            Log.d("debug", "Perception: ${characterSheet.perception.statList.sumEntries()}")
            initializer.refreshPerception()
         },
         statList = characterSheet.perception.statList,
         statName = "Perception:",
         updateFunction = updateFunction
      )
   }
   else if(openPerformanceDialog){
      StatEntryPopup(
         onDismissRequest = {
            openPerformanceDialog = false
            Log.d("debug", "Performance: ${characterSheet.performance.statList.sumEntries()}")
            initializer.refreshPerformance()
         },
         statList = characterSheet.performance.statList,
         statName = "Performance:",
         updateFunction = updateFunction
      )
   }
   else if(openPersuasionDialog){
      StatEntryPopup(
         onDismissRequest = {
            openPersuasionDialog = false
            Log.d("debug", "Persuasion: ${characterSheet.persuasion.statList.sumEntries()}")
            initializer.refreshPersuasion()
         },
         statList = characterSheet.persuasion.statList,
         statName = "Persuasion:",
         updateFunction = updateFunction
      )
   }
   else if(openReligionDialog){
      StatEntryPopup(
         onDismissRequest = {
            openReligionDialog = false
            Log.d("debug", "Religion: ${characterSheet.religion.statList.sumEntries()}")
            initializer.refreshReligion()
         },
         statList = characterSheet.religion.statList,
         statName = "Religion:",
         updateFunction = updateFunction
      )
   }else if(openSlightOfHandDialog){
      StatEntryPopup(
         onDismissRequest = {
            openSlightOfHandDialog = false
            Log.d("debug", "Slight of Hand: ${characterSheet.slightOfHand.statList.sumEntries()}")
            initializer.refreshSlightOfHand()
         },
         statList = characterSheet.slightOfHand.statList,
         statName = "Slight of Hand:",
         updateFunction = updateFunction
      )
   }
   else if(openStealthDialog){
      StatEntryPopup(
         onDismissRequest = {
            openStealthDialog = false
            Log.d("debug", "Stealth: ${characterSheet.stealth.statList.sumEntries()}")
            initializer.refreshStealth()
         },
         statList = characterSheet.stealth.statList,
         statName = "Stealth:",
         updateFunction = updateFunction
      )
   }
   else if(openSurvivalDialog){
      StatEntryPopup(
         onDismissRequest = {
            openSurvivalDialog = false
            Log.d("debug", "Survival: ${characterSheet.survival.statList.sumEntries()}")
            initializer.refreshSurvival()
         },
         statList = characterSheet.survival.statList,
         statName = "Survival:",
         updateFunction = updateFunction
      )
   }
   else if(openPassivePerceptionDialog){
      StatEntryPopup(
         onDismissRequest = {
            openPassivePerceptionDialog = false
            Log.d("debug", "Passive perception: ${characterSheet.passivePerception.sumEntries()}")
            initializer.refreshPassivePerception()
         },
         statList = characterSheet.passivePerception,
         statName = "Passive Perception:",
         updateFunction = updateFunction
      )
   }
}

@Preview
@Composable
fun AttacksWindow(
   characterSheet: CharacterSheet = theoChar.characterSheet,
   initializer: CharacterSheetInitializer = CharacterSheetInitializer((characterSheet)),
   refresherFlag: Boolean = false,
   refreshContent: () -> Unit = {},
   updateFunction: () -> Unit = {}
) {
   Card(
      modifier = Modifier
         .fillMaxWidth()
         .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(size = 5.dp)),
      backgroundColor = primaryContainerLight
   ) {

      var deletionMode by remember {
         mutableStateOf(false)
      }

      Column {
         Row (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.Center) {
            Text(text = "Attacks")
         }
         Row(
            modifier = Modifier
               .fillMaxWidth()
               .padding(horizontal = 20.dp, vertical = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
         ) {
            Text(text = "Name:")
            Text(text = "Attack Bonus:")
            Text(text = "Damage:")
         }
      Divider(color = Color.Black,
         thickness = 1.dp,
         modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))

         for (attack in characterSheet.attacks){
            var nameText by remember {
               mutableStateOf(attack.name)
            }
            var damageText by remember {
               mutableStateOf(attack.damage)
            }
            var openDamageDialog by remember {
               mutableStateOf(false)
            }



            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
               if(deletionMode){
                  IconButton(
                     onClick = {
                        characterSheet.attacks = characterSheet.attacks.minus(attack)
                        Log.d("debug", "Attacks: ${characterSheet.attacks.toString()}")
                        updateFunction()
                        refreshContent()
                     },
                     modifier = Modifier
                        .padding(start = 15.dp)
                        .size(size = 16.dp)
                  ) {
                     Icon(Icons.Filled.Delete, contentDescription = "Delete Class")
                  }
               }

               BasicTextField(
                  value = nameText,
                  onValueChange = {
                     nameText = it
                     attack.name = nameText
                     updateFunction()
               },
                  singleLine = true,
                  textStyle = TextStyle(textAlign = TextAlign.Center),
                  modifier = Modifier
                     .padding(start = 10.dp)
                     .background(color = Color.LightGray, shape = RoundedCornerShape(size = 5.dp))
                     .height(20.dp)
               )

               TextButton(onClick = { openDamageDialog = true },
                  contentPadding = PaddingValues(),
                  modifier = Modifier
                     .background(color = Color.LightGray, shape = RoundedCornerShape(size = 5.dp))
                     .height(20.dp)
               ) {
                 Text(
                    text = if(attack.attackbonus.sumEntries() > 0) "+" + attack.attackbonus.sumEntries().toString()
                    else attack.attackbonus.sumEntries().toString(),
                    color = Color.Black,
                    maxLines = 1
                 )
               }

               BasicTextField(
                  value = damageText,
                  onValueChange = {
                     damageText = it
                     attack.damage = damageText
                     updateFunction()
                  },
                  singleLine = true,
                  textStyle = TextStyle(textAlign = TextAlign.Center),
                  modifier = Modifier
                     .padding(end = 10.dp)
                     .background(color = Color.LightGray, shape = RoundedCornerShape(size = 5.dp))
                     .height(20.dp)

               )
            }
            Divider(color = Color.LightGray,
               thickness = 1.dp,
               modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))

            if(openDamageDialog){
               StatEntryPopup(
                  onDismissRequest = {openDamageDialog = false},
                  statList = attack.attackbonus,
                  statName = "Attack Bonus:",
                  baseStatEditable = true,
                  updateFunction = updateFunction
               )
            }

         }
         Row (modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp), horizontalArrangement = Arrangement.SpaceEvenly ) {
            IconButton(
               onClick = {
                  characterSheet.attacks =
                     characterSheet.attacks.plus(Attack())
                  //characterSheet.characterClass = characterClassState
                  Log.d("debug", "Attacks: ${characterSheet.attacks.toString()}")
                  updateFunction()
                  refreshContent()
               },
               modifier = Modifier
                  .size(size = 20.dp)
            ) {
               Icon(
                  Icons.Filled.Add,
                  contentDescription = "Add Class",
                  tint = Color.DarkGray,
                  modifier = Modifier
                     .border(
                        width = 1.dp,
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(size = 5.dp)
                     )
                     .background(color = addColor, shape = RoundedCornerShape(size = 5.dp))
               )
            }
            IconButton(
               onClick = {
                  deletionMode = !deletionMode
               },
               modifier = Modifier
                  .size(size = 20.dp)
            ) {
               Icon(
                  Icons.Filled.Clear,
                  contentDescription = "Remove Class",
                  tint = Color.DarkGray,
                  modifier = Modifier
                     .border(
                        width = 1.dp,
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(size = 5.dp)
                     )
                     .background(color = removeColor, shape = RoundedCornerShape(size = 5.dp))
               )
            }
         }

      }
   }
}

@Preview
@Composable
fun StringWindow(
   characterSheet: CharacterSheet = theoChar.characterSheet,
   stringList: StringListWrapper = theoChar.characterSheet.equipment,
   refresherFlag: Boolean = false,
   refreshContent: () -> Unit = {},
   title:String = "",
   updateFunction: () -> Unit = {}
){

   var deletionMode by remember {
      mutableStateOf(false)
   }

   Card (modifier = Modifier
      .fillMaxWidth()
      .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(size = 5.dp)),
      backgroundColor = primaryContainerLight
   ) {
      Column(modifier = Modifier.fillMaxWidth()) {
         Row (modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp), horizontalArrangement = Arrangement.Center ) {
            Text(text = title, textAlign = TextAlign.Center)
         }
         Divider(modifier = Modifier.padding(horizontal = 10.dp), color = Color.Black)
         for(entry in stringList.stringList){

           Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
               if (deletionMode) {
                  IconButton(
                     onClick = {
                        stringList.stringList = stringList.stringList.minus(entry)
                        Log.d("debug", "${title}: ${stringList.toString()}")
                        updateFunction()
                        refreshContent()
                     },
                     modifier = Modifier
                        .padding(start = 15.dp)
                        .size(size = 16.dp)
                  ) {
                     Icon(Icons.Filled.Delete, contentDescription = "Delete ${title}")
                  }
               }

               var text by remember {
                  mutableStateOf(entry.value)
               }

               BasicTextField(
                  value = text, onValueChange = {
                     text = it
                     entry.value = text
                     updateFunction()
                     Log.d(
                        "debug",
                        "Current string value: ${
                           stringList.stringList[stringList.stringList.indexOf(entry)]
                        }"
                     )
                  },
                  modifier = Modifier
                     .padding(horizontal = 20.dp, vertical = 5.dp)
                     .background(color = Color.LightGray, shape = RoundedCornerShape(size = 5.dp))
                     .padding(horizontal = 5.dp)
               )

            }
            Divider(modifier = Modifier.padding(horizontal = 15.dp), color = Color.LightGray)
         }
         Row (modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, top = 5.dp), horizontalArrangement = Arrangement.SpaceEvenly ) {
            IconButton(
               onClick = {
                  stringList.stringList =
                     stringList.stringList.plus(StringWrapper())
                  updateFunction()

                  Log.d("debug", "StringWrapper: ${stringList.toString()}")
                  refreshContent()
               },
               modifier = Modifier
                  .size(size = 20.dp)
            ) {
               Icon(
                  Icons.Filled.Add,
                  contentDescription = "Add Class",
                  tint = Color.DarkGray,
                  modifier = Modifier
                     .border(
                        width = 1.dp,
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(size = 5.dp)
                     )
                     .background(color = addColor, shape = RoundedCornerShape(size = 5.dp))
               )
            }
            IconButton(
               onClick = {
                  deletionMode = !deletionMode
               },
               modifier = Modifier
                  .size(size = 20.dp)
            ) {
               Icon(
                  Icons.Filled.Clear,
                  contentDescription = "Remove Class",
                  tint = Color.DarkGray,
                  modifier = Modifier
                     .border(
                        width = 1.dp,
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(size = 5.dp)
                     )
                     .background(color = removeColor, shape = RoundedCornerShape(size = 5.dp))
               )
            }
         }
      }

   }
}

@Preview
@Composable
fun SpellInfoWindow(
    characterSheet: CharacterSheet = theoChar.characterSheet,
    refresherFlag: Boolean = false,
    refreshContent: () -> Unit = {},
    initializer: CharacterSheetInitializer = CharacterSheetInitializer(characterSheet),
    updateFunction: () -> Unit = {}
){
    Card (modifier = Modifier
       .fillMaxWidth()
       .padding(start = 10.dp, end = 10.dp, top = 10.dp)
       .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(size = 5.dp))

    ) {
        Column {
           Row (modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 5.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
           ) {
               Text(text = "Spellcasting Stats:")
           }
           Divider(modifier = Modifier.padding(horizontal = 10.dp), color = Color.Black)
            Row (
               modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 10.dp),
               horizontalArrangement = Arrangement.Center,
               verticalAlignment = Alignment.CenterVertically
            ) {

               var expanded by remember {
                  mutableStateOf(false)
               }
               var spellCastClassText by remember {
                  mutableStateOf(characterSheet.spellCastingClass)
               }

               TextButton(
                  onClick = { expanded = true },
                  contentPadding = PaddingValues(all = 0.dp),
                  modifier = Modifier
                     .padding(horizontal = 5.dp)
                     .size(25.dp)
               ) {
                  Icon(imageVector = Icons.Filled.Menu, contentDescription = null, tint = Color.Black)
               }
                Text(text = "Spellcasting Class: ")
                Text(
                   text = characterSheet.spellCastingClass.toString().lowercase().capitalize(),
                   modifier = Modifier
                      .background(color = Color.LightGray, shape = RoundedCornerShape(size = 5.dp))
                      .padding(horizontal = 5.dp, vertical = 2.dp)
                )

               DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                  //For loop creates a menu item in the dropdown for each class value
                  for (charClass in CharacterClass.entries){
                     DropdownMenuItem(text = { Text(text = charClass.name) }, onClick = {
                        spellCastClassText = charClass
                        characterSheet.spellCastingClass = spellCastClassText
                        updateFunction()
                        expanded = false
                     })
                  }
               }
            }
            Divider(modifier = Modifier.padding(horizontal = 10.dp), color = Color.LightGray)

           Row (
              modifier = Modifier
                 .fillMaxWidth()
                 .padding(vertical = 10.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
           ) {

              var expanded by remember {
                 mutableStateOf(false)
              }
              var spellAbilityText by remember {
                 mutableStateOf(characterSheet.spellCastingAbility)
              }

              TextButton(
                 onClick = { expanded = true },
                 contentPadding = PaddingValues(all = 0.dp),
                 modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .size(25.dp)
              ) {
                 Icon(imageVector = Icons.Filled.Menu, contentDescription = null, tint = Color.Black)
              }
              Text(text = "Spellcasting Ability: ")
              Text(text = characterSheet.spellCastingAbility.toString().lowercase().capitalize(),
                       modifier = Modifier
                          .background(
                             color = Color.LightGray,
                             shape = RoundedCornerShape(size = 5.dp)
                          )
                          .padding(horizontal = 5.dp, vertical = 2.dp)
              )

              DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                 //For loop creates a menu item in the dropdown for each class value
                 for (ability in CharacterStat.entries){
                    DropdownMenuItem(text = { Text(text = ability.name) }, onClick = {
                       spellAbilityText = ability
                       characterSheet.spellCastingAbility = spellAbilityText
                       initializer.refreshSpellSaveDC()
                       initializer.refreshSpellAttackBonus()
                       updateFunction()
                       refreshContent()
                       expanded = false
                    })
                 }
              }
           }
           Divider(modifier = Modifier.padding(horizontal = 10.dp), color = Color.LightGray)

           Row (
              modifier = Modifier
                 .fillMaxWidth()
                 .padding(vertical = 10.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
           ){
              var spellSaveDCText by remember {
                 mutableStateOf(characterSheet.spellSaveDC)
              }
              var spellAttackBonusText by remember {
                 mutableStateOf(characterSheet.spellAttackBonus)
              }

              var openSpellSaveDialog by remember {
                  mutableStateOf(false)
              }
              var openSpellAttackDialog by remember {
                 mutableStateOf(false)
              }

              Text(
                 text = "Spell Save DC:",
                 textAlign = TextAlign.Start,
                 fontSize = 12.sp,
                 modifier = Modifier.padding(end = 15.dp, start = 5.dp)
              )
              TextButton(
                 onClick = { openSpellSaveDialog = true },
                 contentPadding = PaddingValues(all = 0.dp),
                 modifier = Modifier.offset(x = -10.dp),
                 colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
              ) {
                 Text(
                    text = spellSaveDCText.sumEntries().toString(),
                    color = Color.Black,
                    fontSize = 16.sp,
                    maxLines = 1
                 )
              }
              Text(
                 text = "Spell Attack Bonus:",
                 textAlign = TextAlign.Start,
                 fontSize = 12 .sp,
                 modifier = Modifier.padding(end = 15.dp)
              )
              TextButton(
                 onClick = { openSpellAttackDialog = true },
                 contentPadding = PaddingValues(all = 0.dp),
                 modifier = Modifier.offset(x = -10.dp),
                 colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
              ) {
                 Text(
                    text = if(spellAttackBonusText.sumEntries() > 0) "+" + spellAttackBonusText.sumEntries().toString()
                    else spellAttackBonusText.sumEntries().toString(),
                    color = Color.Black,
                    fontSize = 16.sp,
                    maxLines = 1
                 )
              }

              if(openSpellSaveDialog){
              StatEntryPopup(
                 onDismissRequest = {
                    openSpellSaveDialog = false
                    Log.d("debug", "Spell Save: ${characterSheet.spellSaveDC.sumEntries()}")
                    initializer.refreshSpellSaveDC()
                    refreshContent()
                 },
                 statList = characterSheet.spellSaveDC,
                 statName = "Spell Save DC:",
                 updateFunction = updateFunction
              )
           }
              else if(openSpellAttackDialog){
                 StatEntryPopup(
                    onDismissRequest = {
                       openSpellAttackDialog = false
                       Log.d("debug", "Spell Attack: ${characterSheet.spellAttackBonus.sumEntries()}")
                       initializer.refreshSpellAttackBonus()
                       refreshContent()
                    },
                    statList = characterSheet.spellAttackBonus,
                    statName = "Spell Attack Bonus:",
                    updateFunction = updateFunction
                 )
              }
           }
        }
    }

}

@Preview
@Composable
fun BasicAppearanceWindow(
   characterSheet: CharacterSheet = theoChar.characterSheet,
   modifier: Modifier = Modifier,
   refresherFlag: Boolean = false,
   updateFunction: () -> Unit = {}
){
   Column (
      modifier = modifier
         .background(
            color = primaryContainerLight,
            shape = RoundedCornerShape(size = 2.dp)
         )
         .fillMaxWidth()
         .border(
            width = 2.dp,
            color = Color.Black,
            shape = RoundedCornerShape(size = 2.dp)
         )
   ) {
      Text(
         text = "Basic Appearance", textAlign = TextAlign.Center,
         modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
      )
      Divider(modifier = modifier.padding(horizontal = 10.dp))
      Row  {
         Column(verticalArrangement = Arrangement.SpaceEvenly) {
            Row(modifier = modifier
               .padding(vertical = 5.dp, horizontal = 5.dp)
               .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
               Text(
                  text = "Age: ",
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(end = 20.dp, start = 5.dp)
               )
               //TextField(value = characterSheet.background, onValueChange = {characterSheet.background = it})
               var ageText by remember {
                  mutableStateOf(characterSheet.age)
               }
               BasicTextField(
                  value = ageText,
                  onValueChange = {
                     ageText = it
                     characterSheet.age = ageText
                     updateFunction()
                     Log.d("debug", "Current Value of age: ${characterSheet.age}" )
                  },
                  singleLine = true,
                  modifier = modifier
                     .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(size = 5.dp)
                     )
                     .padding(horizontal = 3.dp)
                     .sizeIn(maxWidth = 100.dp)
               )
               Text(
                  text = "Height: ",
                  fontWeight = FontWeight.Bold
               )
               var heightText by remember {
                  mutableStateOf(characterSheet.height)
               }
               BasicTextField(
                  value = heightText,
                  onValueChange = {
                     heightText = it
                     characterSheet.height = heightText
                     updateFunction()
                     Log.d("debug", "Current value of height: ${characterSheet.height}")
                  },
                  singleLine = true,
                  modifier = modifier
                     .offset(x = -4.dp)
                     .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(size = 5.dp)
                     )
                     .padding(horizontal = 3.dp)
                     .sizeIn(maxWidth = 100.dp)

               )
            }
            Row(modifier = modifier
               .padding(vertical = 5.dp, horizontal = 5.dp)
               .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
               Text(
                  text = "Weight: ",
                  fontWeight = FontWeight.Bold
               )
               var weightText by remember {
                  mutableStateOf(characterSheet.weight)
               }
               BasicTextField(
                  value = weightText,
                  onValueChange = {
                     weightText = it
                     characterSheet.weight = weightText
                     updateFunction()
                     Log.d("debug", "Current value of Weight: ${characterSheet.weight}")
                  },
                  singleLine = true,
                  modifier = modifier
                     .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(size = 5.dp)
                     )
                     .padding(horizontal = 3.dp)
                     .sizeIn(maxWidth = 100.dp)
               )
               Text(
                  text = "Eyes: ",
                  fontWeight = FontWeight.Bold
               )
               var eyesText by remember {
                  mutableStateOf(characterSheet.eyes)
               }
               BasicTextField(
                  value = eyesText,
                  onValueChange = {
                     eyesText = it
                     characterSheet.eyes = eyesText
                     updateFunction()
                     Log.d("debug", "Current value of Eyes: ${characterSheet.eyes}")
                  },
                  singleLine = true,
                  modifier = modifier
                     .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(size = 5.dp)
                     )
                     .padding(horizontal = 3.dp)
                     .sizeIn(maxWidth = 100.dp)
               )
            }
            Row(modifier = modifier
               .padding(vertical = 5.dp, horizontal = 5.dp)
               .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
               Text(
                  text = "Skin: ",
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(end = 13.dp)

               )
               var skinText by remember {
                  mutableStateOf(characterSheet.skin)
               }
               BasicTextField(
                  value = skinText,
                  onValueChange = {
                     skinText = it
                     characterSheet.skin = skinText
                     updateFunction()
                     Log.d("debug", "Current value of Skin: ${characterSheet.skin}")
                  },
                  singleLine = true,
                  modifier = modifier
                     .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(size = 5.dp)
                     )
                     .padding(horizontal = 3.dp)
                     .sizeIn(maxWidth = 100.dp)
               )
               Text(
                  text = "Hair: ",
                  fontWeight = FontWeight.Bold
               )
               var hairText by remember {
                  mutableStateOf(characterSheet.hair)
               }
               BasicTextField(
                  value = hairText,
                  onValueChange = {
                     hairText = it
                     characterSheet.hair = hairText
                     updateFunction()
                     Log.d("debug", "Current value of Hair: ${characterSheet.hair}")
                  },
                  singleLine = true,
                  modifier = modifier
                      .background(
                          color = Color.LightGray,
                          shape = RoundedCornerShape(size = 5.dp)
                      )
                      .padding(horizontal = 3.dp)
                      .sizeIn(maxWidth = 100.dp)
               )
            }
         }
      }
   }
}

@Composable
fun SpellBookWindow(
   characterSheet: CharacterSheet = theoChar.characterSheet,
   refresherFlag: Boolean = false,
   refreshContent: () -> Unit = {},
   updateFunction: () -> Unit = {},
   appViewModel: AppViewModel
){
   Column (
      modifier = Modifier
         .padding(horizontal = 10.dp)
          .fillMaxWidth()



   ) {
      SpellCantripWindow(characterSheet = characterSheet,
         spellList = characterSheet.spellBook.cantrips,
         title = "Cantrips",
         appViewModel = appViewModel,
         refreshContent = refreshContent,
         updateFunction = updateFunction)

      Divider(
         color = Color.Black,
         thickness = 2.dp,
         modifier = Modifier.padding(vertical = 15.dp)
      )

      SpellLevelWindow(
         characterSheet = characterSheet,
         spellList = characterSheet.spellBook.levelOneSpells,
         maxSlots = characterSheet.spellBook.levelOneSlotsMax,
         currentSlots = characterSheet.spellBook.levelOneSlots,
         title = "Level One Spells",
         appViewModel = appViewModel,
         refreshContent = refreshContent,
         updateFunction = updateFunction)

      Divider(
         color = Color.Black,
         thickness = 2.dp,
         modifier = Modifier.padding(vertical = 15.dp)
      )

      SpellLevelWindow(
         characterSheet = characterSheet,
         spellList = characterSheet.spellBook.levelTwoSpells,
         maxSlots = characterSheet.spellBook.levelTwoSlotsMax,
         currentSlots = characterSheet.spellBook.levelTwoSlots,
         title = "Level Two Spells",
         appViewModel = appViewModel,
         refreshContent = refreshContent,
         updateFunction = updateFunction)

      Divider(
         color = Color.Black,
         thickness = 2.dp,
         modifier = Modifier.padding(vertical = 15.dp)
      )

      SpellLevelWindow(
         characterSheet = characterSheet,
         spellList = characterSheet.spellBook.levelThreeSpells,
         maxSlots = characterSheet.spellBook.levelThreeSlotsMax,
         currentSlots = characterSheet.spellBook.levelThreeSlots,
         title = "Level Three Spells",
         appViewModel = appViewModel,
         refreshContent = refreshContent,
         updateFunction = updateFunction)

      Divider(
         color = Color.Black,
         thickness = 2.dp,
         modifier = Modifier.padding(vertical = 15.dp)
      )

      SpellLevelWindow(
         characterSheet = characterSheet,
         spellList = characterSheet.spellBook.levelFourSpells,
         maxSlots = characterSheet.spellBook.levelFourSlotsMax,
         currentSlots = characterSheet.spellBook.levelFourSlots,
         title = "Level Four Spells",
         appViewModel = appViewModel,
         refreshContent = refreshContent,
         updateFunction = updateFunction)

      Divider(
         color = Color.Black,
         thickness = 2.dp,
         modifier = Modifier.padding(vertical = 15.dp)
      )

      SpellLevelWindow(
         characterSheet = characterSheet,
         spellList = characterSheet.spellBook.levelFiveSpells,
         maxSlots = characterSheet.spellBook.levelFiveSlotsMax,
         currentSlots = characterSheet.spellBook.levelFiveSlots,
         title = "Level Five Spells",
         appViewModel = appViewModel,
         refreshContent = refreshContent,
         updateFunction = updateFunction)

      Divider(
         color = Color.Black,
         thickness = 2.dp,
         modifier = Modifier.padding(vertical = 15.dp)
      )

      SpellLevelWindow(
         characterSheet = characterSheet,
         spellList = characterSheet.spellBook.levelSixSpells,
         maxSlots = characterSheet.spellBook.levelSixSlotsMax,
         currentSlots = characterSheet.spellBook.levelSixSlots,
         title = "Level Six Spells",
         appViewModel = appViewModel,
         refreshContent = refreshContent,
         updateFunction = updateFunction)

      Divider(
         color = Color.Black,
         thickness = 2.dp,
         modifier = Modifier.padding(vertical = 15.dp)
      )

      SpellLevelWindow(
         characterSheet = characterSheet,
         spellList = characterSheet.spellBook.levelSevenSpells,
         maxSlots = characterSheet.spellBook.levelSevenSlotsMax,
         currentSlots = characterSheet.spellBook.levelSevenSlots,
         title = "Level Seven Spells",
         appViewModel = appViewModel,
         refreshContent = refreshContent,
         updateFunction = updateFunction)

      Divider(
         color = Color.Black,
         thickness = 2.dp,
         modifier = Modifier.padding(vertical = 15.dp)
      )

      SpellLevelWindow(
         characterSheet = characterSheet,
         spellList = characterSheet.spellBook.levelEightSpells,
         maxSlots = characterSheet.spellBook.levelEightSlotsMax,
         currentSlots = characterSheet.spellBook.levelEightSlots,
         title = "Level Eight Spells",
         appViewModel = appViewModel,
         refreshContent = refreshContent,
         updateFunction = updateFunction)

      Divider(
         color = Color.Black,
         thickness = 2.dp,
         modifier = Modifier.padding(vertical = 15.dp)
      )

      SpellLevelWindow(
         characterSheet = characterSheet,
         spellList = characterSheet.spellBook.levelNineSpells,
         maxSlots = characterSheet.spellBook.levelNineSlotsMax,
         currentSlots = characterSheet.spellBook.levelNineSlots,
         title = "Level Nine Spells",
         appViewModel = appViewModel,
         refreshContent = refreshContent,
         updateFunction = updateFunction)
   }

}

@Composable
fun SpellCantripWindow(
   characterSheet: CharacterSheet = theoChar.characterSheet,
   refresherFlag: Boolean = false,
   refreshContent: () -> Unit = {},
   spellList: List<Spell>?,
   title: String = "",
   updateFunction: () -> Unit = {},
   appViewModel: AppViewModel){
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
            SpellList(spellList = spellList, appViewModel = appViewModel, additionMode = false, updateFunction = {
               //appViewModel.updateDatabase(characterSheet = characterSheet)
               updateFunction()
               refreshContent()
            })
         }
      }
   }

@Composable
fun SpellLevelWindow(
   characterSheet: CharacterSheet = theoChar.characterSheet,
   refresherFlag: Boolean = false,
   refreshContent: () -> Unit = {},
   spellList: List<Spell>?,
   maxSlots: StatList,
   currentSlots: IntWrapper,
   title: String = "",
   updateFunction: () -> Unit = {},
   appViewModel: AppViewModel
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
            SpellList(spellList = spellList, appViewModel = appViewModel, additionMode = false, updateFunction = {
               //appViewModel.updateDatabase(characterSheet = characterSheet)
               updateFunction()
               refreshContent()
            })
         }
      }
   }
}

