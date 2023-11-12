package com.example.game_rent.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.game_rent.components.CentralBoldText
import com.example.game_rent.components.CentralButton
import com.example.game_rent.components.LoginInputField
import com.example.game_rent.components.PasswordInputField
import com.example.game_rent.data_classes.User
import com.example.game_rent.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Authorization(navController: NavController) {
    val admin = User("", "")
    var click by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        CentralBoldText("Вход")
        val login = LoginInputField()
        val password = PasswordInputField()
        CentralButton(text = "Войти") {
            click = true
        }
        if (click) {
            if (User(login, password) == admin) {
                navController.navigate(Screen.OrderListScreen.route)
            } else {
                navController.navigate(Screen.CatalogScreen.route)
            }
        }

    }

}