package com.moke.dragontome.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.moke.dragontome.R
import com.moke.dragontome.data.Note
import com.moke.dragontome.state.AppViewModel
import com.moke.dragontome.ui.theme.primaryContainerLight
import com.moke.dragontome.ui.theme.removeColor

@Composable
fun NotesScreen(appViewModel: AppViewModel){

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.grunge_background),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        var noteSelected by remember {
            mutableStateOf(appViewModel.currentNote != null)
        }

       if(!noteSelected){

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var openCreateNoteDialog by remember {
                    mutableStateOf(false)
                }
                var openDeletionDialog by remember {
                    mutableStateOf(false)
                }
                Text(
                    text = "Notes",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp).fillMaxWidth()
                )
                Divider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    color = Color.Black
                )

                LazyColumn(contentPadding = PaddingValues(5.dp)) {

                    items(appViewModel.noteList.sortedBy { it.lastUpdated }) { note ->

                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                                color = Color.Black
                            ),
                            ) {
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White, shape = RoundedCornerShape(8.dp)), verticalAlignment = Alignment.CenterVertically) {
                                androidx.compose.material3.IconButton(
                                    onClick = { openDeletionDialog = true },
                                    modifier = Modifier
                                        .padding(bottom = 10.dp, start = 15.dp)
                                        .size(size = 20.dp)

                                ) {
                                    androidx.compose.material3.Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Delete Note"
                                    )
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
                                                androidx.compose.material3.Text(
                                                    text = "Delete \"${note.title}\"?",
                                                    modifier = Modifier.padding(vertical = 10.dp)
                                                )
                                                androidx.compose.material.Divider(
                                                    thickness = 1.dp,
                                                    modifier = Modifier.padding(
                                                        horizontal = 10.dp,
                                                        vertical = 5.dp
                                                    )
                                                )
                                                Row {
                                                    androidx.compose.material.TextButton(
                                                        onClick = {
                                                            appViewModel.removeNote(note)
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
                                                        androidx.compose.material3.Text(text = "Delete")
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
                                                        androidx.compose.material3.Text(text = "Cancel")
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                Column(Modifier.fillMaxWidth(0.80f)) {
                                    Text(
                                        text = note.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(start = 10.dp)
                                    )
                                    Text(
                                        text = note.data,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = Color.LightGray,
                                        fontStyle = FontStyle.Italic,
                                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 3.dp)
                                    )
                                }
                                CharacterItemButton(onClick = {
                                    appViewModel.currentNote = note
                                    noteSelected = true
                                })
                            }
                        }
                        Spacer(modifier = Modifier.padding(vertical = 5.dp))

                    }
                }

                IconButton(
                    onClick = { openCreateNoteDialog = true },
                    modifier = Modifier
                        .align(alignment = Alignment.End)
                        .padding(horizontal = 25.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))

                ) {
                    Icon(imageVector = Icons.Sharp.Add, contentDescription = "Create new note")
                }

                if (openCreateNoteDialog) {
                    CreateNoteDialog(
                        appViewModel = appViewModel,
                        onDismiss = { openCreateNoteDialog = false })
                }
            }
        }
        else if (noteSelected){
            var noteData by remember {
                mutableStateOf(appViewModel.currentNote!!.data)
            }
           var noteTitle by remember {
               mutableStateOf(appViewModel.currentNote!!.title)
           }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {

                TextButton(modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)), onClick = {
                    appViewModel.currentNote = null
                    noteSelected = false
                }) {
                    Text(text = "Back", fontStyle = FontStyle.Italic, modifier = Modifier.padding(0.dp))
                }

                BasicTextField(value = noteTitle, onValueChange = {
                    noteTitle = it
                    appViewModel.currentNote!!.title = noteTitle
                    appViewModel.currentNote!!.lastUpdated = System.currentTimeMillis()
                    appViewModel.updateNote(appViewModel.currentNote!!)
                                                                  },
                    keyboardOptions = KeyboardOptions(autoCorrect = false),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp), textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))

                Divider(
                    thickness = 2.dp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    color = Color.Black
                )


                Column(
                    Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                            color = Color.Black
                        )) {
                    BasicTextField(
                        value = noteData, onValueChange = {
                            noteData = it
                            appViewModel.currentNote!!.data = noteData
                            appViewModel.currentNote!!.lastUpdated = System.currentTimeMillis()
                            appViewModel.updateNote(appViewModel.currentNote!!)
                        },
                        keyboardOptions = KeyboardOptions(autoCorrect = false),
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(height = 500.dp, width = 350.dp)
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .verticalScroll(state = rememberScrollState())
                    )
                }
            }
       }
    }

}

@Composable
fun CreateNoteDialog(appViewModel: AppViewModel, onDismiss:() -> Unit){
    var noteName by remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
                .verticalScroll(state = rememberScrollState()),
            colors = CardDefaults.cardColors(containerColor = primaryContainerLight),
            shape = RoundedCornerShape(16.dp)
        ){
            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp)){
                androidx.compose.material3.Text(
                    text = "Create a new notes page?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                TextField(
                    value = noteName,
                    onValueChange = {noteName = it},
                    label = { androidx.compose.material3.Text(text = "Note Name") },
                    placeholder = { androidx.compose.material3.Text(text = "Note Name") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.White,
                        focusedLabelColor = Color.Black
                    )
                )
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    TextButton(onClick = onDismiss) {
                        androidx.compose.material3.Text(text = "Cancel", color = Color.Black)
                    }
                    TextButton(onClick = {
                        appViewModel.createNote(title = noteName)
                        onDismiss()
                    }) {
                        androidx.compose.material3.Text(text = "Create", color = Color.Black)
                    }
                }
            }
        }
    }
}