package com.example.sample_front_screen

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.sample_front_screen.ui.theme.Red
import com.example.sample_front_screen.ui.theme.Yellow


@Composable
fun AddictedScreenStart(context: Context) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Black
    ) { paddingValues ->
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            var isVisible = remember { mutableStateOf(true) }
            val (addiction) = createRefs()
            val (assisstant) =createRefs()
            val (clickabletext) = createRefs()
            AssisstantTalkSide("CLICK TO DESTROY the feelings you associate with your ADDICTION , with every click break FREE from these emotions!",modifier=Modifier.constrainAs(assisstant){
                end.linkTo(parent.end, margin = 15.dp)
                top.linkTo(parent.top, margin = 80.dp)
            })
            Image(
                painter = painterResource(id = R.drawable.addiction),
                contentDescription = "App Logo",
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .aspectRatio(1f)
                    .constrainAs(addiction){
                        centerTo(parent)
                    }
            )
            Box(
                modifier = Modifier.size(width = 140.dp, height = 50.dp).constrainAs(clickabletext){
                    bottom.linkTo(assisstant.bottom, margin = 50.dp)
                    end.linkTo(parent.end, margin = 70.dp)},
                contentAlignment = Alignment.Center
            ) {
                PureOval(
                    modifier = Modifier.fillMaxSize(),
                    borderColor = Yellow,
                    borderWidth = 4f
                )
                if (isVisible.value) {
                    Text(text = "Shame",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        color = Red, modifier = Modifier.clickable(

                            onClick = {
                                isVisible.value = !isVisible.value ;
                                playSound(context,R.raw.pop)


                            }),
                    )
                }}
            }




        }
    }

@Composable
fun PureOval(
    modifier: Modifier = Modifier,
    borderWidth: Float = 2f,
    borderColor: Color = Color.Black,
    fillColor: Color = Color.Transparent
) {
    Canvas(
        modifier = modifier
    ) {
        // Draw fill
        drawOval(
            color = fillColor
        )

        // Draw border
        drawOval(
            color = borderColor,
            style = Stroke(width = borderWidth)
        )
    }
}