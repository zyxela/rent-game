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
import com.example.game_rent.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Authorization(navController: NavController) {
    var click by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        CentralBoldText("Вход")
        LoginInputField()
        PasswordInputField()
        CentralButton(text = "Войти") {
            click = true
        }
        if (click) {
            navController.navigate(Screen.MainView.route)
        }

    }

}