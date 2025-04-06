import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.sample_front_screen.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideMenuLayout() {
    var isMenuOpen by remember { mutableStateOf(false) }
    Image(
        painter = painterResource(R.drawable.home_page_back),
        contentDescription = "Background",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.size(
            width = LocalConfiguration.current.screenWidthDp.dp,
            height = LocalConfiguration.current.screenHeightDp.dp
        ).scale(scaleX = 1.024f, scaleY = 1.028f)
    )


    ConstraintLayout(Modifier.fillMaxSize()) {
        val menubutton = createRef()
        val text = createRef()
        val mile = createRef()
        val tree1 = createRef()
        val tree2  = createRef()
        val tree3 = createRef()
        val icons = createRef()
        val quote = createRef()
        FilledIconButton(
            onClick = {isMenuOpen = !isMenuOpen },
            modifier = Modifier
                .constrainAs(menubutton) {
                    top.linkTo(parent.top, margin = 15.dp)
                    end.linkTo(parent.end, margin = 30.dp)
                }
                .scale(scaleX = 1.5f ,scaleY=1F),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.White.copy(alpha = 0.3f),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(40),

            ) {
            Icon(
                painter = painterResource(R.drawable.menum),
                contentDescription = "menu"
            )
        }

        Text ("Miles Covered...",Modifier.constrainAs(text){
            centerHorizontallyTo(parent,bias= 0.20f)
            top.linkTo(menubutton.bottom, margin = (-5.dp))
        }, fontSize = 23.sp, fontWeight = FontWeight.ExtraBold)
        Row(
            modifier = Modifier.constrainAs(mile) {
                top.linkTo(text.bottom)
                centerHorizontallyTo(parent)
            },
            horizontalArrangement = Arrangement.spacedBy(10.dp) // Adds space between cards
        ) {
            Card(
                modifier = Modifier
                    .size(75.dp)
                    .padding(4.dp)
                    , colors = CardDefaults.cardColors(containerColor = Color(0x80D4D4D4),
                    contentColor=(Color.Black))// Adds internal padding to the card
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("15", fontSize = 35.sp,fontWeight = FontWeight.ExtraBold)
                    Text("Days", fontSize = 12.sp, textAlign = TextAlign.Center)
                }
            }

            Card(
                modifier = Modifier
                    .size(75.dp)
                    .padding(4.dp)
                , colors = CardDefaults.cardColors(containerColor = Color(0x80D4D4D4),
                    contentColor=(Color.Black))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("15", fontSize = 35.sp,fontWeight = FontWeight.ExtraBold)
                    Text("Hours", fontSize = 12.sp, textAlign = TextAlign.Center)
                }
            }

            Card(
                modifier = Modifier
                    .size(75.dp)
                    .padding(4.dp),
                 colors = CardDefaults.cardColors(containerColor = Color(0x80D4D4D4),
                    contentColor=(Color.Black))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("15", fontSize = 35.sp,fontWeight = FontWeight.ExtraBold)
                    Text("Minutes", fontSize = 12.sp, textAlign = TextAlign.Center)
                }
            }

            Card(
                modifier = Modifier
                    .size(75.dp)
                    .padding(4.dp)
                , colors = CardDefaults.cardColors(containerColor = Color(0x80D4D4D4),
                    contentColor=(Color.Black))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("15", fontSize = 35.sp,fontWeight = FontWeight.ExtraBold)
                    Text("Seconds", fontSize = 12.sp, textAlign = TextAlign.Center)
                }
            }
        }
        Row(
            modifier = Modifier
                .constrainAs(icons) {
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                    start.linkTo(parent.start, margin = 6.9.dp)

                }
                .fillMaxWidth(0.95f)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Home
            NavigationItem(
                icon = R.drawable.home,
                label = "Home",
                onClick = { /* Handle click */ }
            )

            // Community
            NavigationItem(
                icon = R.drawable.community,
                label = "Community",
                onClick = { /* Handle click */ }
            )

            // My Tasks
            NavigationItem(
                icon = R.drawable.task,
                label = "My Tasks",
                onClick = { /* Handle click */ }
            )

            // My Goals
            NavigationItem(
                icon = R.drawable.goal,
                label = "My Goals",
                onClick = { /* Handle click */ }
            )

            // Talk to Me
            NavigationItem(
                icon = R.drawable.chat,
                label = "Talk To Me",
                onClick = { /* Handle click */ }
            )

            // My Progress
            NavigationItem(
                icon = R.drawable.progress,
                label = "My Progress",
                onClick = { /* Handle click */ }
            )
        }
        TiltedClickableText("MY PROGRESS", onClick = {}, rotationDegrees = -10f,modifier =Modifier.constrainAs(tree1){
            top.linkTo(mile.bottom, margin = (-5.dp))
            end.linkTo(parent.end,margin=24.dp)

        }.scale(0.85f))
        TiltedClickableText("MY TASKS", onClick = {}, rotationDegrees = 13f,modifier =Modifier.constrainAs(tree2){
            top.linkTo(tree1.bottom, margin = (32.dp))
            end.linkTo(parent.end,margin=37.dp)

        })
        TiltedClickableText("DISTRACT ME", onClick = {}, rotationDegrees = -4f,modifier =Modifier.constrainAs(tree3){
            top.linkTo(tree2.bottom, margin = (26.dp))
            end.linkTo(parent.end,margin=37.dp)

        })
        Text("A scientific fact about addiction", color = Color.White, fontWeight =FontWeight.Bold, fontSize = 20.sp ,modifier =Modifier.constrainAs(quote){
            bottom.linkTo(icons.top, margin = 40.dp)
            centerHorizontallyTo(parent)
        })

        }






        // Animated overlay and menu
        AnimatedSideMenu(isMenuOpen) { isMenuOpen = false }
    }


