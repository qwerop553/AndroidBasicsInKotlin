package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.tiptime.ui.theme.TipTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    TipTimeScreen()
                }
            }
        }
    }
}

@Composable
fun TipTimeScreen() {
    var text by rememberSaveable { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    // TODO() : Study about InteractionSource Class Impl
    // val isFocused = interactionSource.collectIsFocusedAsState()


    ConstraintLayout(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()

    ) {

        var result by rememberSaveable { mutableStateOf("") }
        val (textField, editTextField, radioGroup, switchButton, calculateButton, tipAmount) = createRefs()

        TextField(
            value = text,
            onValueChange = {
                if (isNormalCost(it)) text = it
            },
            modifier = Modifier
                .width(160.dp)
                .constrainAs(editTextField) {
                    top.linkTo(parent.top, margin = 0.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                },
            label = {
                Text(stringResource(R.string.cost_of_service))
                //  Text ("${isFocused.value}")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )

        )

        Text(
            text = stringResource(R.string.how_was_the_service),
            modifier = Modifier
                .constrainAs(textField) {
                    start.linkTo(editTextField.start, margin = 0.dp)
                    top.linkTo(editTextField.bottom, margin = 0.dp)
                }
        )

        displayRadioGroup(
            modifier = Modifier.constrainAs(radioGroup) {
                top.linkTo(textField.bottom, margin = 8.dp)
                start.linkTo(editTextField.start, margin = 0.dp)
            }
        )

        SwitchButton(
            modifier = Modifier.constrainAs(switchButton) {
                top.linkTo(radioGroup.bottom, margin = 8.dp)
            })

        Button(
            onClick = {
                val cost = if(isNormalCost(text)) text.toDouble() else 0
                val serviceClass = when()
                      result = ret

            },
            modifier = Modifier
                .constrainAs(calculateButton) {
                    top.linkTo(switchButton.bottom, margin = 8.dp)
                }
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.calculate),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Text(
            text = stringResource(R.string.tip_amount) + result,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.constrainAs(tipAmount){
                top.linkTo(calculateButton.bottom, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)

            }
        )

    }
}


/**
 * RadioGroup
 */
@Composable
fun displayRadioGroup(modifier: Modifier = Modifier, selected: ListString) {
    var selected by remember { mutableStateOf(AMAZING) }
    Column(modifier = modifier) {
        Row {
            RadioButton(selected = selected == AMAZING, onClick = { selected = AMAZING })
            Text(
                text = stringResource(R.string.amazing_service),
                modifier = Modifier
                    .clickable { selected = AMAZING }
                    .padding(start = 4.dp, top = 4.dp)
            )
        }
        Row {
            RadioButton(selected = selected == GOOD, onClick = { selected = GOOD })
            Text(
                text = stringResource(R.string.good_service),
                modifier = Modifier
                    .clickable { selected = GOOD }
                    .padding(start = 4.dp, top = 4.dp)
            )
        }
        Row {
            RadioButton(selected = selected == OKAY, onClick = { selected = OKAY })
            Text(
                text = stringResource(R.string.ok_service),
                modifier = Modifier
                    .clickable { selected = OKAY }
                    .padding(start = 4.dp, top = 4.dp)
            )
        }

    }
}

@Composable
fun SwitchButton(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(stringResource(R.string.round_up_tip))
        Switch(checked = checked, onCheckedChange = { checked = it })
    }

}

private const val AMAZING: String = "Amazing (20%)"
private const val GOOD: String = "Good (18%)"
private const val OKAY: String = "Okay (15%)"

@Preview(showBackground = true)
@Composable
fun TipTimePreview() {
    TipTimeScreen()
}
