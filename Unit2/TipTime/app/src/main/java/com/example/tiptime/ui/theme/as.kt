package com.example.tiptime.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiptime.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.Dimension

class SD {
}

@Composable
fun Screen(){
    Column(modifier = Modifier
        .background(Color.White)
        .padding(16.dp)){
        Text(text = "Discover", fontSize = 36.sp, modifier = Modifier.padding(top = 32.dp))
        Text(text = "WHAT'S NEW TODAY", fontSize = 13.sp, modifier = Modifier.padding(top = 32.dp))

        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .height(434.dp)
            .background(Color.Blue), horizontalArrangement = Arrangement.spacedBy(16.dp)){

            (0..5).forEach{
                item{
                    DiscoverCard(owner = "HoltyKnight", message = "Wow, Fantastic baby: $it")
                }
            }
        }
    }
}

@Composable
fun DiscoverCard(owner: String, message: String){
    Column(modifier = Modifier
        .width(IntrinsicSize.Min)
        .background(Color.Yellow)){

        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "profile",
                modifier = Modifier.fillMaxWidth()
            )
            Column{
                Text(text = String.format("@%s", owner), fontSize = 13.sp)
                Text(text = message, fontSize = 11.sp, overflow = TextOverflow.Clip)
            }
        }
        Image(
            painter = painterResource(id = R.drawable._sw___),
            contentDescription = "discover",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    Screen()
}