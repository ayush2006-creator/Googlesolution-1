package com.example.sample_front_screen.fontfunctions

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.sp
import com.example.sample_front_screen.R
import com.example.sample_front_screen.ui.theme.DarkYellow
import com.example.sample_front_screen.ui.theme.Yellow

@Composable
fun HeadingText(text: String, modifier: Modifier = Modifier, fontsize: Double =35.51) {
    Text(
        text = text,
        fontSize =fontsize.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 37.sp,
        fontFamily = FontFamily(Font(R.font.poppinsld)),
        modifier = modifier,
        color = Yellow

    )
}
@Composable
fun SubHeadingText(text: String, modifier: Modifier = Modifier,fontsize: Double =20.00) {
    Text(
        text = text,
        fontSize = fontsize.sp,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 23.sp,
        fontFamily = FontFamily(Font(R.font.lato)),
        modifier = modifier,
        color = DarkYellow,
        textAlign = TextAlign.Center,


    )
}