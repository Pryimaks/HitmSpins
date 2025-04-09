package com.first.hitmspins.music

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import com.first.hitmspins.R
import com.first.hitmspins.mainScreen.SettingsManager

object MusicPlayer {
    private var mediaPlayer: MediaPlayer? = null
    private var currentPosition: Int = 0
    private var isSoundEnabled = true
    private var isPrepared = false
    private var isInitialized = false

    fun setSoundEnabled(enabled: Boolean) {
        isSoundEnabled = enabled
        mediaPlayer?.setVolume(if (enabled) 1f else 0f, if (enabled) 1f else 0f)
    }
    private var currentResId: Int = -1
    fun initialize(context: Context, resId: Int) {
        if (currentResId == resId && mediaPlayer != null) return

        mediaPlayer?.release()
        currentResId = resId
        mediaPlayer = MediaPlayer.create(context, resId).apply {
            isLooping = true
            if (isSoundEnabled) start()
        }
    }

    fun setSoundEnabledf(enabled: Boolean) {
        isSoundEnabled = enabled
        mediaPlayer?.setVolume(if (enabled) 1f else 0f, if (enabled) 1f else 0f)
    }

    fun initializef(context: Context, resId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, resId).apply {
            setVolume(if (isSoundEnabled) 1f else 0f, if (isSoundEnabled) 1f else 0f)
            isLooping = false
        }
    }

    fun play() {
        if (isSoundEnabled) {
            mediaPlayer?.start()
        }
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun getCurrentPosition() = mediaPlayer?.currentPosition ?: 0
    fun getDuration() = mediaPlayer?.duration ?: 1
    fun isPlaying() = mediaPlayer?.isPlaying ?: false

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
    private var isMusicEnabled = true

    fun setMusicEnabled(enabled: Boolean) {
        isMusicEnabled = enabled
        if (enabled) {
            mediaPlayer?.start()
        } else {
            mediaPlayer?.pause()
        }
    }

    fun playGameSoundIfEnabled(soundId: Int) {
        if (isSoundEnabled) {
            // грай звук
        }
    }
}