package com.example.game_rent.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.game_rent.navigation.BottomNavigationBar
import com.example.game_rent.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    var name by remember { mutableStateOf(value = sharedPreferences.getString("userName", "User")) }

    var address by remember {
        mutableStateOf(
            value = sharedPreferences.getString(
                "address",
                "ул. Пушкина, д. Калатушкина"
            )
        )
    }


    var click = remember {
        mutableStateOf(false)
    }


    DisposableEffect(name, address) {

        val editor = sharedPreferences.edit()
        editor.putString("userName", name)
        editor.putString("address", address)
        editor.apply()


        onDispose { }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = name!!,
            onValueChange = {
                name = it
            }
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = address!!,
            onValueChange = {
                address = it
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(4.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(),

                border = BorderStroke(2.dp, Color.Red),
                onClick = {
                    click.value = true
                }) {
                Text(color = Color.Red, text = "Выход")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            BottomNavigationBar(navController)
        }

    }

    if (click.value) {
        navController.navigate(Screen.Authorization.route)
    }

}


