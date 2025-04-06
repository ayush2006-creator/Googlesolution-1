package com.example.sample_front_screen.lighttower

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController

import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.sample_front_screen.R
import com.example.sample_front_screen.Screens
import com.example.sample_front_screen.ui.theme.SignYellow
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

@Composable
fun SplashScreen(navController: NavController,homepage: Boolean) {
    // Animation states
    var cameravisible by remember { mutableStateOf(false) }
    var scale by remember { mutableStateOf(4.5f) }
    var opacity by remember { mutableStateOf(1f) }
    var verticalOffset by remember { mutableStateOf(0.dp) }
    var visibility by remember { mutableStateOf(false) }
    var isClickEnabled by remember { mutableStateOf(false) }
    var hasClicked by remember { mutableStateOf(false) }

    // First Animation - Initial zoom in and downward movement
    LaunchedEffect(Unit) {
        delay(300)

        // First stage: Zoom from 4.5f to 13f while moving down
        animate(
            initialValue = 4.5f,
            targetValue = 13f,
            animationSpec = tween(8000, easing = FastOutSlowInEasing)
        ) { value, _ ->
            scale = value
            verticalOffset = (value - 4.5f) * 110.dp / (13f - 4.5f)
        }

        visibility = !homepage
        isClickEnabled = true
    }

    // Second Animation - After click (continues zooming and moving down)
    LaunchedEffect(hasClicked) {
        if (hasClicked) {
            visibility = false

            // Second stage: Continue zooming from 13f to 32f while moving further down
            animate(
                initialValue = scale,
                targetValue = 120f,
                animationSpec = tween(8000, easing = FastOutSlowInEasing)
            ) { value, _ ->
                scale = value
                // Continue moving downward (starting from 110.dp)
                verticalOffset = 110.dp + (value - 13f) * 660.dp / (35f - 13f)
            }

            // Fade out


            delay(500)
            cameravisible = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable(enabled = isClickEnabled && !hasClicked) {
                hasClicked = true
                isClickEnabled = false
            },
        contentAlignment = Alignment.Center
    ) {
        // Lighthouse Image
        Image(
            painter = painterResource(R.drawable.lighttower),
            contentDescription = "Lighthouse",
            modifier = Modifier
                .size(200.dp)
                .offset(y = verticalOffset)
                .scale(scale)
                .alpha(opacity)
        )

        // Visibility content
        if (visibility) {
            Image(
                painter = painterResource(R.drawable.rectangle_31),
                contentDescription = "Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Card(
                modifier = Modifier
                    .width(300.dp)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(Color(0xFFCCCCCC))
            ) {
                Text(
                    text = "Warrior, the path ahead may be\n uncertain, but in the distance, a\n light calls out to you.\n" +
                            "It’s your Recovery Tower, always\n guiding you through the storms.\n" +
                            "\n" +
                            "CLICK TO MEET THIS LIGHT\n INSIDE YOUR TOWER",
                    modifier = Modifier.padding(12.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Camera visible content - now integrated as an element with transparent background
        if (cameravisible) {
            // We add the ImagePickerContent with no background
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ImagePickerContent(true,navController)
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImagePickerContent(homepage:Boolean,navController: NavController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Create a temporary file for storing camera images
    val tempImageFile = remember {
        try {
            File.createTempFile(
                "temp_image_",
                ".jpg",
                context.cacheDir
            ).apply {
                deleteOnExit()
            }
        } catch (e: IOException) {
            null
        }
    }

    // Create the camera image URI
    val cameraImageUri = remember(tempImageFile) {
        tempImageFile?.let {
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                it
            )
        }
    }

    // Permission state
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    // Gallery Launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        bitmap = null
    }

    // Camera Launcher - using TakePicture instead of TakePicturePreview
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success && cameraImageUri != null) {
            imageUri = cameraImageUri
            // Convert URI to bitmap in a coroutine to avoid main thread IO
            lifecycleOwner.lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        val inputStream = context.contentResolver.openInputStream(cameraImageUri)
                        val bmp = BitmapFactory.decodeStream(inputStream)
                        withContext(Dispatchers.Main) {
                            bitmap = bmp?.asImageBitmap()
                        }
                    } catch (e: Exception) {
                        Log.e("ImagePickerScreen", "Error loading camera image", e)
                    }
                }
            }
        }
    }

    // Using Column instead of ConstraintLayout for simpler layout
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Display selected image
        Card(
            modifier = Modifier
                .width(210.dp)
                .height(120.dp),
            shape = RoundedCornerShape(18.dp)
        ) {
            when {
                imageUri != null -> {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(context)
                                .data(imageUri)
                                .crossfade(true)
                                .build()
                        ),
                        contentDescription = "Selected image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                bitmap != null -> {
                    Image(
                        bitmap = bitmap!!,
                        contentDescription = "Captured image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(R.drawable.photo),
                                contentDescription = "add a photo",
                                modifier = Modifier.scale(1.4f)
                            )
                            Text(
                                "CAPTURE YOUR REASON to quit,\n the light that keep you on track",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
        if (homepage){
        // Buttons row
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Button(
                modifier = Modifier
                    .height(40.dp)
                    .width(130.dp)
                    .scale(scaleY = 0.8f, scaleX = 1f),
                onClick = {
                    galleryLauncher.launch("image/*")
                },
                elevation = ButtonDefaults.elevatedButtonElevation(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SignYellow,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Gallery")
            }

            Button(
                modifier = Modifier
                    .height(40.dp)
                    .width(130.dp)
                    .scale(scaleY = 0.8f, scaleX = 1f),
                onClick = {
                    when {
                        cameraPermissionState.status.isGranted -> {
                            cameraImageUri?.let {
                                cameraLauncher.launch(it)
                            }
                        }
                        else -> {
                            cameraPermissionState.launchPermissionRequest()
                        }
                    }
                },
                elevation = ButtonDefaults.elevatedButtonElevation(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SignYellow,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Camera")
            }
        }}

        Text(
            "OKAY",
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,

                    // This removes the ripple effect
                ) {
                    navController.navigate(Screens.dashboard1.route  )
                },
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}





fun Uri.toFile(context: Context): File {
    val inputStream = context.contentResolver.openInputStream(this)
    val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
    inputStream?.use { input ->
        tempFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return tempFile
}