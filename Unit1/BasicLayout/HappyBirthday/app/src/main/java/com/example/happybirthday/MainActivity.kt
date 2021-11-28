package com.example.happybirthday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){

        val (background, textView1, textView2) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.androidparty),
            contentDescription = "background",
            modifier = Modifier.constrainAs(background){
                top.linkTo(parent.top, 0.dp)
                bottom.linkTo(parent.bottom, 0.dp)
                start.linkTo(parent.start, 0.dp)
                end.linkTo(parent.end, 0.dp)
            },
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )

        MyText(
            text = stringResource(R.string.happy_birthday_text),
            modifier = Modifier.constrainAs(textView1){
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }
        )

        MyText(
            text = stringResource(R.string.signature_text),
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
