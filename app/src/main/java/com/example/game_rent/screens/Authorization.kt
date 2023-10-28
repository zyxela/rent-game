package com.example.game_rent.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.game_rent.components.CentralBoldText
import com.example.game_rent.components.CentralButton
import com.example.game_rent.components.LoginInputField
import com.example.game_rent.components.PasswordInputField

@Composable
fun Authorization(){
    Column {
        CentralBoldText("Вход")
        LoginInputField()
        PasswordInputField()
        CentralButton(text = "Войти") {
            
        }
    }
}