package com.example.compose.rally

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.toUpperCase
import com.example.compose.rally.ui.components.RallyTopAppBar
import com.example.compose.rally.ui.theme.RallyTheme
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()




    @Test
    fun rallyTopAppBarTest_currentLabelExists(){
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTheme {
                RallyTopAppBar(
                    allScreens = allScreens,
                    onTabSelected = {},
                    currentScreen = RallyScreen.Accounts
                )
            }
        }

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")


        composeTestRule
            .onNode(hasText(RallyScreen.Accounts.name.uppercase()) and
            hasParent(
                hasContentDescription(RallyScreen.Accounts.name)
            ),
            useUnmergedTree = true)
            .assertExists()
    }

    @Test
    fun rallyTopAppBarTest_isFirstOverviewScreen(){

        composeTestRule.setContent{
            RallyApp()
        }

        composeTestRule
            .onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()

    }


}