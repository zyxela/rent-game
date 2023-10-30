package com.example.game_rent.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.game_rent.MainScreenView
import com.example.game_rent.screens.Authorization

@Composable
fun InterstitialGraph(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainView.route){
        composable(Screen.Authorization.route){
            Authorization(navController = navController)
        }
        composable(Screen.MainView.route){
            MainScreenView()
        }

    }
}