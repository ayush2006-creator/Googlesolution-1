package com.example.sample_front_screen.reusablefunctions
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sample_front_screen.R
import com.example.sample_front_screen.Screens
import com.example.sample_front_screen.fontfunctions.HeadingText
import com.example.sample_front_screen.fontfunctions.SubHeadingText
import com.example.sample_front_screen.ui.theme.SignYellow


@Composable
fun pretaskscreen(title:String="",subheading:String="",taskhead:String="",taskd:String="",taskmessage:String="",buttonm:String,navController: NavController,nextScreen: String?){
    // RIGHT: Background fills entire Box
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)){
        Image(
            painter = painterResource(R.drawable.mainbackground),
            contentDescription = "Background",
            contentScale = ContentScale.Crop, // Ensures full coverage
            modifier = Modifier
                .fillMaxSize()

                    // Ensures image fi
        )

        Box(
            modifier = Modifier.fillMaxSize(),

            ) {
            ConstraintLayout(modifier =Modifier.fillMaxSize()) {
                val Title = createRef()
                val subtitle =   createRef()
                val board = createRef()
                val Taskh = createRef()
                val Taskd=createRef()
                val Taskm = createRef()
                val button = createRef()



                HeadingText(
                    text = title,
                    modifier = Modifier.scale(1.2f).constrainAs(Title){
                        top.linkTo(parent.top, margin = 200.dp)
                        centerHorizontallyTo(parent)

                    })
                SubHeadingText(text = subheading, modifier = Modifier.scale(0.8f).constrainAs(subtitle){
                    top.linkTo(Title.bottom, margin = 5.dp)
                    centerHorizontallyTo(parent)
                })
                Image(painter = painterResource(R.drawable.signboard),
                    contentDescription = "board",Modifier.constrainAs(board){
                        top.linkTo(subtitle.bottom, margin = 80.dp)
                        centerHorizontallyTo(parent)
                    }.scale(1.3f))
                Text(taskhead,fontFamily = FontFamily(Font(R.font.lato)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W900,
                    lineHeight = 23.sp,
                    color = Color.Black,
                    modifier = Modifier.constrainAs(Taskh){
                        top.linkTo(board.top, margin = 50.dp)
                        centerHorizontallyTo(board)
                    }

                )
                Box(modifier = Modifier.constrainAs(Taskd){
                    top.linkTo(Taskh.bottom, margin = 10.dp)
                    centerHorizontallyTo(board)
                }){
                Text(text = taskd,fontFamily = FontFamily(Font(R.font.lato)),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W500,
                    lineHeight = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center))}

                Text(text = taskmessage,fontFamily = FontFamily(Font(R.font.lato)),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 17.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.constrainAs(Taskm){
                        bottom.linkTo(board.bottom, margin = 40.dp)
                        centerHorizontallyTo(board)
                    })
                Button(modifier = Modifier.constrainAs(button){
                    top.linkTo(board.bottom, margin = 100.dp)
                    centerHorizontallyTo(parent)

                }    .height(50.dp)
                    .width(340.dp)
                    , onClick = { nextScreen?.let { navController.navigate(it) } },elevation= ButtonDefaults.elevatedButtonElevation(20.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = SignYellow, // Background color
                        contentColor = Color.Black  // Text color
                    ),
                    shape = RoundedCornerShape(8.dp)
                ){
                    Text(buttonm)
                }


            }}
    }
}
