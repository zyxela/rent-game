package com.example.game_rent.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.game_rent.MainScreenView
import com.example.game_rent.screens.Catalog
import com.example.game_rent.screens.MyCart
import com.example.game_rent.screens.Profile

@Composable
fun BottomBarNavigationGraph(navController:NavHostController) {

    NavHost(navController, startDestination = Screen.CatalogScreen.route) {
        composable(Screen.MainView.route) {
            MainScreenView()
        }

        composable(Screen.CatalogScreen.route) {
            Catalog(navController)
        }
        composable(Screen.MyCartScreen.route) {
            MyCart(navController)
        }
        composable(Screen.ProfileScreen.route) {
            Profile(navController)
        }
        composable(Screen.Authorization.route){
            InterstitialGraph()
        }


    }
}