package com.example.diceroller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private var _number1: MutableLiveData<Int> = MutableLiveData(1)
    val number1: LiveData<Int> get() = _number1

    private var _number2: MutableLiveData<Int> = MutableLiveData(-1)
    val number2: LiveData<Int> get() = _number2

    companion object{
        val dice = Dice(6)
    }

    fun rollDice1(){
        _number1.value = dice.roll()
    }

    /**
     * Create Dice 2 when user clicked "New Dice" Button.
     */
    fun makeDice2(){
        _number2.value = 1
    }

    fun deleteDice(){
        _number2.value = -1
    }

    fun rollDice2(){
        _number2.value = dice.roll()
    }
}