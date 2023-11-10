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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.game_rent.components.AddItemDialog
import com.example.game_rent.data.DatabaseInteraction
import com.example.game_rent.data_classes.CatalogItem
import com.example.game_rent.navigation.AdminBottomNavigationBar

@SuppressLint("UnrememberedMutableState")
@Composable
fun AdminCatalog(navController: NavHostController) {

    val di = DatabaseInteraction()
    var catalogList by remember { mutableStateOf<List<CatalogItem>>(emptyList()) }

    LaunchedEffect(Unit){
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

        var showDialog by remember { mutableStateOf(false) }
        if (showDialog) {
            AddItemDialog(onDismissRequest = {
                showDialog = false
            })
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