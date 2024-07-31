package com.example.dragontome.screens

import android.content.Context
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.dragontome.R
import com.example.dragontome.data.OfflineSpellRepository
import com.example.dragontome.data.Spell
import com.example.dragontome.data.SpellDao
import com.example.dragontome.data.SpellDatabase
import com.example.dragontome.data.SpellPreviewProvider
import com.example.dragontome.data.SpellsRepository
import com.example.dragontome.state.AppViewModel
import com.example.dragontome.ui.theme.primaryContainerDark
import com.example.dragontome.ui.theme.primaryContainerLight
import com.example.dragontome.ui.theme.primaryDark
import kotlinx.coroutines.flow.first
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
    SpellList(spellList = appViewModel.spellList, appViewModel = appViewModel, additionMode = true)
    }
}

@Preview
@Composable
fun SpellCard(
    @PreviewParameter(SpellPreviewProvider::class) spell: Spell,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    updateFunction: () -> Unit = {},
    additionMode: Boolean = true
) {
    var expanded by remember { mutableStateOf(false)}
    Card (
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = primaryContainerLight)
        //TODO: Add animation to the card expanding. Weird lag or not working at all last time.
    ) {
        Row {
            Column {
                Text(
                    text = spell.name,
                    modifier.padding(horizontal = 10.dp),
                    fontSize = 25.sp
                )
                Text(
                    text = if(spell.level.equals("Cantrip")) "${spell.school} ${spell.level}"
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
                SpellAddButton(additionMode = additionMode, onClick = onClick, updateFunction = updateFunction)
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
    modifier: Modifier = Modifier
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
    updateFunction: () -> Unit = {}
){
    IconButton(onClick = {
        onClick()
        updateFunction()
        Log.d("debug", "Button hit. Addition mode : ${additionMode}")
    }) {
        Icon(imageVector = if(additionMode) Icons.Filled.Add else Icons.Filled.Clear, contentDescription =
        if (additionMode) "Add Spell to Character" else "Remove Spell from Character")
    }
}