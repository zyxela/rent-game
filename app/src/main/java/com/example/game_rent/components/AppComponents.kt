package com.example.game_rent.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.game_rent.data.DatabaseInteraction
import com.example.game_rent.data_classes.CatalogItem
import com.example.game_rent.data_classes.Order

@Composable
fun CentralBoldText(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal
        ),
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginInputField(text: String = "Логин"): String {
    var loginText by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        value = loginText,
        onValueChange = { loginText = it },
        label = { Text(text = text) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent, //hide the indicator
            unfocusedIndicatorColor = Color.Transparent
        )
    )
    return loginText
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputField(text: String = "Пароль"): String {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        value = password,
        onValueChange = { password = it },
        label = { Text(text = text) },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        placeholder = { Text(text) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent, //hide the indicator
            unfocusedIndicatorColor = Color.Transparent
        )
    )
    return password
}

@Composable
fun CentralButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(
            text = text,
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.fillMaxWidth(3f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LazyListCatalog(list: List<CatalogItem>) {

    LazyColumn {

        items(list.count()) { index ->
            Row() {
                Column {
                    Text(text = list[index].name)
                    Text(
                        text = "${list[index].price}",
                        fontSize = 30.sp,
                        fontWeight = FontWeight(600)
                    )
                    Button(onClick = {

                    }) {
                        Text(text = "Добавить")
                    }
                }


            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemDialog(
    onDismissRequest: () -> Unit,

    ) {

    var name by remember {
        mutableStateOf("")
    }
    var price by remember {
        mutableStateOf("")
    }

    Dialog(onDismissRequest = {
        val db = DatabaseInteraction()
        db.addCatalogItem(CatalogItem(name, price.toDouble()))
        onDismissRequest
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
                        value = price,
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

@Composable
fun ShowItemDialog(item: CatalogItem, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(567.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column {
                Text(text = item.name)
                Text(text = item.price.toString())
                Text(text = item.name)
            }
        }
    }
}

@Composable
fun historyItem(item:Order, color:Color){
    Row(modifier = Modifier.fillMaxWidth()) {
        Card (modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = color)){
            Text(item.game)
            Text(text = item.price.toString())
        }

    }
}

@Composable
fun ShowHistoryDialog(
    executedList: List<Order>,
    deniedList: List<Order>,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest }) {

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