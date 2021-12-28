package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import com.example.tiptime.ui.theme.TipTimeTheme
import kotlin.math.ceil

private const val AMAZING: String = "Amazing (20%)"
private const val GOOD: String = "Good (18%)"
private const val OKAY: String = "Okay (15%)"

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
        decoupledConstraints(0.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        var result by rememberSaveable { mutableStateOf("") }

        TextField(
            value = text,
            onValueChange = {
                if (isNormalCost(it)) text = it
            },
            modifier = Modifier
                .width(160.dp)
                .layoutId("costOfService"),
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
            modifier = Modifier.layoutId("howWasTheService")
        )

        val selectedService = displayRadioGroup(
            modifier = Modifier.layoutId("serviceRadioButton"),
            serviceOptions = listOf(
                stringResource(R.string.amazing_service),
                stringResource(R.string.good_service),
                stringResource(R.string.ok_service)
            )
        )

        val isRoundUp = switchButton(
            modifier = Modifier.layoutId("roundUpSwitch")
        )

        Button(
            onClick = {
                val cost: Double = if (isNormalCost(text)) text.toDouble() else 0.0
                val percentage: Double = when (selectedService) {
                    AMAZING -> 0.2
                    GOOD -> 0.15
                    OKAY -> 0.12
                    else -> 0.1
                }
                result =
                    if (isRoundUp) ceil(cost * percentage).toString() else (cost * percentage).toString()

            },
            modifier = Modifier
                .layoutId("calculateButton")
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
            modifier = Modifier.layoutId("tipAmount")
        )

    }
}

/**
 *  UI 배치만을 return 해주는 함수. 이곳에서 UI 배치만을 설정한다.
 */
private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val costOfService = createRefFor("costOfService")
        val howWasTheService = createRefFor("howWasTheService")
        val serviceRadiobutton = createRefFor("serviceRadioButton")
        val roundUpSwitch = createRefFor("roundUpSwitch")
        val calculateButton = createRefFor("calculateButton")
        val tipAmount = createRefFor("tipAmount")

        constrain(costOfService) {
            top.linkTo(parent.top, margin = 0.dp)
            start.linkTo(parent.start, margin = 0.dp)
        }

        constrain(howWasTheService) {
            top.linkTo(costOfService.bottom, margin = 0.dp)
            start.linkTo(costOfService.start, margin = 0.dp)
        }

        constrain(serviceRadiobutton) {
            top.linkTo(howWasTheService.bottom, margin = 8.dp)
            start.linkTo(parent.start, margin = 0.dp)
        }

        constrain(roundUpSwitch) {
            top.linkTo(serviceRadiobutton.bottom, margin = 8.dp)
            linkTo(parent.start, parent.end)
        }

        constrain(calculateButton) {
            top.linkTo(roundUpSwitch.bottom, margin = 8.dp)
            linkTo(parent.start, parent.end)
        }

        constrain(tipAmount) {
            top.linkTo(calculateButton.bottom, margin = 8.dp)
            end.linkTo(parent.end, margin = 8.dp)
        }

    }
}


/**
 * RadioGroup
 */
@Composable
fun displayRadioGroup(
    @Suppress("unused") modifier: Modifier,
    serviceOptions: List<String>
): String {
    if (serviceOptions.isNotEmpty()) {
        val (selectedOption, onOptionSelected) = remember {
            mutableStateOf(serviceOptions[0])
        }

        Column {
            serviceOptions.forEach { item ->
                Row(
                    Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = item == selectedOption,
                        onClick = { onOptionSelected(item) })

                    ClickableText(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold)
                            ) { append("  $item  ") }
                        },
                        onClick = {
                            onOptionSelected(item)
                        }
                    )
                }
            }
        }
            return selectedOption
    } else {
        return ""
    }
}



@Composable
fun switchButton(modifier: Modifier = Modifier): Boolean {
    val (checked, onChecked) = remember { mutableStateOf(true) }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(stringResource(R.string.round_up_tip))
        Switch(checked = checked, onCheckedChange = { onChecked(it) })
    }
    return checked

}


@Preview(showBackground = true)
@Composable
fun TipTimePreview() {
    TipTimeScreen()
}
