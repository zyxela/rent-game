package com.example.game_rent.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavHostController?) {
    var name = remember {
        mutableStateOf("User")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = name.value,
            onValueChange = {
                name.value = it
            }
        )
    }
}

