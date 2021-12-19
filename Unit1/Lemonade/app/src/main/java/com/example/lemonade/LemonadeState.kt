package com.example.lemonade

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf

enum class LemonadeState(

    private val body: @Composable ((LemonadeState) -> Unit) -> Unit
) {
    Start(
        body = { onScreenChange ->
            Column() {
                Text(text = stringResource(id = R.string.lemon_select))
                Button(onClick = { onScreenChange(LemonadeState.Squeeze) }) {
                    Image(
                        painterResource(R.drawable.lemon_tree),
                        contentDescription = "Start",
                    )
                }

            }
        }
    ),

    Squeeze(
        body = { onScreenChange ->
            Column() {
                var lemonSize = remember { mutableStateOf((2..4).random()) }
                Log.d("TAG", "lemonSize: $lemonSize")
                Text(text = stringResource(id = R.string.lemon_squeeze))
                Button(onClick = {
                    if (lemonSize.value == 0) {
                        onScreenChange(LemonadeState.Drink)
                        Log.d("TAG", "lemonSize: $lemonSize")
                    }
                    else {
                        lemonSize.value--
                        Log.d("TAG", "lemonSize: $lemonSize")}
                }) {
                    Image(
                        painterResource(R.drawable.lemon_squeeze),
                        contentDescription = "Squeeze",
                    )
                }

            }
        }
    ),

    Drink(
        body = { onScreenChange ->
            Column() {
                Text(text = stringResource(id = R.string.lemon_drink))
                Button(onClick = { onScreenChange(LemonadeState.Restart) }) {
                    Image(
                        painterResource(R.drawable.lemon_drink),
                        contentDescription = "Drink",
                    )
                }

            }
        }
    ),

    Restart(
        body = { onScreenChange ->
            Column() {
                Text(text = stringResource(id = R.string.lemon_empty_glass))
                Button(onClick = { onScreenChange(LemonadeState.Start) }) {
                    Image(
                        painterResource(R.drawable.lemon_restart),
                        contentDescription = "Restart",
                    )
                }

            }
        }
    );

    @Composable
    fun content(onScreenChange: (LemonadeState) -> Unit) {
        body(onScreenChange)
    }


}