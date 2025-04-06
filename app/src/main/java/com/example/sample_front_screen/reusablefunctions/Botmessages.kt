package com.example.sample_front_screen.reusablefunctions

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.sample_front_screen.R



@Composable
fun BotwithUpperChat(modifier: Modifier=Modifier,text:String){

        ConstraintLayout(modifier) {
            // Create references for message and image
            val (message, image) = createRefs()

            // Message Card - Now positioned above the image
            Box(
                modifier = Modifier
                    .constrainAs(message) {
                        top.linkTo(parent.top)
                        centerHorizontallyTo(parent)

                    }.zIndex(0f)
                    .fillMaxWidth()

            ) {
                Card(
                    modifier = Modifier
                        .width(300.dp)
                        .wrapContentHeight()
                        .heightIn(min = 60.dp)
                        .align(Alignment.BottomCenter)
                        .shadow(elevation = 30.dp),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 16.dp
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 40.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFCCCCCC)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 10.dp, top = 3.dp, end = 3.dp, bottom = 3.dp)

                            .wrapContentHeight()

                    ) {
                        Text(
                            text =text,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Bot Image - Centered horizontally
            Image(
                painter = painterResource(id = R.drawable.botwithmsup),
                contentDescription = "Bot Image",
                modifier = Modifier
                    .size(350.dp)
                    .constrainAs(image) {
                        top.linkTo(message.bottom, margin = (-100.dp))
                        centerHorizontallyTo(parent)
                    }.zIndex(-1f)
            )
        }
    }
@Composable
fun Botwithsidechat(
    modifier: Modifier = Modifier.fillMaxWidth(),
    text: String,
    isRightAligned: Boolean = true // Toggle between left/right alignment
) {
    ConstraintLayout(modifier) {
        val (message, image) = createRefs()

        // Bot Image - Centered vertically, left or right based on alignment
        Image(
            painter = painterResource(id = R.drawable.botwithmsup),
            contentDescription = "Bot Image",
            modifier = Modifier
                .size(350.dp)
                .constrainAs(image) {
                    centerVerticallyTo(parent)
                    if (isRightAligned) {
                        start.linkTo(parent.start) // Bot on the left
                    } else {
                        end.linkTo(parent.end) // Bot on the right (default)
                    }
                }
        )

        // Message Card - Positioned to the right/left of the bot
        Box(
            modifier = Modifier
                .constrainAs(message) {
                    centerVerticallyTo(image)
                    if (isRightAligned) {
                        start.linkTo(image.end, margin = -90.dp) // Chat on right
                    } else {
                        end.linkTo(image.start, margin = 16.dp) // Chat on left
                    }
                }
                .zIndex(1f)
                .widthIn(max = 230.dp) // Limit chat width
        ) {
            Card(
                modifier = Modifier
                    .wrapContentHeight()
                    .shadow(elevation = 10.dp),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isRightAligned) 0.dp else 16.dp,
                    bottomEnd = if (isRightAligned) 16.dp else 0.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFCCCCCC)
                )
            ) {

                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp, top = 5.dp, end = 5.dp, bottom = 5.dp)
                            .heightIn(30.dp)
                            .wrapContentHeight()

                    ) {
                        Text(
                            text =text,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

            }
        }

}

