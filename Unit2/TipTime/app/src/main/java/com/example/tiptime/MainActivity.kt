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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
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
import java.text.NumberFormat
import kotlin.math.ceil

private const val AMAZING: String = "Amazing (20%)"
private const val GOOD: String = "Good (18%)"
private const val OKAY: String = "Okay (15%)"

// layout Ids
private const val COST_OF_SERVICE: String = "Cost Of Service"
private const val HOW_WAS_THE_SERVICE: String = "How was the service?"
private const val RADIOBUTTON_SATISFACTION = "Satisfaction Radiobutton"
private const val ROUND_UP_TIP = "Round up tip?"
private const val CALCULATE_BUTTON = "Calculate"
private const val TIP_AMOUNT_TEXT = "Tip Amount"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    TipTimeScreen()
                }
            }
        }
    }
}

@Composable
fun TipTimeScreen() {
    BoxWithConstraints {
        if (maxWidth < maxHeight) { // 세로방향
            TipTimeLeftScreen()
        } else { // 가로방향
            Row {
                TipTimeLeftScreen(Modifier.weight(3f))
                TipTimeRightScreen(Modifier.weight(2f))
            }
        }
    }
}

@Composable
private fun TipTimeLeftScreen(modifier: Modifier = Modifier) {

    ConstraintLayout(
        constraintSet = decoupledConstraints(1.dp),
        modifier = modifier
            .padding(16.dp)

    ) {

        // 사용자가 입력한 금액
        val inputCost: String = costOfService(
            modifier = Modifier
                .width(160.dp)
                .layoutId(COST_OF_SERVICE),
        )

        Text(
            text = stringResource(R.string.how_was_the_service),
            modifier = Modifier.layoutId(HOW_WAS_THE_SERVICE)
        )

        val selectedService = displayRadioGroup(
            modifier = Modifier.layoutId(RADIOBUTTON_SATISFACTION),
            serviceOptions = listOf(
                stringResource(R.string.amazing_service),
                stringResource(R.string.good_service),
                stringResource(R.string.ok_service)
            )
        )

        val isRoundUp: Boolean = switchButton(
            modifier = Modifier
                .layoutId(ROUND_UP_TIP)
                .fillMaxWidth()
        )

        var totalTip by rememberSaveable { mutableStateOf("") }

        Button(
            onClick = {
                totalTip = calculateResult(
                    inputCost, selectedService, isRoundUp
                )
            },
            modifier = Modifier
                .layoutId(CALCULATE_BUTTON)
                .fillMaxWidth()
        ) {

            Text(
                text = stringResource(R.string.calculate),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Text(
            text = stringResource(R.string.tip_amount) + " $totalTip ",
            fontStyle = FontStyle.Italic,
            modifier = Modifier.layoutId(TIP_AMOUNT_TEXT)
        )
    }
}

/**
 * 가로배경에서 History 기능을 제공하는 Screen 입니다.
 */
@Composable
private fun TipTimeRightScreen(modifier: Modifier = Modifier) {

}

/**
 * 사용자가 서비스 비용을 입력하는 Composable
 */
@Composable
private fun costOfService(modifier: Modifier): String {

    var cost: String by rememberSaveable { mutableStateOf("") }

    TextField(
        value = cost,
        onValueChange = {
            if (isNormalCost(it)) cost = it
        },
        modifier = modifier,
        label = {
            Text(stringResource(R.string.cost_of_service))
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        )
    )
    return cost
}

/**
 * RadioGroup
 *
 * @return selectedOption 선택한 서비스 옵션을 String 으로 제공합니다.
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

        Column(modifier = modifier) {
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
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(stringResource(R.string.round_up_tip))
        Switch(checked = checked, onCheckedChange = { onChecked(it) })
    }
    return checked
}

/**
 * @param cost 입력된 String
 * @param selectedService 선택된 서비스 만족도
 * @param isRoundUp 반올림 여부
 */
private fun calculateResult(
    cost: String,
    selectedService: String,
    isRoundUp: Boolean
): String {
    val price: Double = cost.toDoubleOrNull() ?: 0.0
    val percentage: Double = when (selectedService) {
        AMAZING -> 0.2
        GOOD -> 0.18
        OKAY -> 0.15
        else -> 0.1
    }
    return if (isRoundUp)
        NumberFormat.getCurrencyInstance().format(
            ceil(
                price * percentage / 1000
            ) * 1000
        )
    else
        NumberFormat.getCurrencyInstance().format(
            ceil(price * percentage)
        )
}

/**
 *  UI 배치만을 return 해주는 함수. 이곳에서 UI 배치만을 설정한다.
 */
private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val costOfService = createRefFor(COST_OF_SERVICE)
        val howWasTheService = createRefFor(HOW_WAS_THE_SERVICE)
        val serviceRadiobutton = createRefFor(RADIOBUTTON_SATISFACTION)
        val roundUpSwitch = createRefFor(ROUND_UP_TIP)
        val calculateButton = createRefFor(CALCULATE_BUTTON)
        val tipAmount = createRefFor(TIP_AMOUNT_TEXT)

        if (margin == 1.dp) {
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
        } else {
            val guideLine = createGuidelineFromStart(fraction = 0.5f)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TipTimePreview() {
    TipTimeScreen()
}
