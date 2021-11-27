package com.example.happybirthday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ConstraintLayoutContent()
        }
    }
}

@Composable
fun ConstraintLayoutContent(){
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    ){

        val (textView1, textView2) = createRefs()

        MyText(
            text = "Happy Birthday, Sam!",
            modifier = Modifier.constrainAs(textView1){
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }
        )

        MyText(
            text = "From Emma",
            modifier = Modifier.constrainAs(textView2){
                bottom.linkTo(parent.bottom, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }
        )
    }
}

@Composable
fun MyText(
    modifier: Modifier = Modifier,
    text: String = ""){
    Text(
        text = text,
        fontSize = 36.sp,
        modifier = modifier,
        fontFamily = FontFamily.SansSerif,
        color = Color.Black
    )
}

@Preview
@Composable
fun Preview(){
    ConstraintLayoutContent()

}
