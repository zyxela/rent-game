package com.example.game_rent.admin.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.game_rent.data.DatabaseInteraction
import com.example.game_rent.data_classes.CatalogItem
import com.example.game_rent.navigation.AdminBottomNavigationBar
import com.example.game_rent.navigation.Screen

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
        modifier = Modifier.fillMaxWidth()
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(4.dp)
                .height(660.dp)
        ) {

            items(catalogList.count()) { index ->
                Row(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 7.dp)) {
                    Column {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(238, 238, 238))
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = catalogList[index].name,
                                fontSize = 22.sp
                            )
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = "${catalogList[index].price}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight(600)
                            )
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        di.removeOrder(catalogList[index].id)
                                        navController.navigate(Screen.AdminCatalogScreen.route)
                                    },
                                    modifier = Modifier.size(70.dp),
                                    shape = CircleShape,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(255, 105, 105)
                                    )
                                ) {

                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "",
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                        }
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

        var uri by remember {
            mutableStateOf<Uri?>(null)
        }

        val singlePhotoPicker =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
                onResult = {
                    uri = it
                })
        val context = LocalContext.current
        var showDialog by remember { mutableStateOf(false) }
        if (showDialog) {
            Dialog(onDismissRequest = {
                val db = DatabaseInteraction()
                db.addCatalogItem(
                    CatalogItem(
                        name, if (price != "") price.toDouble() else 0.0, description
                    )
                )
                uri?.let {
                    DatabaseInteraction.uploadToStorage(uri!!, context, name)
                }

                showDialog = false
                navController.navigate(Screen.AdminCatalogScreen.route)
            }) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(375.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        Text(
                            text = "This is a dialog with buttons and an image.",
                            modifier = Modifier.padding(16.dp),
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),

                            ) {
                            TextField(modifier = Modifier.padding(8.dp),
                                value = name,
                                onValueChange = {
                                    name = it
                                })
                            TextField(modifier = Modifier.padding(8.dp),
                                value = price.toString(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                onValueChange = {
                                    price = it
                                })
                            TextField(modifier = Modifier.padding(8.dp),
                                value = description,
                                onValueChange = {
                                    description = it
                                })


                            Button(onClick = {
                                singlePhotoPicker.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )


                            }) {
                                Text(text = "Добавить изображение")
                            }

                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
                .weight(2f),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp), onClick = {
                showDialog = true

            }) {
                Text(text = "Добавить")
            }

            AdminBottomNavigationBar(navController)
        }
    }
}
