package team.gaeul.weeklymanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import team.gaeul.weeklymanager.data.Schedule
import java.util.*

@Composable
fun ScheduleScreen(onItemComplete: (Schedule) -> Unit) {
    ScheduleItemInputBackground {
        ScheduleItemEntryInput(
            onItemComplete = onItemComplete
        )
    }
}

@Composable
fun ScheduleItemEntryInput(onItemComplete: (Schedule) -> Unit) {
    val (text, setText) = remember { mutableStateOf("") }
    val (star, setStar) = remember { mutableStateOf(3) }
    val detailVisible = text.isNotBlank()
    val submit: () -> Unit = {
        onItemComplete(Schedule(text, star, Date(1)))
        setText("")
        setStar(3)
    }
    ScheduleItemInput(
        text = text,
        onTextChange = setText,
        star = star,
        onStarChange = setStar,
        submit = submit,
        detailVisible = detailVisible
    ) {
        Text(
            text = stringResource(R.string.importance),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(CenterVertically)
        )
    }
}

@Composable
fun ScheduleItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    star: Int,
    onStarChange: (Int) -> Unit,
    submit: () -> Unit,
    detailVisible: Boolean,
    buttonSlot: @Composable RowScope.() -> Unit
) {
    Row() {
        buttonSlot()
        CountingStar(star, onStarChange)
    }


}

@Composable
fun ScheduleInputText(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onImeAction: ImeAction
) {
}