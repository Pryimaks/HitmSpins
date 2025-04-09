package com.first.hitmspins.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.first.hitmspins.challenges.ChallengeModeScreen
import com.first.hitmspins.mainScreen.LeaderboardScreen
import com.first.hitmspins.mainScreen.MainMenuScreen
import com.first.hitmspins.mainScreen.ProfileScreen
import com.first.hitmspins.mainScreen.SettingsScreen
import com.first.hitmspins.musicSelection.MusicSelectionScreen
import com.first.hitmspins.musicSelection.gameplay.GameOverScreen
import com.first.hitmspins.musicSelection.gameplay.GameplayScreen
import com.first.hitmspins.musiclibrary.MusicLibraryScreen
import com.first.hitmspins.startIntro.OnboardingScreen
import com.first.hitmspins.viewmodel.GameViewModel

@androidx.annotation.RequiresPermission(android.Manifest.permission.VIBRATE)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HitmSpinsApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "onboarding") {

        composable("onboarding") {
            OnboardingScreen(
                onFinish = {
                    navController.navigate("mainMenu") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "gameOverScreen/{title}/{difficulty}/{score}/{combo}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("difficulty") { type = NavType.StringType },
                navArgument("score") { type = NavType.IntType },
                navArgument("combo") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: "Unknown"
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "Easy"
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val combo = backStackEntry.arguments?.getInt("combo") ?: 0
            GameOverScreen(navController, title, difficulty, score, combo)
        }





        composable("mainMenu") {
            MainMenuScreen(
                navController = navController
            )
        }

        composable("challengeMode") {
            ChallengeModeScreen(navController)
        }

        composable("musicLibraryMode") {
            MusicLibraryScreen(navController)
        }

        composable("leaderboardScreen") {
            LeaderboardScreen(navController)
        }

        composable("profileScreen") {
            ProfileScreen(navController)
        }

        composable("settingsScreen"){
            SettingsScreen(navController)
        }

        composable("gameOverScreen")
            { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: "Unknown"
                val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "Easy"
                val score = backStackEntry.arguments?.getInt("score") ?: 0
                val combo = backStackEntry.arguments?.getInt("combo") ?: 0
                GameOverScreen(navController, title, difficulty, score, combo)
            }


        composable("musicSelection") {
            MusicSelectionScreen(
                onBack = { navController.navigate("mainMenu") },
                onTrackSelected = { title, difficulty ->
                    navController.navigate("gameplay/$title/$difficulty")
                },
                navController = navController
            )
        }

        composable("gameplay/{title}/{difficulty}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: ""
            GameplayScreen(navController, title, difficulty)
        }

        composable(
            "gameplay/{title}/{difficulty}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("difficulty") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: ""
            GameplayScreen(navController, title, difficulty)
        }

    }
}
