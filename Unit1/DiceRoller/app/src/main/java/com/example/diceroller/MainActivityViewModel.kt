package com.example.diceroller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private var _number: MutableLiveData<Int> = MutableLiveData(1)
    val number: LiveData<Int> get() = _number

    companion object{
        val dice = Dice(6)
    }

    fun rollDice(){
        _number.value = dice.roll()
    }
}