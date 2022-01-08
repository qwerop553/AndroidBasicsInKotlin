package team.gaeul.weeklymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import team.gaeul.weeklymanager.ui.theme.WeeklyManagerTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ScheduleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeeklyManagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ScheduleActivityScreen(viewModel)                
                }
            }
        }
    }
}

@Composable
fun ScheduleActivityScreen(scheduleViewModel: ScheduleViewModel){
    
    ScheduleScreen(
        scheduleViewModel::addItem
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeeklyManagerTheme {
        ScheduleActivityScreen(scheduleViewModel = ScheduleViewModel())
    }
}