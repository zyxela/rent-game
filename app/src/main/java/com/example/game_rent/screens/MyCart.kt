package com.example.game_rent.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.game_rent.data_classes.CatalogItem
import com.example.game_rent.navigation.BottomNavigationBar

@Composable
fun MyCart(navController: NavHostController) {
    val list = mutableListOf<CatalogItem>(
        CatalogItem("God of War", 30.0),
        CatalogItem("Call of Duty", 70.0),
        CatalogItem("Cyberpunk", 100.0),
        CatalogItem("GTA 6", 130.0),
        CatalogItem("WoT: Blitz", 15.0), CatalogItem("God of War", 30.0),
        CatalogItem("Call of Duty", 70.0)
    )
    val checkedState = remember { mutableStateOf(MutableList(list.count()) { false }) }
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
                Button(modifier = Modifier.fillMaxWidth(), onClick = {

                }) {
                    Text("Оформить заказ")
                }
            }
            BottomNavigationBar(navController)
        }

    }
}