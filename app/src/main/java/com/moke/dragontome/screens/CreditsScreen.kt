package com.moke.dragontome.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moke.dragontome.R
import com.moke.dragontome.ui.theme.primaryLight

@Composable
fun CreditsScreen(){

    val context = LocalContext.current
    val intent = remember {
        Intent(Intent.ACTION_VIEW, Uri.parse("https://paypal.me/moke74?country.x=CA&locale.x=en_US"))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.grunge_background),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(state = rememberScrollState())
            , horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Credits:", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black), modifier = Modifier
                .width(300.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = "App designed by Matthew Oke, 2024. Copyright held by Matthew Oke")
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Card(shape = RoundedCornerShape(16.dp),colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black), modifier = Modifier
                .width(300.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = "DragonTome is unofficial Fan Content permitted under the Fan Content Policy. Not approved/endorsed by Wizards. Portions of the materials used are property of Wizards of the Coast. ©Wizards of the Coast LLC.")
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Card(shape = RoundedCornerShape(16.dp),colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black), modifier = Modifier
                .width(300.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = "A huge thank you to those who helped in the making of this app. Thank you to Tatianna, Noah, Josh, Jeebs, Glue and Erza.")
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Card(shape = RoundedCornerShape(16.dp),colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black), modifier = Modifier
                .width(300.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            ) {
                TextButton(onClick = { context.startActivity(intent) }) {
                    Text(text = "Enjoying the app? Feel like supporting my future projects?", color = Color.Blue)
                }
                //Icon(imageVector = Icons.Rounded.Info, contentDescription = "Donate")
            }
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Card(shape = RoundedCornerShape(16.dp),colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black), modifier = Modifier
                .width(300.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = "Suggestions? Bugs? Feel free to let me know at:")
                    Text(text = "daftapollo74@gmail.com", fontStyle = FontStyle.Italic)
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Card(shape = RoundedCornerShape(16.dp),colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black), modifier = Modifier
                .width(300.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = "Credited resources used in the making of this app:")
                    Text(text = "App Background: https://www.freepik.com/free-photo/grunge-background_4258615.htm#fromView=search&page=1&position=2&uuid=d813fba5-b9aa-448b-b801-465e86264df6", fontStyle = FontStyle.Italic)
                    Text(text = "Roll Icon: https://www.flaticon.com/free-icon/role-playing_4258987?term=dice&page=1&position=39&origin=tag&related_id=4258987", fontStyle = FontStyle.Italic)
                }
            }
        }


    }
}