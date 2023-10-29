package com.example.game_rent.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.game_rent.MainScreenView
import com.example.game_rent.screens.Authorization

@Composable
fun InterstitialGraph(){
    var navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Authorization.route){
        composable(Screen.Authorization.route){
            Authorization(navController = navController)
        }
        composable(Screen.MainView.route){
            MainScreenView()
        }
    }
}