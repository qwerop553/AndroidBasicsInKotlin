package com.example.lemonade

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.provider.Settings.Global.getString
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.lemonade.ui.theme.LemonadeTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.lang.Thread.sleep

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest : BaseTest() {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            LemonadeTheme {
                LemonadeScreen()
            }
        }
    }

    @Test
    fun `test_initial_state`(){
        sleep(5000)
        testState(composeTestRule, R.string.lemon_select, R.drawable.lemon_tree)
    }

    @Test
    fun `test_picking_lemon_proceeds_to_squeeze_state`(){
        sleep(5000)
        // Click image to progress state
        composeTestRule.onNodeWithContentDescription(R.drawable.lemon_tree.toString())
            .performClick()
        testState(composeTestRule, R.string.lemon_squeeze,  R.drawable.lemon_squeeze)
    }

    @Test
    fun `test_squeezing_lemon_proceeds_to_drink_state`(){
        sleep(5000)
        composeTestRule.onNodeWithContentDescription(R.drawable.lemon_tree.toString())
            .performClick()
        composeTestRule.onNodeWithContentDescription(R.drawable.lemon_squeeze.toString())
            .performClick()
        juiceLemon(composeTestRule)
        testState(composeTestRule, R.string.lemon_drink, R.drawable.lemon_drink)
    }

}

open class BaseTest {
    fun testState(
        composeTestRule: ComposeTestRule,
        @StringRes textResource: Int,
        @DrawableRes drawableResource: Int
    ) {
        composeTestRule.onNode(hasText(getApplicationContext<Context>().getString(textResource)))
            .assertExists()
        composeTestRule.onNode(hasContentDescription(drawableResource.toString()))
            .assertExists()
    }

    fun pickLemon(
        composeTestRule: ComposeTestRule
    ){
        composeTestRule.onNodeWithContentDescription(R.drawable.lemon_tree.toString())
            .performClick()
    }

    fun juiceLemon(composeTestRule: ComposeTestRule){
        val squeezeNode = composeTestRule.onNodeWithContentDescription(R.drawable.lemon_squeeze.toString())
        while (squeezeNode.isPresent()){
            squeezeNode.performClick()
        }
    }

    private fun SemanticsNodeInteraction.isPresent(): Boolean{
        return try {
            assertExists()
            true
        } catch (e: AssertionError){
            false
        }
    }
}

