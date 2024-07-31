package com.example.dragontome

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dragontome.state.AppViewModel
import com.example.dragontome.ui.theme.DragonTomeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context: Context = applicationContext
        val appViewModel: AppViewModel = AppViewModel(context)
        setContent {
            DragonTomeTheme  {
                DragonTomeApp(context = context, appViewModel = appViewModel)

            }
        }
    }
}
