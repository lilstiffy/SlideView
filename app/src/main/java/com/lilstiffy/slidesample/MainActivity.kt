package com.lilstiffy.slidesample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lilstiffy.slidesample.ui.theme.LibPlaygroundTheme
import com.lilstiffy.slideview.SlideViewConfig
import com.lilstiffy.slideview.SlideView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibPlaygroundTheme {
                var backgroundColor by remember { mutableStateOf(Color.White) }

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor)
                        .padding(horizontal = 32.dp)
                ) {
                    SlideView(
                        text = "Slide to confirm",
                        completionDelay = 1000L,
                        onSlideDone = {
                            Log.d("MainActivity", "Swipe done!")
                            backgroundColor = Color.Green
                        },
                        config = SlideViewConfig().copy(backgroundColor = Color.Black),
                        modifier = Modifier.padding(top = 64.dp)
                    )

                    SlideView(
                        text = "Slide to unlock", completionDelay = 1000L, onSlideDone = {
                            Log.d("MainActivity", "Swipe done!")
                            backgroundColor = Color.Green
                        }, config = SlideViewConfig().copy(backgroundColor = Color.Blue),
                        modifier = Modifier.padding(top = 64.dp)
                    )
                }
            }
        }
    }
}
