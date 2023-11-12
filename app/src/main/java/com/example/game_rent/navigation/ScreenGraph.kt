package com.example.game_rent.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.game_rent.admin.screens.AdminCatalog
import com.example.game_rent.admin.screens.Exit
import com.example.game_rent.admin.screens.OrderList
import com.example.game_rent.screens.Authorization
import com.example.game_rent.screens.Catalog
import com.example.game_rent.screens.MyCart
import com.example.game_rent.screens.Profile

@Composable
fun ScreenGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Authorization.route) {
        composable(Screen.OrderListScreen.route) {
            OrderList(navController = navController)
        }

        composable(Screen.ExitScreen.route) {
            Exit(navController = navController)
        }
        composable(Screen.AdminCatalogScreen.route) {
            AdminCatalog(navController = navController)
        }
        composable(Screen.Authorization.route) {
            Authorization(navController = navController)
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

    }
}