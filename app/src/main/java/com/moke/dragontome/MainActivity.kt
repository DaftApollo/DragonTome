package com.moke.dragontome

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.moke.dragontome.state.AppViewModel
import com.moke.dragontome.ui.theme.DragonTomeTheme
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch { MobileAds.initialize(this@MainActivity) {} }

        val context: Context = applicationContext
        val appViewModel: AppViewModel = AppViewModel(context)
        setContent {
            DragonTomeTheme  {
                DragonTomeApp(context = context, appViewModel = appViewModel)

            }
        }
    }
}
