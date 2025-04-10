package com.first.hitmspins.mainScreen

import android.Manifest
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import com.first.hitmspins.R
import com.first.hitmspins.music.MusicPlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {
    companion object {
        private val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        private val MUSIC_ENABLED = booleanPreferencesKey("music_enabled")
    }

    val musicEnabledFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[MUSIC_ENABLED] ?: true }

    val soundEnabledFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[SOUND_ENABLED] ?: true }

    suspend fun setSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SOUND_ENABLED] = enabled
        }
        MusicPlayer.setSoundEnabled(enabled)
    }

    suspend fun setMusicEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[MUSIC_ENABLED] = enabled
        }
        MusicPlayer.setMusicEnabled(enabled)
    }
}


@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val settingsManager = remember { SettingsManager(context) }

    val isMusicEnabled by settingsManager.musicEnabledFlow.collectAsState(initial = true)

    val isSoundEnabled by settingsManager.soundEnabledFlow.collectAsState(initial = true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C0C1E))
            .padding(16.dp)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFFFF4081)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "⚙️ Game Settings",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(24.dp))

        SettingItem(
            title = "🎮 Game Sound",
            checked = isSoundEnabled,
            onCheckedChange = { enabled ->
                scope.launch { settingsManager.setSoundEnabled(enabled) }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SettingItem(
            title = "🎵 Background Music",
            checked = isMusicEnabled,
            onCheckedChange = { enabled ->
                scope.launch { settingsManager.setMusicEnabled(enabled) }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = if (isMusicEnabled) "🎵 Music is ON" else "🔇 Music is OFF",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


@Composable
fun SettingItem(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .border(2.dp, Color(0xFFFF4081), RoundedCornerShape(20.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

