package com.moke.dragontome.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.moke.dragontome.R
import com.moke.dragontome.data.Spell
import com.moke.dragontome.data.SpellPreviewProvider
import com.moke.dragontome.state.AppViewModel
import com.moke.dragontome.ui.theme.primaryContainerLight
import com.moke.dragontome.ui.theme.primaryLight
import kotlinx.coroutines.*


@Composable
fun SpellsScreen(context: Context = LocalContext.current, appViewModel: AppViewModel ) {

Box(modifier = Modifier.fillMaxSize()) {
    Image(
        painter = painterResource(id = R.drawable.grunge_background),
        contentDescription = "",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.matchParentSize()
    )
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        var openFilterMenu by remember {
            mutableStateOf(false)
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Column(Modifier.fillMaxWidth(0.8f)) {
                Text(text = "Spellbook", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Divider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 5.dp),
                    color = Color.Black
                )
            }
            IconButton(onClick = {openFilterMenu = true  },
                modifier = Modifier
                    .background(color = primaryContainerLight, shape = RoundedCornerShape(8.dp))
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
            ) {
                Icon(imageVector = Icons.Sharp.Menu, contentDescription = "Filter")
            }
        }
        if(openFilterMenu){
            FilterDialog(
                appViewModel = appViewModel,
                onDismissRequest = { openFilterMenu = false }
            )
        }
        SpellList(spellList = if(appViewModel.filteredSpellList.isEmpty()) appViewModel.spellList else appViewModel.filteredSpellList, appViewModel = appViewModel, additionMode = true)
    }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(appViewModel: AppViewModel, onDismissRequest: () -> Unit){
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(400.dp)
                .verticalScroll(state = rememberScrollState()),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(width = 1.dp, color = Color.Black),
            colors = CardDefaults.cardColors(containerColor = primaryContainerLight)
        ) {
            Column {
                var cantripSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.cantripSelected)
                }
                var oneSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.oneSelected)
                }
                var twoSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.twoSelected)
                }
                var threeSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.threeSelected)
                }
                var fourSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.fourSelected)
                }
                var fiveSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.fiveSelected)
                }
                var sixSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.sixSelected)
                }
                var sevenSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.sevenSelected)
                }
                var eightSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.eightSelected)
                }
                var nineSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.nineSelected)
                }

                var abjurationSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.abjurationSelected)
                }
                var conjurationSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.conjurationSelected)
                }
                var divinationSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.divinationSelected)
                }
                var enchantmentSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.enchantmentSelected)
                }
                var evocationSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.evocationSelected)
                }
                var illusionSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.illusionSelected)
                }
                var necromancySelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.necromancySelected)
                }
                var transmutationSelected by remember {
                    mutableStateOf(appViewModel.spellFilterObject.transmutationSelected)
                }

                var titleSearch by remember {
                    mutableStateOf(appViewModel.spellFilterObject.titleSearch)
                }
                var descriptionSearch by remember {
                    mutableStateOf(appViewModel.spellFilterObject.descriptionSearch)
                }


                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)) {
                    Text(
                        text = "Spell Filter",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    IconButton(
                        onClick = {
                            cantripSelected = false
                            oneSelected = false
                            twoSelected = false
                            threeSelected = false
                            fourSelected = false
                            fiveSelected = false
                            sixSelected = false
                            sevenSelected = false
                            eightSelected = false
                            nineSelected = false

                            abjurationSelected = false
                            conjurationSelected = false
                            divinationSelected = false
                            enchantmentSelected = false
                            evocationSelected = false
                            illusionSelected = false
                            necromancySelected = false
                            transmutationSelected = false

                            titleSearch = ""
                            descriptionSearch = ""

                            appViewModel.spellFilterObject.clearFilter()
                            appViewModel.filteredSpellList = emptyList()
                                  },
                        modifier = Modifier
                    ) {
                        Icon(imageVector = Icons.Sharp.Refresh, contentDescription = "", modifier = Modifier)

                    }
                }
                Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp), color = Color.Black)
                Text(text = "Spell Level:", modifier = Modifier.padding(horizontal = 20.dp))
                Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp), color = Color.LightGray)

                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {


                        FilterChip(selected = cantripSelected, onClick = {
                            cantripSelected = !cantripSelected
                            appViewModel.spellFilterObject.cantripSelected = cantripSelected
                            if (cantripSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                        } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                        } }, label = {
                            Text(text = "Cantrip")
                        },
                            leadingIcon = {
                                if (cantripSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )

                        FilterChip(selected = oneSelected, modifier = Modifier.padding(horizontal = 10.dp), onClick = {
                            oneSelected = !oneSelected
                            appViewModel.spellFilterObject.oneSelected = oneSelected
                            if (oneSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("1") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("1") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "1st")
                        },
                            leadingIcon = {
                                if (oneSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )

                        FilterChip(selected = twoSelected, onClick = {
                            twoSelected = !twoSelected
                            appViewModel.spellFilterObject.twoSelected = twoSelected
                            if (twoSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("2") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("2") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "2nd")
                        },
                            leadingIcon = {
                                if (twoSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )

                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(state = rememberScrollState()), horizontalArrangement = Arrangement.Center) {



                        FilterChip(selected = threeSelected, onClick = {
                            threeSelected = !threeSelected
                            appViewModel.spellFilterObject.threeSelected = threeSelected
                            if (threeSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("3") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("3") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "3rd")
                        },
                            leadingIcon = {
                                if (threeSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )

                        FilterChip(selected = fourSelected, modifier = Modifier.padding(start = 10.dp, end = 5.dp), onClick = {
                            fourSelected = !fourSelected
                            appViewModel.spellFilterObject.fourSelected = fourSelected
                            if (fourSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("4") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("4") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "4th")
                        },
                            leadingIcon = {
                                if (fourSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )

                        FilterChip(selected = fiveSelected, modifier = Modifier.padding(start = 5.dp, end = 10.dp), onClick = {
                            fiveSelected = !fiveSelected
                            appViewModel.spellFilterObject.fiveSelected = fiveSelected
                            if (fiveSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("5") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("5") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "5th")
                        },
                            leadingIcon = {
                                if (fiveSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )

                        FilterChip(selected = sixSelected, onClick = {
                            sixSelected = !sixSelected
                            appViewModel.spellFilterObject.sixSelected = sixSelected
                            if (sixSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("6") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("6") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "6th")
                        },
                            leadingIcon = {
                                if (sixSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )

                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {


                        FilterChip(selected = sevenSelected, onClick = {
                            sevenSelected = !sevenSelected
                            appViewModel.spellFilterObject.sevenSelected = sevenSelected
                            if (sevenSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("7") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("7") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "7th")
                        },
                            leadingIcon = {
                                if (sevenSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )

                        FilterChip(selected = eightSelected, modifier = Modifier.padding(horizontal = 10.dp), onClick = {
                            eightSelected = !eightSelected
                            appViewModel.spellFilterObject.eightSelected = eightSelected
                            if (eightSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("8") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("8") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "8th")
                        },
                            leadingIcon = {
                                if (eightSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )

                        FilterChip(selected = nineSelected, onClick = {
                            nineSelected = !nineSelected
                            appViewModel.spellFilterObject.nineSelected = nineSelected
                            if (nineSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("9") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("9") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "9th")
                        },
                            leadingIcon = {
                                if (nineSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Text(text = "Spell School:", modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .align(alignment = Alignment.Start))
                    Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp), color = Color.LightGray)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                        FilterChip(selected = abjurationSelected, modifier = Modifier.padding(end = 5.dp), onClick = {
                            abjurationSelected = !abjurationSelected
                            appViewModel.spellFilterObject.abjurationSelected = abjurationSelected
                            if (abjurationSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "Abjuration")
                        },
                            leadingIcon = {
                                if (abjurationSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )

                        FilterChip(selected = conjurationSelected, modifier = Modifier.padding(start = 5.dp), onClick = {
                            conjurationSelected = !conjurationSelected
                            appViewModel.spellFilterObject.conjurationSelected = conjurationSelected
                            if (conjurationSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "Conjuration")
                        },
                            leadingIcon = {
                                if (conjurationSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {


                        FilterChip(selected = divinationSelected, modifier = Modifier.padding(end = 5.dp), onClick = {
                            divinationSelected = !divinationSelected
                            appViewModel.spellFilterObject.divinationSelected = divinationSelected
                            if (divinationSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "Divination")
                        },
                            leadingIcon = {
                                if (divinationSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )

                        FilterChip(selected = enchantmentSelected, modifier = Modifier.padding(start = 5.dp), onClick = {
                            enchantmentSelected = !enchantmentSelected
                            appViewModel.spellFilterObject.enchantmentSelected = enchantmentSelected
                            if (enchantmentSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "Enchantment")
                        },
                            leadingIcon = {
                                if (enchantmentSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {


                        FilterChip(selected = evocationSelected, modifier = Modifier.padding(end = 5.dp), onClick = {
                            evocationSelected = !evocationSelected
                            appViewModel.spellFilterObject.evocationSelected = evocationSelected
                            if (evocationSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "Evocation")
                        },
                            leadingIcon = {
                                if (evocationSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )

                        FilterChip(selected = illusionSelected, modifier = Modifier.padding(start = 5.dp), onClick = {
                            illusionSelected = !illusionSelected
                            appViewModel.spellFilterObject.illusionSelected = illusionSelected
                            if (illusionSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "Illusion")
                        },
                            leadingIcon = {
                                if (illusionSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(state = rememberScrollState()), horizontalArrangement = Arrangement.Center) {


                        FilterChip(selected = necromancySelected, modifier = Modifier.padding(end = 5.dp), onClick = {
                            necromancySelected = !necromancySelected
                            appViewModel.spellFilterObject.necromancySelected = necromancySelected
                            if (necromancySelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "Necromancy")
                        },
                            leadingIcon = {
                                if (necromancySelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )

                        FilterChip(selected = transmutationSelected, modifier = Modifier.padding(start = 5.dp), onClick = {
                            transmutationSelected = !transmutationSelected
                            appViewModel.spellFilterObject.transmutationSelected = transmutationSelected
                            if (transmutationSelected){
                                Log.d("debug", "Filtered list before union: ${appViewModel.filteredSpellList}.")
                                //appViewModel.filteredSpellList = appViewModel.filteredSpellList.union(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.sortedBy { it.level }
                                Log.d("debug", "Filtered list after union: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } else{
                                Log.d("debug", "Filtered list before subtract: ${appViewModel.filteredSpellList}.")
                                appViewModel.filteredSpellList = appViewModel.filteredSpellList.subtract(appViewModel.spellList.filter { it.level.equals("0") }).toList()
                                Log.d("debug", "Filtered list after subtract: ${appViewModel.filteredSpellList}.")
                                Log.d("debug", "Count: ${appViewModel.filteredSpellList.count()}")
                            } }, label = {
                            Text(text = "Transmutation")
                        },
                            leadingIcon = {
                                if (transmutationSelected){
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Selected")
                                } else {
                                    null
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = primaryLight)
                        )
                    }
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Text(text = "Spell Name:", modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .align(alignment = Alignment.Start))
                    Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp), color = Color.LightGray)
                    TextField(value = titleSearch, onValueChange = {
                        titleSearch = it
                        appViewModel.spellFilterObject.titleSearch = titleSearch
                        appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                    },
                        colors = TextFieldDefaults.colors(focusedContainerColor = Color.LightGray, unfocusedContainerColor = primaryContainerLight,
                          unfocusedPlaceholderColor = Color.LightGray),
                        placeholder = { Text(text = "Fireball", fontStyle = FontStyle.Italic)}
                    )

                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Text(text = "Spell Description:", modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .align(alignment = Alignment.Start))
                    Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp), color = Color.LightGray)
                    TextField(value = descriptionSearch, onValueChange = {
                        descriptionSearch = it
                        appViewModel.spellFilterObject.descriptionSearch = descriptionSearch
                        appViewModel.filteredSpellList = appViewModel.spellList.filter { appViewModel.spellFilterObject.getFilterPredicate(it) }
                    },
                        colors = TextFieldDefaults.colors(focusedContainerColor = Color.LightGray, unfocusedContainerColor = primaryContainerLight,
                            unfocusedPlaceholderColor = Color.LightGray),
                        placeholder = { Text(text = "fire damage", fontStyle = FontStyle.Italic)}
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SpellCard(
    @PreviewParameter(SpellPreviewProvider::class) spell: Spell,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    updateFunction: () -> Unit = {},
    additionMode: Boolean = true,
    isEditable: Boolean = true
) {
    var expanded by remember { mutableStateOf(false)}
    Card (
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = primaryContainerLight)
        //TODO: Add animation to the card expanding. Weird lag or not working at all last time.
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth(0.85f)) {
                Text(
                    text = spell.name,
                    modifier.padding(horizontal = 10.dp),
                    fontSize = 25.sp
                )
                Text(
                    text = if(spell.level.equals("0")) "${spell.school} Cantrip"
                    else if (spell.level.equals("1")) "${spell.level}st Level ${spell.school}"
                    else if (spell.level.equals("2")) "${spell.level}nd Level ${spell.school}"
                    else if (spell.level.equals("3")) "${spell.level}rd Level ${spell.school}"
                    else "${spell.level}th Level ${spell.school}",

                    modifier = modifier.padding(start = 15.dp, end = 15.dp, top = 0.dp, bottom = 5.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            SpellItemButton(expanded = expanded, onClick = { expanded = !expanded })

        }

        if(expanded) {

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Cast Time: ")
                    }
                    append("${spell.castTime}")
                },
                modifier = modifier.padding(horizontal = 15.dp, vertical = 5.dp)
            )
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Range: ")
                    }
                    append("${spell.range}")
                },
                modifier = modifier.padding(horizontal = 15.dp, vertical = 5.dp)
            )
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Components: ")
                    }
                    append("${spell.components}")
                },
                modifier = modifier.padding(horizontal = 15.dp, vertical = 5.dp)
            )
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Duration: ")
                    }
                    append("${spell.duration}")
                },
                modifier = modifier.padding(horizontal = 15.dp, vertical = 5.dp)
            )
            Text(
                text = spell.text,
                modifier = modifier.padding(horizontal = 15.dp, vertical = 5.dp)
            )
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Source: ")
                    }
                    append("${spell.source}")
                },
                modifier = modifier.padding(horizontal = 15.dp, vertical = 5.dp)
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

                SpellAddButton(additionMode = additionMode, onClick = onClick, updateFunction = updateFunction, isEditable = isEditable)
            }
        }
    }
}

@Composable
fun SpellList(
    spellList: List<Spell>,
    additionMode: Boolean,
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier,
    updateFunction: () -> Unit = {}
) {
    LazyColumn (modifier = modifier, contentPadding = PaddingValues(all = 5.dp)) {
        items(spellList) {
            spell->
            SpellCard(spell = spell, modifier = modifier, onClick = {
                appViewModel.addSpell(additionMode = additionMode, spell = spell)
                if(appViewModel.currentCharacter != null)
                    appViewModel.updateDatabase(characterSheet = appViewModel.currentCharacter!!.characterSheet)
            },
                updateFunction = updateFunction,
                additionMode = additionMode
            )
            Spacer(modifier = Modifier.padding(all = 3.dp))

        }
    }
}

@Composable
private fun SpellItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if(expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.expand),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
private fun SpellAddButton(
    onClick: () -> Unit = {},
    additionMode: Boolean = true,
    updateFunction: () -> Unit = {},
    isEditable: Boolean = true
){
    IconButton(
        enabled = isEditable,
        onClick = {
        onClick()
        updateFunction()
    }) {
        Icon(imageVector = if(additionMode) Icons.Filled.Add else Icons.Filled.Clear, contentDescription =
        if (additionMode) "Add Spell to Character" else "Remove Spell from Character")
    }
}