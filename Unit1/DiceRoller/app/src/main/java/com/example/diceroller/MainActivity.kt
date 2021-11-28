package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


import androidx.constraintlayout.compose.ConstraintLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainActivityViewModel by viewModels()

        setContent{
            DiceActivityScreen(viewModel)
        }

    }
}

@Composable
fun DiceActivityScreen(viewModel: MainActivityViewModel){
    val result: Int by viewModel.number.observeAsState(1)
    DiceScreen(result, viewModel::rollDice)
}

@Composable
fun DiceScreen(number: Int, onButtonClicked: () -> Unit){
    ConstraintLayout(Modifier.fillMaxSize()){

        val (numberText, rollButton) = createRefs()

        Text(
            text = number.toString(),
            modifier = Modifier.constrainAs(numberText){
                top.linkTo(parent.top, 0.dp)
                bottom.linkTo(parent.bottom, 0.dp)
                start.linkTo(parent.start, 0.dp)
                end.linkTo(parent.end, 0.dp)
            }
        )

        Button(
            onClick = onButtonClicked,
            modifier = Modifier.constrainAs(rollButton){
                top.linkTo(numberText.bottom, 0.dp)
                start.linkTo(numberText.start, 0.dp)
                end.linkTo(numberText.end, 0.dp)
            }
        ){
            Text("ROLL")
        }

    }
}

@Preview
@Composable
fun DicePreview(){
    DiceScreen(1){}
}