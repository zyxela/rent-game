package com.example.game_rent.admin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.game_rent.data_classes.CatalogItem
import com.example.game_rent.navigation.AdminBottomNavigationBar

@Composable
fun AdminCatalog(navController: NavHostController) {
    val list = mutableListOf<CatalogItem>(
        CatalogItem("God of War", 30.0),
        CatalogItem("Call of Duty", 70.0),
        CatalogItem("Cyberpunk", 100.0),
        CatalogItem("GTA 6", 130.0),
        CatalogItem("WoT: Blitz", 15.0), CatalogItem("God of War", 30.0),
        CatalogItem("Cyberpunk", 100.0),
        CatalogItem("GTA 6", 130.0),
        CatalogItem("WoT: Blitz", 15.0),
        CatalogItem("God of War", 30.0),
        CatalogItem("Call of Duty", 70.0),
        CatalogItem("Cyberpunk", 100.0)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(4.dp)
                .height(660.dp)
        ) {

            items(list.count()) { index ->
                Row(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 7.dp)) {
                    Column {
                        Text(text = list[index].name, fontSize = 22.sp)
                        Text(
                            text = "${list[index].price}",
                            fontSize = 26.sp,
                            fontWeight = FontWeight(600)
                        )
                    }

                }

            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
                .weight(2f), verticalArrangement = Arrangement.Bottom
        ) {
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp), onClick = {

            }) {
                Text(text = "Добавить")
            }

            AdminBottomNavigationBar(navController)
        }
    }
}