package team.gaeul.weeklymanager

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import team.gaeul.weeklymanager.data.Schedule

class ScheduleViewModel: ViewModel() {

    // State: 스케쥴 리스트
    var scheduleItems = mutableStateListOf<Schedule>()
        private set

    fun addItem(item: Schedule){
        scheduleItems.add(item)
    }


}