@Composable
fun AnimatedSideMenu(isOpen: Boolean, onClose: () -> Unit) {
    // Animate the menu width (0dp to 240dp)
    val menuWidth by animateDpAsState(
        targetValue = if (isOpen) 260.dp else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )

    // Animate overlay opacity (0f to 0.5f)
    val overlayAlpha by animateFloatAsState(
        targetValue = if (isOpen) 0.5f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Box(Modifier.fillMaxSize()) {
        // Semi-transparent overlay with fade animation
        if (isOpen || overlayAlpha > 0f) {
            Surface(
                color = Color.Black.copy(alpha = overlayAlpha),
                modifier = Modifier.fillMaxSize(),
                onClick = onClose
            ) {}
        }

        // Menu with slide-in animation
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(menuWidth)
                .align(Alignment.CenterEnd),
            color = Color(0xFF5B508E),
            shadowElevation = 16.dp
        ) {
            if (isOpen) {

                Column(
                    modifier = Modifier
                        .width(350.dp)
                        .background(Color(0xFF5B508E))
                ) {
                    // Header with full-width image and left-aligned close button
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(R.drawable.yellowlight),
                            contentDescription = "yellowlight",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .align(Alignment.TopCenter)
                                .scale(scaleX = 1.45f, scaleY = 1.2f)


                        )
                        Image(
                            painter = painterResource(R.drawable.quote_wala),
                            contentDescription = "Motivational quote",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .align(Alignment.Center)
                                .padding(top = 35.dp)

                        )

                        FilledIconButton(
                            onClick = onClose ,
                            modifier = Modifier.padding(start = 10.dp,top=10.dp)


                                .scale(scaleX = 1.15f ,scaleY=0.9f),
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = Color.White.copy(alpha = 0.3f),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(40),

                            ) {
                            Icon(
                                painter = painterResource(R.drawable.back),
                                contentDescription = "Back button"
                            )
                        }
                    }

                    // Menu items with modern ripple effect
                    val menuItems = listOf(
                        Pair(R.drawable.community, "Community"),
                        Pair(R.drawable.task, "My Tasks"),
                        Pair(R.drawable.goal, "My Goals"),
                        Pair(R.drawable.chat, "Talk To Me"),
                        Pair(R.drawable.progress, "My Progress"),
                        Pair(R.drawable.smilie, "Distract Me"),
                        Pair(R.drawable.recoverygarden, "My Recovery Garden"),
                        Pair(R.drawable.milestones, "Milestones"),
                        Pair(R.drawable.lighttowericon, "Light House")
                    )

                    menuItems.forEach { (iconRes, text) ->
                        var isHovered by remember { mutableStateOf(false) }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .background(
                                    when {
                                        isHovered -> MaterialTheme.colorScheme.surfaceVariant.copy(
                                            alpha = 0.3f
                                        )

                                        else -> Color.Transparent
                                    }
                                )
                                .clickable { /* Handle click */ }
                                .indication(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = LocalIndication.current
                                )
                                .padding(horizontal = 16.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = { isHovered = true },

                                        onTap = { isHovered = false }
                                    )
                                }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Image(
                                    painter = painterResource(iconRes),
                                    contentDescription = text,
                                    modifier = Modifier.size(24.dp),
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = text,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
                }


                    // Menu items

                    }
                }
            }


@Composable
fun NavigationItem(icon: Int, label: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    // Track pressed state for custom indication
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor = if (isPressed) Color.White.copy(alpha = 0.2f) else Color.Transparent

    Column(
        modifier = Modifier
            .width(60.dp) // Equal width for all items
            .background(color = backgroundColor, shape = RoundedCornerShape(4.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null, // No default indication
                onClick = onClick
            ) // Make whole area clickable
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 9.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = Color.White
        )
    }
}
@Preview
@Composable
fun PreviewSideMenuLayout() {
    SideMenuLayout()
}
@Composable
fun TiltedClickableText(
    text: String,
    rotationDegrees: Float = -15f,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    pressedScale: Float = 0.95f
) {
    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) pressedScale else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> isPressed = true
                is PressInteraction.Release -> isPressed = false
                is PressInteraction.Cancel -> isPressed = false
            }
        }
    }

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null, // No ripple effect
                onClick = onClick
            )
            .graphicsLayer {
                rotationZ = rotationDegrees
                scaleX = scale
                scaleY = scale
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle.copy(
                fontSize = 12.5.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}