package com.example.game_rent.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.game_rent.navigation.BottomNavigationBar

@Composable
fun MyCart(navController: NavHostController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Green)) {


        Column(modifier = Modifier.fillMaxSize().padding(0.dp), verticalArrangement = Arrangement.Bottom) {
            BottomNavigationBar(navController)
        }

    }
}