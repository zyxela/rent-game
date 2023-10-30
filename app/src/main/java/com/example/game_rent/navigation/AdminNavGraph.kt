package com.example.game_rent.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.game_rent.admin.screens.AdminCatalog
import com.example.game_rent.admin.screens.Exit
import com.example.game_rent.admin.screens.OrderList

@Composable
fun AdminNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.OrderListScreen.route) {
        composable(Screen.OrderListScreen.route) {
            OrderList(navController = navController)
        }

        composable(Screen.ExitScreen.route) {
            Exit(navController = navController)
        }
        composable(Screen.AdminCatalogScreen.route) {
            AdminCatalog(navController = navController)
        }
        composable(Screen.Authorization.route){
            val ng = rememberNavController()
            UserNavGraph(navController = ng)
        }
    }
}