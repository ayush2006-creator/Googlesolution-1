package com.example.sample_front_screen.lighttower

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.sample_front_screen.R

@Composable
fun milesign(no:String,title1:String,date:String){
    ConstraintLayout() {
        val sno = createRef()
        val title = createRef()
        val number = createRef()
        Image(painter = painterResource(R.drawable.milesign), contentDescription = "mileboard")
        Text(no,Modifier.constrainAs(sno){
            centerHorizontallyTo(parent, bias = 0.45f)
            top.linkTo(parent.top, margin = 5.dp)
        }, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text(title1,Modifier.constrainAs(title){
            centerHorizontallyTo(parent, bias = 0.45f)
            top.linkTo(sno.bottom, margin = (5.dp))
        }, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Text(date,Modifier.constrainAs(number){
            centerHorizontallyTo(parent, bias = 0.45f)
            top.linkTo(title.bottom, margin = (0.dp))
        }, fontSize = 10.sp, fontWeight = FontWeight.Bold)

    }

}
