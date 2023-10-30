package com.example.game_rent.navigation



import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AdminBottomNavigationBar(navController: NavController) {
    val bottomItems = listOf(
        Screen.AdminCatalogScreen,
        Screen.OrderListScreen,
        Screen.ExitScreen
    )


    BottomNavigation(
        backgroundColor = Color(232, 243, 214),
        contentColor = Color.Cyan
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        bottomItems.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon!!, contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title!!,
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = false,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)

                }
            )
        }
    }
}