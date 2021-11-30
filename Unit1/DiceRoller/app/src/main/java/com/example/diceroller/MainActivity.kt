package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


import androidx.constraintlayout.compose.ConstraintLayout

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
    val result1: Int by viewModel.number1.observeAsState(1)
    val result2: Int by viewModel.number2.observeAsState(-1)
    Column(Modifier.fillMaxSize()) {

        Row(
            Modifier
                .fillMaxWidth()
                .weight(0.8f, true)
        ) {
            DiceScreen(
                Modifier
                    .weight(1f)
                    .align(CenterVertically), result1, viewModel::rollDice1
            )
            if (result2 != -1) {
                DiceScreen(
                    Modifier
                        .weight(1f)
                        .align(CenterVertically),
                    number = result2,
                    onButtonClicked = viewModel::rollDice2
                )
            }
        }

        Button(
            onClick = if (result2 == -1) viewModel::makeDice2 else viewModel::deleteDice,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(16.dp)
        ) {
            if (result2 == -1)
                Text("Make Dice 2")
            else
                Text("Delete Dice 2")
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun DiceScreen(modifier: Modifier = Modifier, number: Int, onButtonClicked: () -> Unit) {
    ConstraintLayout(modifier) {

        val (numberText, rollButton) = createRefs()


        Dice(
            number = number,
            modifier = Modifier.constrainAs(numberText) {
                top.linkTo(parent.top, 0.dp)
                bottom.linkTo(parent.bottom, 0.dp)
                start.linkTo(parent.start, 0.dp)
                end.linkTo(parent.end, 0.dp)
            }
        )

        Button(
            onClick = onButtonClicked,
            modifier = Modifier.constrainAs(rollButton) {
                top.linkTo(numberText.bottom, 0.dp)
            }
        ) {
            Text("ROLL")
        }

    }
}

@ExperimentalAnimationApi
@Composable
fun Dice(modifier: Modifier = Modifier, number: Int) {

    AnimatedContent(
        targetState = number,
        transitionSpec = {
            (slideInVertically({ height -> height }) + fadeIn() with
                    slideOutVertically({ height -> -height }) + fadeOut())
                .using(
                    // Disable clipping since the faded slide-in/out should
                    // be displayed out of bounds.
                    SizeTransform(clip = false)
                )
        }
    )
    { targetNumber ->
        val drawableId = when (targetNumber) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        Image(
            painterResource(
                drawableId
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center
        )
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun DicePreview() {
    DiceScreen(Modifier, 1) {}
}