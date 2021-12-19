package com.example.lemonade

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

enum class LemonadeState(

    private val body: @Composable ((LemonadeState) -> Unit, Context?) -> Unit
) {
    Start(
        body = { onScreenChange, _ ->
            FragmentBody(
                changeScreen = { onScreenChange(Squeeze) },
                textId = R.string.lemon_select,
                imageId = R.drawable.lemon_tree
            )
        }
    ),

    Squeeze(
        body = { onScreenChange, context ->
            val lemonSize = remember { mutableStateOf((2..4).random()) }
            var clicked = remember { 0 }
            FragmentBody(
                changeScreen = {
                    if (lemonSize.equals(0)) onScreenChange(Drink)
                },
                textId = R.string.lemon_squeeze,
                imageId = R.drawable.lemon_squeeze,
                modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (lemonSize.value == 0) {
                                onScreenChange(Drink)
                            } else {
                                lemonSize.value--
                                clicked++
                            }
                        },
                        onLongPress = {
                            android.widget.Toast.makeText(
                                context,
                                "Squeeze count: $clicked, keep squeezing!",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            )
        }
    ),

    Drink(
        body = { onScreenChange, _ ->
            FragmentBody(
                { onScreenChange(Restart) },
                R.string.lemon_drink,
                R.drawable.lemon_drink
            )
        }
    ),

    Restart(
        body = { onScreenChange, _ ->
            FragmentBody(
                { onScreenChange(Start) },
                R.string.lemon_empty_glass,
                R.drawable.lemon_restart
            )
        }
    );

    @Composable
    fun Content(onScreenChange: (LemonadeState) -> Unit, @Nullable context: Context? = null) {
        body(onScreenChange, context)
    }
}

@Composable
fun FragmentBody(
    changeScreen: () -> Unit,
    @StringRes textId: Int,
    @DrawableRes imageId: Int,
    @Nullable modifier: Modifier? = null
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = textId))
        Image(
            painterResource(imageId),
            contentDescription = imageId.toString(),
            modifier = modifier ?: Modifier.clickable { changeScreen() }
        )
    }
}

