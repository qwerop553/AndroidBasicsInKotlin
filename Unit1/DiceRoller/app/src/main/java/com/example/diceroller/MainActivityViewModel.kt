package com.example.diceroller

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.currentThread
import java.lang.Thread.sleep

class MainActivityViewModel: ViewModel() {

    // State1
    private var _dice1: MutableLiveData<DiceResult> = MutableLiveData(DiceResult(false, 1))
    val dice1: LiveData<DiceResult> get() = _dice1

    // State2
    private var _dice2: MutableLiveData<DiceResult> = MutableLiveData(DiceResult(false, -1))
    val dice2: LiveData<DiceResult> get() = _dice2

    val rollTime: Int get() = dice1RollTime + dice2RollTime
    private var dice1RollTime: Int = 0
    private var dice2RollTime: Int = 0


    companion object{
        private val dice = Dice(6)
        fun diceRoll(): Int = dice.roll()
    }

    init{
        _dice1.value = DiceResult(!_dice1.value!!.rolled, diceRoll())
    }

    fun rollDice1(){
        // _dice1.value!!.changeNumber(diceRoll())
        _dice1.value = DiceResult(!_dice1.value!!.rolled, diceRoll())
        Log.d("ViewModel", "RollDice1, , eye: ${_dice1.value!!.eye}")
        dice1RollTime++
    }

    /**
     * Create Dice 2 when user clicked "New Dice" Button.
     */
    fun makeDice2(){
        _dice2.value = DiceResult(false, 1)
        Log.d("ViewModel", "MakeDice2, eye: ${_dice2.value!!.eye}")
    }

    fun deleteDice(){
        _dice2.value = DiceResult(false, -1)
        dice2RollTime = 0
    }

    fun rollDice2(){
        _dice2.value = DiceResult(!_dice2.value!!.rolled, diceRoll())
        dice2RollTime++
    }
}

class Dice(private val numSides: Int) {

    fun roll(): Int{
        return (1..numSides).random()
    }
}

data class DiceResult(var rolled: Boolean, var eye: Int)