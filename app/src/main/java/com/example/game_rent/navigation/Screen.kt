package com.example.game_rent.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(var title: String? = null, var icon: ImageVector? = null, var route: String) {

    object MainView : Screen(route = "main_view")

    object Authorization : Screen(route = "auth")
    object CatalogScreen : Screen("Каталог", Icons.Filled.List, "catalog")
    object MyCartScreen : Screen("Корзина", Icons.Filled.ShoppingCart, "my_cart")
    object ProfileScreen : Screen("Прифиль", Icons.Filled.Person, "profile")


    //admin
    object AdminCatalogScreen : Screen("Каталог", Icons.Filled.Shop, "admin_catalog")
    object OrderListScreen : Screen("Заказы", Icons.Filled.List, "order_list")

    object ExitScreen : Screen("Выход", Icons.Filled.ExitToApp, "exit")
}
