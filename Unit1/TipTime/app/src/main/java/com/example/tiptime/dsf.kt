/**
 * This code is from cfusman's code snippet.
 * He is genius
 *
 * https://android--code.blogspot.com/2021/03/jetpack-compose-radio-group-example.html?m=1
 *
 */

package com.example.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainContent()
        }
    }


    @Composable
    fun MainContent(){
        val languageOptions: List<String> = listOf("Java", "Kotlin", "C#")
        val ideOptions: List<String> = listOf(
            "Android Studio", "Visual Studio", "IntelliJ Idea", "Eclipse"
        )

        Column{
            val selectedLanguage = radioGroup(
                radioOptions = languageOptions,
                title = "Which is your most favorite language?",
                cardBackgroundColor = Color(0xFFFFFAF0)
            )

            val selectedIDE = radioGroup(
                radioOptions = ideOptions,
                title = "Which is your most favorite IDE?",
                cardBackgroundColor = Color(0xFFF8F8FF)
            )

            Text(
                text = "Selected : $selectedLanguage & $selectedIDE",
                fontSize = 22.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color(0xFF665D1E)
            )
        }
    }



    @Composable
    fun radioGroup(
        radioOptions: List<String> = listOf(),
        title: String = "",
        cardBackgroundColor: Color = Color(0xFFFEFEFA)
    ):String{
        if (radioOptions.isNotEmpty()){
            val (selectedOption, onOptionSelected) = remember {
                mutableStateOf(radioOptions[0])
            }

            Card(
                backgroundColor = cardBackgroundColor,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
            ) {
                Column(
                    Modifier.padding(10.dp)
                ) {
                    Text(
                        text = title,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(5.dp),
                    )

                    radioOptions.forEach { item ->
                        Row(
                            Modifier.padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (item == selectedOption),
                                onClick = { onOptionSelected(item) }
                            )

                            val annotatedString = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Bold)
                                ){ append("  $item  ") }
                            }

                            ClickableText(
                                text = annotatedString,
                                onClick = {
                                    onOptionSelected(item)
                                }
                            )
                        }
                    }
                }
            }
            return selectedOption
        }else{
            return ""
        }
    }


    @Preview
    @Composable
    fun ComposablePreview(){
        //MainContent()
    }
}

