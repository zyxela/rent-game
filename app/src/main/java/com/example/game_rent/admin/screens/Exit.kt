package com.example.game_rent.admin.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.game_rent.navigation.AdminBottomNavigationBar
import com.example.game_rent.navigation.Screen

@Composable
fun Exit(navController: NavHostController) {
    val click = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Вы являетесь\nАдминистратором", fontSize = 36.sp)
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),

            border = BorderStroke(2.dp, Color.Red),
            onClick = {

                click.value = true
            }) {
            Text(color = Color.Red, text = "Выход")
        }
        AdminBottomNavigationBar(navController)
    }


    if (click.value) {
        navController.navigate(Screen.Authorization.route)
    }
}