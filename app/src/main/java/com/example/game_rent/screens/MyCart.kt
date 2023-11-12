package com.example.game_rent.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.game_rent.components.historyItem
import com.example.game_rent.data.DatabaseInteraction
import com.example.game_rent.data_classes.CatalogItem
import com.example.game_rent.data_classes.Order
import com.example.game_rent.navigation.BottomNavigationBar
import com.example.game_rent.navigation.Screen

@Composable
fun MyCart(navController: NavHostController) {

    var list by remember { mutableStateOf<MutableList<CatalogItem>>(mutableListOf()) }

    var executedList by remember { mutableStateOf<MutableList<Order>>(mutableListOf()) }
    var deniedList by remember { mutableStateOf<MutableList<Order>>(mutableListOf()) }

    var history by remember {
        mutableStateOf(false)
    }

    val di = DatabaseInteraction()
    LaunchedEffect(Unit) {
        list = di.getOrders()
        executedList = di.getExecuteOrder()
        deniedList = di.getDeniedOrder()
    }
    var checks = MutableList<Boolean>(list.count()) { false }

    if (history) {
        Dialog(onDismissRequest = { history = false }) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(567.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(executedList.count()) { index ->
                        historyItem(item = executedList[index], color = Color.Green)
                    }
                    items(deniedList.count()) { index ->
                        historyItem(item = deniedList[index], color = Color.Red)

                    }

                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalAlignment = Alignment.End
        ) {

            Icon(modifier = Modifier
                .size(48.dp)
                .clickable {
                    history = true

                }, imageVector = Icons.Filled.History, contentDescription = ""
            )

        }
        LazyColumn(modifier = Modifier.height(610.dp)) {
            items(list.count()) { index ->
                val isChecked =
                    remember { mutableStateOf(false) }

                LaunchedEffect(isChecked.value) {
                    isChecked.value = isChecked.value
                }
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)) {
                    Row {
                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = {
                                isChecked.value = it
                                checks[index] = isChecked.value
                            }
                        )
                        Column {
                            Text(
                                text = list[index].name,
                                fontWeight = FontWeight(550),
                                fontSize = 22.sp
                            )
                            Text(
                                text = list[index].price.toString(),
                                fontWeight = FontWeight(500),
                                fontSize = 18.sp
                            )

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


                Button(modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(166, 207, 152)),
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
                                navController.navigate(Screen.MyCartScreen.route)
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