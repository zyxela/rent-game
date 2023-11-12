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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
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
                val ref = Firebase.storage.reference.child("images/${showenItem.name}.jpg")
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
                            Text(text = showenItem.description)

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
                //containerColor = Color(238, 238, 238)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(238, 238, 238))
                ) {
                    Row(
                        modifier = Modifier
                            .padding(7.dp)
                            .clickable(onClick = {
                                showenItem.name = filteredList[index].name
                                showenItem.price = filteredList[index].price
                                showDialog = true
                            })
                    ) {
                        Column(modifier = Modifier.padding(6.dp)) {
                            Text(
                                text = filteredList[index].name,
                                fontSize = 26.sp,
                                fontWeight = FontWeight(550)
                            )
                            Text(
                                text = "${filteredList[index].price}",
                                fontSize = 21.sp,
                                fontWeight = FontWeight(500)
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Center
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
                                modifier = Modifier.size(70.dp),
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(255, 105, 105)
                                )
                            ) {

                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "",
                                    modifier = Modifier.size(28.dp)
                                )
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
                .weight(2f), verticalArrangement = Arrangement.Bottom
        ) {
            BottomNavigationBar(navController)
        }
    }
}