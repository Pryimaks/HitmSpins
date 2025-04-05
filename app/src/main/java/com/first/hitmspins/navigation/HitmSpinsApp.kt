package com.first.hitmspins.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.first.hitmspins.challenges.ChallengeModeScreen
import com.first.hitmspins.mainScreen.MainMenuScreen
import com.first.hitmspins.musicSelection.MusicSelectionScreen
import com.first.hitmspins.musicSelection.gameplay.GameplayScreen
import com.first.hitmspins.musiclibrary.MusicLibraryScreen
import com.first.hitmspins.startIntro.OnboardingScreen

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

        composable("musicSelection") {
            MusicSelectionScreen(
                onBack = { navController.navigate("mainMenu") },
                onTrackSelected = { title, difficulty ->
                    navController.navigate("gameplay/$title/$difficulty")
                },
                navController = navController
            )
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
            GameplayScreen()
        }

    }
}
