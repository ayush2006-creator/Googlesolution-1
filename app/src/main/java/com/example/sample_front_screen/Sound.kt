package com.example.sample_front_screen

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.RowScope

fun playSound(context: Context, soundResId: Int) {
    val mediaPlayer = MediaPlayer.create(context, soundResId)
    mediaPlayer?.start()

    mediaPlayer?.setOnCompletionListener {
        it.release() // Release resources after playing
    }
}
