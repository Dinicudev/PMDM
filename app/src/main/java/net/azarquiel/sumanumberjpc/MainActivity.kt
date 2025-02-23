package net.azarquiel.sumanumberjpc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import net.azarquiel.sumanumberjpc.ui.theme.SumaNumberJPCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SumaNumberJPCTheme {
                MainScreen(MainViewModel(this))
            }
        }
    }
}



