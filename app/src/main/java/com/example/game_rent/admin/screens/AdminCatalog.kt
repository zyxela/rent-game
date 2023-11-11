package com.example.game_rent.admin.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.game_rent.data.DatabaseInteraction
import com.example.game_rent.data_classes.CatalogItem
import com.example.game_rent.navigation.AdminBottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun AdminCatalog(navController: NavHostController) {

    val di = DatabaseInteraction()
    var catalogList by remember { mutableStateOf<List<CatalogItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        catalogList = di.getCatalog()
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(4.dp)
                .height(660.dp)
        ) {

            items(catalogList.count()) { index ->
                Row(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 7.dp)) {
                    Column {
                        Text(text = catalogList[index].name, fontSize = 22.sp)
                        Text(
                            text = "${catalogList[index].price}",
                            fontSize = 26.sp,
                            fontWeight = FontWeight(600)
                        )
                    }

                }

            }
        }

        var name by remember {
            mutableStateOf("")
        }
        var price by remember {
            mutableStateOf("")
        }
        var description by remember {
            mutableStateOf("")
        }

        var showDialog by remember { mutableStateOf(false) }
        if (showDialog) {
            Dialog(onDismissRequest = {
                val db = DatabaseInteraction()
                db.addCatalogItem(CatalogItem(name, if (price != "") price.toDouble() else 0.0, description))
                showDialog = false
            }) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(375.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        Text(
                            text = "This is a dialog with buttons and an image.",
                            modifier = Modifier.padding(16.dp),
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            // horizontalArrangement = Arrangement.Center,
                        ) {
                            TextField(
                                modifier = Modifier.padding(8.dp),
                                value = name,
                                onValueChange = {
                                    name = it
                                }
                            )
                            TextField(
                                modifier = Modifier.padding(8.dp),
                                value = price.toString(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                onValueChange = {
                                    price = it
                                }
                            )
                            TextField(
                                modifier = Modifier.padding(8.dp),
                                value = "Description",
                                onValueChange = {

                                }
                            )
                        }
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
                .padding(4.dp),
                onClick = {

                    showDialog = true

                }) {
                Text(text = "Добавить")
            }

            AdminBottomNavigationBar(navController)
        }
    }
}