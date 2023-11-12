package com.example.game_rent.admin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.game_rent.data.DatabaseInteraction
import com.example.game_rent.data_classes.Order
import com.example.game_rent.navigation.AdminBottomNavigationBar
import com.example.game_rent.navigation.Screen

@Composable
fun OrderList(navController: NavHostController) {

    var list by remember { mutableStateOf<MutableList<Order>>(mutableListOf()) }


    val di = DatabaseInteraction()
    LaunchedEffect(Unit) {
        list = di.getOrderList()
    }
    var checks = MutableList(list.count()) { false }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        LazyColumn(modifier = Modifier.height(660.dp)) {
            items(list.count()) { index ->
                val isChecked =
                    remember { mutableStateOf(false) }

                LaunchedEffect(isChecked.value) {
                    isChecked.value = isChecked.value
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(238, 238, 238))
                ) {
                    Row {
                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = {
                                isChecked.value = it
                                checks[index] = isChecked.value
                            }
                        )
                        Column {
                            Text(text = list[index].userName, fontSize = 14.sp, fontWeight = FontWeight(550))
                            Text(text = list[index].address, fontSize = 14.sp,fontWeight = FontWeight(550))
                            Text(text = list[index].game, fontSize = 14.sp,fontWeight = FontWeight(550))
                            Text(text = list[index].price.toString(), fontSize = 14.sp,fontWeight = FontWeight(550))

                        }

                    }
                }
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            verticalArrangement = Arrangement.Bottom,

            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp),
                verticalArrangement = Arrangement.Bottom,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color(166, 207, 152)),
                        modifier = Modifier.weight(1f),
                        onClick = {
                            for (i in list.indices) {
                                if (checks[i]) {
                                    di.removeFromOrderList(list[i].id)
                                    di.addToExecutedOrders(list[i])
                                    navController.navigate(Screen.OrderListScreen.route)
                                }
                            }

                        }) {
                        Text("Выполнить заказ")
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(250, 112, 112)),
                        onClick = {
                            for (i in list.indices) {
                                if (checks[i]) {
                                    di.removeFromOrderList(list[i].id)
                                    di.addToDeniedOrders(list[i])
                                    navController.navigate(Screen.OrderListScreen.route)
                                }
                            }
                        }) {
                        Text(text = "Отменить")
                    }
                }

            }

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                AdminBottomNavigationBar(navController)
            }
        }
    }
}