package com.example.game_rent.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.game_rent.data.DatabaseInteraction
import com.example.game_rent.data_classes.CatalogItem
import com.example.game_rent.data_classes.Order
import com.example.game_rent.navigation.BottomNavigationBar
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Catalog(navController: NavHostController) {

    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchText,
            onValueChange = {
                searchText = it
            },
            placeholder = { Text("Поиск") },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            })

        val di = DatabaseInteraction()
        var list by remember { mutableStateOf<List<CatalogItem>>(emptyList()) }
        var image by remember {
            mutableStateOf<Bitmap?>(null)
        }

        LaunchedEffect(Unit) {
            list = di.getCatalog()
        }
        val filteredList: List<CatalogItem> = if (searchText != "") {
            remember(searchText) {
                list.filter { it.name.contains(searchText, ignoreCase = true) }
            }
        } else {
            list
        }

        var showDialog by remember {
            mutableStateOf(false)
        }
        var showenItem by remember {
            mutableStateOf(CatalogItem("", 0.0, ""))
        }
        if (showDialog) {
            LaunchedEffect(Unit) {
                //image = di.getImage("gta6.jpg")
                val ref = Firebase.storage.reference.child("images/gta6.jpg")
                val localFile = File.createTempFile("images", "jpg")
                 ref.getFile(localFile).addOnSuccessListener {
                     image = BitmapFactory.decodeFile(localFile.absolutePath)
                }.addOnFailureListener {
                    // Handle any errors
                }
            }
            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    Card(
                        modifier = Modifier
                            .height(567.dp)
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Column {
                            if (image != null) {
                                Image(bitmap = image!!.asImageBitmap(), contentDescription = "")
                            }
                            Text(text = showenItem.name)
                            Text(text = showenItem.price.toString())
                            Text(text = showenItem.name)

                        }
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(4.dp)
                .height(660.dp)
        ) {

            items(filteredList.count()) { index ->
                Row(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 7.dp)
                        .clickable(onClick = {
                            showenItem.name = filteredList[index].name
                            showenItem.price = filteredList[index].price
                            showDialog = true
                        })
                ) {
                    Column {
                        Text(text = filteredList[index].name, fontSize = 22.sp)
                        Text(
                            text = "${filteredList[index].price}",
                            fontSize = 26.sp,
                            fontWeight = FontWeight(600)
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Button(
                            onClick = {
                                di.addOrder(
                                    Order(
                                        "ул. Пушкина",
                                        list[index].name,
                                        "Пользователь",
                                        list[index].price
                                    )
                                )
                            },
                            modifier = Modifier.width(65.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.hsl(
                                    0.168f,
                                    0.6f,
                                    0.62f,
                                    1f
                                )
                            )
                        ) {

                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "",
                                modifier = Modifier.size(24.dp)
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
            BottomNavigationBar(navController)
        }
    }
}