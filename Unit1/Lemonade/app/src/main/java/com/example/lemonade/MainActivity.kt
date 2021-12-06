package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                LemonadeScreen()
            }
        }
    }
}

@Composable
fun LemonadeScreen() {
    var currentScreen by rememberSaveable { mutableStateOf(LemonadeState.Start) }
    Scaffold (
        topBar = {
            TopAppBar(
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 30.sp
                )
            }
        }
    ){ innerPadding ->
        Box(Modifier.padding(innerPadding)){
            currentScreen.content(onScreenChange = { screen -> currentScreen = screen})
        }
    }

}

@Composable
fun Lemonade(lemonState: String){

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        LemonadeScreen()
    }
}