package com.example.game_rent.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.game_rent.data.DatabaseInteraction
import com.example.game_rent.data_classes.CatalogItem
import com.example.game_rent.data_classes.Order
import com.example.game_rent.navigation.BottomNavigationBar
import com.example.game_rent.navigation.Screen

@Composable
fun MyCart(navController: NavHostController) {

    var list by remember { mutableStateOf<MutableList<CatalogItem>>(mutableListOf()) }


    val di = DatabaseInteraction()
    LaunchedEffect(Unit) {
        list = di.getOrders()
    }
    var checks = MutableList<Boolean>(list.count()) { false }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        LazyColumn(modifier = Modifier.height(680.dp)) {
            items(list.count()) { index ->
                val isChecked =
                    remember { mutableStateOf(false) }

                LaunchedEffect(isChecked.value) {
                    isChecked.value = isChecked.value
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row {
                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = {
                                isChecked.value = it
                                checks[index] = isChecked.value
                            }
                        )
                        Column {
                            Text(text = list[index].name)
                            Text(text = list[index].price.toString())

                        }

                    }
                }
            }
        }
        val context = LocalContext.current
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp), verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp), verticalArrangement = Arrangement.Bottom
            ) {
                Column(horizontalAlignment = Alignment.End) {
                    Button(
                        onClick = {

                        }) {
                        Icon(imageVector = Icons.Filled.History, contentDescription = "")
                    }
                }

                Button(modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        for (i in list.indices) {
                            if (checks[i]) {
                                di.addToListOrder(
                                    Order(
                                        sharedPreferences.getString("address", "")
                                            ?: "groove street",
                                        list[i].name,
                                        sharedPreferences.getString("userName", "") ?: "Customer",
                                        list[i].price
                                    )
                                )
                                navController . navigate (Screen.MyCartScreen.route)
                            }
                        }

                    }) {
                    Text("Оформить заказ")
                }
            }
            BottomNavigationBar(navController)
        }

    }
}