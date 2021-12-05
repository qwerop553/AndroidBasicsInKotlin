package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


import androidx.constraintlayout.compose.ConstraintLayout
import kotlinx.coroutines.internal.synchronized
import kotlin.reflect.KProperty

class MainActivity : AppCompatActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainActivityViewModel by viewModels()

        setContent {
            DiceActivityScreen(viewModel)
        }

    }
}

@ExperimentalAnimationApi
@Composable
fun DiceActivityScreen(viewModel: MainActivityViewModel) {
    val result1 by viewModel.dice1.observeAsState()
    val result2 by viewModel.dice2.observeAsState()
    Log.d("MainActivity", "DiceActivityScreen Recomposition Occurred")
    Column(Modifier.fillMaxSize()) {

        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f, true)
        ) {
            DiceScreen(
                Modifier
                    .weight(1f)
                    .align(CenterVertically),
                number = result1!!,
                onButtonClicked = viewModel::rollDice1
            )
            if (result2!!.eye != -1) {
                DiceScreen(
                    Modifier
                        .weight(1f)
                        .align(CenterVertically),
                    number = result2!!,
                    onButtonClicked = viewModel::rollDice2
                )
            }
        }

        Text(
            text = "Sum of Dices: ${result1!!.eye + ( if (result2!!.eye == -1) 0 else result2!!.eye)}" +
                    " Roll Time: ${viewModel.rollTime}"
        )

        Button(
            onClick = if (result2!!.eye == -1) viewModel::makeDice2 else viewModel::deleteDice,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(16.dp)
        ) {
            if (result2!!.eye == -1)
                Text("Make Dice 2")
            else
                Text("Delete Dice 2")
        }
    }
}




@ExperimentalAnimationApi
@Composable
fun DiceScreen(modifier: Modifier = Modifier, number: DiceResult, onButtonClicked: () -> Unit) {
    Log.d("MainActivity", "DiceScreen Recomposition Occured")
    ConstraintLayout(modifier.background(Color.LightGray)) {

        val (numberText, rollButton) = createRefs()


        Dice(
            number = number,
            modifier = Modifier.constrainAs(numberText) {
                top.linkTo(parent.top, 30.dp)
                start.linkTo(parent.start, 12.dp)
                end.linkTo(parent.end, 12.dp)
            }
        )

        Button(
            onClick = onButtonClicked,
            modifier = Modifier.constrainAs(rollButton) {
                start.linkTo(parent.start, 12.dp)
                end.linkTo(parent.end, 12.dp)
                top.linkTo(numberText.bottom, 10.dp)
                bottom.linkTo(parent.bottom, 10.dp)
            }
        ) {
            Text("ROLL")
        }

    }
}

// 이 컴포저블은 실험용이므로 추후에 삭제되거나 변경될 수 있습니다.
@ExperimentalAnimationApi
@Composable
fun Dice(modifier: Modifier = Modifier, number: DiceResult) {

    AnimatedContent(
        targetState = number,
        modifier = modifier,
        transitionSpec = {
            // EnterTransition with ExitTransition
            (slideInVertically({ height -> height }) + fadeIn() with
                    slideOutVertically({ height -> -height }) + fadeOut())
                .using(
                    // Disable clipping since the faded slide-in/out should
                    // be displayed out of bounds.
                    SizeTransform(clip = false)
                )
        }
    )
    { targetState ->
        Log.d("MainActivity", "Dice Recomposition Occurred")
        Log.d("MainActivity", "Dice: $number")
        DiceImage(selectDiceDrawable(targetState.eye))
    }
}

@Composable
private fun DiceImage(@DrawableRes drawableId: Int) {

    Image(
        painterResource(
            drawableId
        ),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center
    )
}

@DrawableRes
private fun selectDiceDrawable(targetNumber: Int): Int{
     return when (targetNumber) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}
