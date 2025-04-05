package com.first.hitmspins.music

import android.content.Context
import android.media.MediaPlayer

object MusicPlayer {
    var mediaPlayer: MediaPlayer? = null
        private set

    private var currentPosition: Int = 0

    fun play(context: Context, resId: Int) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, resId).apply {
                isLooping = false
                seekTo(currentPosition)
                start()
            }
        } else {
            mediaPlayer?.apply {
                seekTo(currentPosition)
                start()
            }
        }
    }

    fun pause() {
        mediaPlayer?.let {
            currentPosition = it.currentPosition
            it.pause()
        }
    }

    fun stop() {
        mediaPlayer?.apply {
            stop()
            release()
        }
        mediaPlayer = null
        currentPosition = 0
    }
}
