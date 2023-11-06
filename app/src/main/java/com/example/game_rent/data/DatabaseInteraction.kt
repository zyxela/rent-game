package com.example.game_rent.data

import android.util.Log
import com.example.game_rent.data_classes.CatalogItem
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DatabaseInteraction {
    lateinit var db: FirebaseFirestore
    private fun connect() {
        try {
            db = Firebase.firestore
        } catch (e: Exception) {
            Log.e("DATABASE_CONNECTION_ERROR", e.message.toString())
        }


    }

    suspend fun getCatalog(): MutableList<CatalogItem> = suspendCoroutine { continuation ->
        val list: MutableList<CatalogItem> = mutableListOf()
        connect()

        db.collection("catalog")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val name = document.data["name"].toString()
                    if (name != "") {
                        val price = document.data["price"].toString().toDouble()
                        val item = CatalogItem(name, price)
                        list.add(item)
                    }
                    Log.i("TAG", "${document.id} => ${document.data["price"]}; ${document.data["name"]}")
                }
                continuation.resume(list) // Возвращаем список после успешного выполнения
            }
            .addOnFailureListener { exception ->
                Log.w("ERROr", "Error getting documents.", exception)
                continuation.resume(list) // Возвращаем пустой список при ошибке
            }
    }

    fun addCatalogItem(list: List<CatalogItem>) {
        connect()
        for (item in list)
            db.collection("catalog")
                .add(item)
                .addOnSuccessListener {
                    Log.i("COMPLETE_ADDED", "Item successfully added")
                }
                .addOnFailureListener { e ->
                    Log.e("ADDED_ERROR", "${e.message}")
                }

    }
}